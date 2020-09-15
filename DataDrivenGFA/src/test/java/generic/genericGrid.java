package generic;


import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver; 
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class genericGrid extends evidenceGrid {
    
    public static final char SEPARATOR=',';
    public static final char QUOTE='"';
    /*
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    CONFIGURACIONES
    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */
    
    /***
     * En este método abrimos el properties de configuración del proyecto debe estar en la ruta c:/ambiente/configuracion.
     * @return El archivo de propiedades con la configuración.
     * @throws FileNotFoundException si no encuentra el archivo en la ruta c:/ambiente/configuracion.
     */
    public Properties getPropetiesFile(String Archivo) throws FileNotFoundException{
        Properties prop = new Properties();
        try{
            Reader reader = new InputStreamReader((new FileInputStream(Archivo)), "UTF-8"); 
            prop.load(reader);
        }catch(IOException e){
            System.out.println("Mensaje Properties: "+ e);
        }
        return prop;
        
    }
    
    /**
     * Este método nos sirve para obtener un archivo CSV en una variable tipo CSVReader.
     * @param ruta Es la ruta dentro de la PC donde encontramos el archivo CSV.
     * @return Una variable tipo CSVReader con el contenido del archivo CSV.
     */
    public CSVReader ObtenerDatos(String ruta){
        CSVReader datos = null;
        try{    
            datos = new CSVReader(new FileReader(ruta),SEPARATOR,QUOTE);
        }catch(IOException e){
            System.out.println("No se puede abrir el archivo de Datos verifique la ruta: "+ e);
        }
        return datos;
    }
    
    public RemoteWebDriver openGridBrowser(String navegador, Properties config) throws MalformedURLException, InterruptedException{
        RemoteWebDriver driver = null;
        URL url=null;
        try{
        
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setPlatform(Platform.WINDOWS);
            
            if("chrome".equals(navegador)){
                capabilities.setBrowserName(navegador);
                capabilities = DesiredCapabilities.chrome();
                url = new URL("http://localhost:5557/wd/hub");
                driver = new RemoteWebDriver(url, capabilities);
            }
            if("ie".equals(navegador)){
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                //capabilities.setBrowserName(navegador);
                //capabilities = DesiredCapabilities.internetExplorer();
//                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
//                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,false);
                url = new URL("http://localhost:5559/wd/hub");
                driver = new RemoteWebDriver(url, ieOptions);
            }
            if("edge".equals(navegador)){
                EdgeOptions optionEdge = new EdgeOptions();
                //capabilities.setBrowserName(navegador);
                //capabilities = DesiredCapabilities.edge();
                url = new URL("http://localhost:5557/wd/hub");
                driver = new RemoteWebDriver(url, optionEdge);
            }
            if("firefox".equals(navegador)){
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                //capabilities.setBrowserName(navegador);
                //capabilities = DesiredCapabilities.firefox();
                url = new URL("http://localhost:5556/wd/hub");
                driver = new RemoteWebDriver(url, firefoxOptions);
            }
            if("motorolaOneVision".equals(navegador)){
                DesiredCapabilities capabilitie =new DesiredCapabilities();
                capabilitie.setCapability("deviceName", navegador);
                capabilitie.setCapability("platformVerion", "9");
                capabilitie.setCapability("platformName", "Android");
                capabilitie.setCapability("device", "ZY326LRQHS");
                capabilitie.setCapability("browserName", "Chrome");
                url = new URL("http://localhost:4723/wd/hub");
                driver = new RemoteWebDriver(url, capabilitie);
            }
            
            
        }catch(Exception e){
            System.out.println(e);
        }

            return driver;
        }
    
    public void leventarNodosGrid() throws InterruptedException, FileNotFoundException{
        Properties Config = this.getPropetiesFile("configuracion\\configuracion.properties");
        try {
            String cmd = "cmd /c start cmd.exe /K java -jar "+Config.getProperty("seleniumServer")+" -role hub -port "+Config.getProperty("hubPort");
            Runtime.getRuntime().exec(cmd); 
        } catch (IOException ioe) {
            System.out.println ("Hub: "+ioe);
        }
        Thread.sleep(5000);
        try {
            String cmd = "cmd /c start cmd.exe /K java -Dwebdriver.gecko.driver="+Config.getProperty("gecko")+" -jar "+Config.getProperty("seleniumServer")+" -role webdriver -hub http://localhost:5555/grid/register -port "+Config.getProperty("geckoPort")+" -browser browserName=firefox";
            Runtime.getRuntime().exec(cmd); 
        } catch (IOException ioe) {
            System.out.println ("Gecko node: "+ioe);
        }
        Thread.sleep(5000);
        try {
            String cmd = "cmd /c start cmd.exe /K java -Dwebdriver.chrome.driver="+Config.getProperty("chrome")+" -jar "+Config.getProperty("seleniumServer")+" -role webdriver -hub http://localhost:5555/grid/register -port "+Config.getProperty("chromePort")+" -browser browserName=chrome";
            Runtime.getRuntime().exec(cmd); 
        } catch (IOException ioe) {
            System.out.println ("Chrome node: "+ioe);
        }
        /*Thread.sleep(5000);
        try {
            String cmd = "cmd /c start cmd.exe /K java -Dwebdriver.edge.driver="+Config.getProperty("edge")+" -jar "+Config.getProperty("seleniumServer")+" -role webdriver -hub http://localhost:5555/grid/register -port "+Config.getProperty("edgePort")+" -browser browserName=iexplorer";
            Runtime.getRuntime().exec(cmd); 
        } catch (IOException ioe) {
            System.out.println ("Edege node: "+ioe);
        } 
        Thread.sleep(5000);
        try {
            String cmd = "cmd /c start cmd.exe /K java -Dwebdriver.ie.driver="+Config.getProperty("ie")+" -jar "+Config.getProperty("seleniumServer")+" -role webdriver -hub http://localhost:5555/grid/register -port "+Config.getProperty("iePort")+" -browser browserName=iexplorer";
            Runtime.getRuntime().exec(cmd); 
        } catch (IOException ioe) {
            System.out.println ("Edege node: "+ioe);
        }*/
    }
    
    public void cierraNodosGrid(){
        try {
            String cmd = "cmd /c start cmd.exe /K TASKKILL /F /IM cmd.exe /T";
            Runtime.getRuntime().exec(cmd); 
        } catch (IOException ioe) {
            System.out.println ("Edege node: "+ioe);
        }
        
    }
    
    public void GenerarEvidencias(RemoteWebDriver driver, String Escenario, String Resultado, int contador, List<String> Pasos, String RutaEvidencia, String Modulo, String Version, String navegador){
        try{
            System.out.println("Lista: "+Pasos);
            if(Resultado.length()>10){
                if("Ejecución Fallida".equals(Resultado.substring(0, 17))){
                    this.capturaDriver(driver, RutaEvidencia, contador, Escenario, navegador);
                }
            }
            //Generamos PDF
            this.crearPDF(Escenario, Resultado, contador, Pasos, RutaEvidencia, Modulo, Version, navegador);
            //Generamos PDF
            //this.crearXML(Escenario, Resultado, contador, Pasos, RutaEvidencia, navegador);
            //Generamos HTML
            this.crearHTML(Escenario, Resultado, contador, Pasos, RutaEvidencia, Modulo, Version, navegador);

        }catch(Exception e){
            System.out.println("MEnsaje Evidencia: "+e);
        }
    }
    /*
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    ASSERTS
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */
    
    /**
     * En este método vamos a validar que dos mensajes sean iguales.
     * @param driver 
     * @param msjActual Es el valr del texto que se compara.
     * @param Elemento Es el elemento del que se va a comparr el texto.
     */
    public String AssertMsjElemento(RemoteWebDriver driver, String msjActual, String Elemento) throws InterruptedException{
        String msj = "";
        try{
            this.dormir3seg();
            Assert.assertEquals(this.obtenerTexto(driver, "id", Elemento), msjActual);
            msj = "Exitoso";
        }catch(AssertionError e){
            System.out.println("Mensaje Assert Fail: "+e);
            msj = "Fallido, Reusltado Esperado: "+e;
        }
        
        return msj;
    }
    
    public String AssertTrueElemento(RemoteWebDriver driver, String msjActual, String Elemento) throws InterruptedException{
   	 String msj = "";
        try{
            this.dormir3seg();
            Assert.assertTrue(this.obtenerTexto(driver, "id", Elemento).contains("se envió para su aprobación."));
            msj = "Exitoso";
        }catch(AssertionError e){
            System.out.println("Mensaje Assert Fail: "+e);
            msj = "Fallido, Resultado Esperado: "+e;
        }
        
        return msj;
   	
   }
    
    /*
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    METODOS
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    */
    
    /***
     * El método nos ayuda a sweleccionar un texto de un cbo, select o dripdownlist.
     * @param driver es el webDriver en el que se ejecuta la pruebas automatizada.
     * @param findby Es el tipo de selector selenium id, name o XPATH.
     * @param Elemento Es el selector selenium del cbo, select o dropdownlist.
     * @param Texto Es el valor del texto que se busca en el elemento.
     */
    public void seleccionar_combo(RemoteWebDriver driver, String findby, String Elemento, String Texto){
        WebElement combo = this.driverWait(driver, findby, Elemento);
        Select cbo = new Select(combo);
        cbo.selectByVisibleText(Texto);
    }
    
    
    
    /***
     * El método nos ayuda a dar clic a un elemento.
     * @param driver es el webDriver en el que se ejecuta la pruebas automatizada.
     * @param findby Es el tipo de selector selenium id, name o XPATH.
     * @param Elemento Es el selector selenium que se le va a dar Clic.
     * @throws InterruptedException 
     */
    public void clic_btn(RemoteWebDriver driver, String findby, String Elemento) throws InterruptedException{
        WebElement btn = this.driverWait(driver, findby, Elemento);
        btn.click();
    }
    
    
    /***
     * El método nos ayuda obtener un elemento pero utiliza el driver wait para darle tiempo al navegador de reaccionar.
     * @param driver Es el webDriver en el que se ejecuta la pruebas automatizada.
     * @param findby Es el tipo de selector selenium id, name o XPATH.
     * @param Elemento Es el selector selenium al que le vamos agregar texto.
     */
    public WebElement driverWait(WebDriver driver, String findby, String Elemento){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement el = null;
        switch(findby) {
            case "id":
                el = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id(Elemento))));
                break;
            case "name":
            	el = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name(Elemento))));
                break;
            case "xpath":
                el = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(Elemento))));
                break;
        }
        return el;
    }
    
    /***
     * El método nos ayuda a presionar enter sobre un elemento.
     * @param driver Es el webDriver en el que se ejecuta la pruebas automatizada.
     * @param findby Es el tipo de selector selenium id, name o XPATH.
     * @param Elemento Es el selector selenium al que le vamos agregar texto.
     */
    public void enter(RemoteWebDriver driver, String findby, String Elemento){
        WebElement Elemen = this.driverWait(driver, findby, Elemento);
        Elemen.submit();
    }
    
    /***
     * El método nos ayuda a ingresar texto a un elemento.
     * @param driver Es el webDriver en el que se ejecuta la pruebas automatizada.
     * @param findby Es el tipo de selector selenium id, name o XPATH.
     * @param Elemento Es el selector selenium al que le vamos agregar texto.
     * @param Texto Es el texto que se va ingresar al campo.
     */
    public void ingresar_texto(RemoteWebDriver driver, String findby, String Elemento, String Texto){
        WebElement Elemen = this.driverWait(driver, findby, Elemento);
        Elemen.sendKeys(Texto);
    }

    /***
     * El método nos ayuda a cerrar un WebDriver.
     * @param driver Es el webDriver en el que se ejecuta la pruebas automatizada.
     */
    public void cerrar_driver(WebDriver driver){
            driver.close();
    }

    /***
     * El método le da un tiempo de 3 segundos al webDriver.
     * @exception InterruptedException Para manejar excepciones con el hilo de procesamiento que se esta deteniendo.
     */
    public void dormir3seg() throws InterruptedException{
        Thread.sleep(3000);
    }
    /***
     * El método le da un tiempo de 10 segundos al webDriver.
     * @exception InterruptedException Para manejar excepciones con el hilo de procesamiento que se esta deteniendo.
     */
    public void dormir10seg() throws InterruptedException{
        Thread.sleep(10000);
    }
    /***
     * El método le da un tiempo de 10 segundos al webDriver.
     * 
     * @exception InterruptedException Para manejar excepciones con el hilo de procesamiento que se esta deteniendo.
     */
    public void abrirURl(RemoteWebDriver driver, String URL){
        try{
            driver.get(URL);
        }catch(Exception e){
            System.out.println("Mensaje: "+e);
        }
    }
    
    /***
     * El método obtiene el texto de un objeto.
     * @param driver es el webDriver en el que se ejecuta la pruebas automatizada.
     * @param findby Es el tipo de selector selenium id, name o XPATH.
     * @param Elemento Es el selector selenium.
     * @return Regresa el texto del objeto o un vacio en caso de no encontrar el findby.
     */
    public String obtenerTexto(RemoteWebDriver driver, String findby, String Elemento){
        String texto = "";
        WebElement Element = this.driverWait(driver, findby, Elemento);
        texto = Element.getText();
        return texto;
    }
    
    public void Bajar_Scroll(RemoteWebDriver driver, int contador, Properties Config, Properties Elementos, String Escenario, String navegador) throws FileNotFoundException, InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
    }
    
    public void Subir_Scroll(RemoteWebDriver driver, int contador, Properties Config, Properties Elementos, String Escenario, String navegador) throws FileNotFoundException, InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-1000)");
    }
    
    public void Bajar_Tecla(RemoteWebDriver driver, int contador, Properties Config, Properties Elementos, String Escenario, String navegador) throws FileNotFoundException, InterruptedException {
    	Actions action = new Actions(driver);
    	action.keyDown(Keys.CONTROL).sendKeys(Keys.ENTER).perform(); 
    }
    
}	
