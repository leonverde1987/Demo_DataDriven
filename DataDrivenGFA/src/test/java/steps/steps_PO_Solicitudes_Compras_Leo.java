package steps;


import generic.genericGrid;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;
import org.junit.ComparisonFailure;
import org.openqa.selenium.remote.RemoteWebDriver;

public class steps_PO_Solicitudes_Compras_Leo extends genericGrid{
    
    
    public String navegador(String Cadena){
        String navegador="";
        Cadena = Cadena.substring(17, Cadena.length());
        //RemoteWebDriver: firefox on WINDOWS (32e13b4d-f272-43f3-9ad8-045535191a9a)
        for(int a = 0; Cadena.length() > a; a++){
            if(" ".equals(Cadena.substring(a, a+1))){
                a=Cadena.length()+1;
            }
            else{
                navegador = navegador + Cadena.substring(a, a+1);
            }
        }
        return navegador;
    }
    
    
    
    /**
     * Esté Método nos ayuda a dirigir el driver a una URL en especifico.
     * @param driver Elemento WebDriver de la prueba.
     * @param contador Es el controlador de pasos ejecutados.
     * @param Config Es el archivo de configuración de la prueba.
     * @param Escenario Es el nombre del caso de prueba.
     * @throws FileNotFoundException Cacha cualquier excepción en la ejecución.
     * @throws InterruptedException Cacha si el archivo Config no existe.
     */
    public void ingresar_A_URL(RemoteWebDriver driver, int contador, Properties Config, String Escenario, String navegador) throws FileNotFoundException, InterruptedException {
        try{
            this.abrirURl(driver, Config.getProperty("urlOracle"));
            this.capturaDriver(driver, Config.getProperty("rutaEvidencia"), contador, Escenario, navegador);
        }catch(InterruptedException e){
            System.out.println("Mensaje: "+e);
        }
        
    }
    
    
    

    public steps_PO_Solicitudes_Compras_Leo() {
    }
    
    /**
     * Esté método nos ingresa contenido en un componente de texto.
     * @param driver Elemento WebDriver de la prueba.
     * @param usuario Es el valor del texto que se va ingresar al componente.
     * @param contador Es el controlador de pasos ejecutados.
     * @param Config Es el archivo de configuración de la prueba.
     * @param Elementos Es el archivo con los elementos del aplicativo 
     * @param Escenario Nombre del caso de prueba a ejecutar
     * @throws FileNotFoundException Cacha cualquier excepción en la ejecución.
     * @throws InterruptedException Cacha si el archivo Config no existe. 
     */
    public void Login_Oracle(RemoteWebDriver driver, String usuario, String contrasena, int contador, Properties Config, Properties Elementos, String Escenario, String navegador) throws FileNotFoundException, InterruptedException {
        
        this.ingresar_texto(driver, "id", Elementos.getProperty("id_usuario"), usuario);
        this.ingresar_texto(driver, "id", Elementos.getProperty("id_contrasena"), contrasena);
        this.clic_btn(driver, "id", Elementos.getProperty("btn_conexion"));
        this.capturaDriver(driver, Config.getProperty("rutaEvidencia"), contador, Escenario, navegador);
    }
    
    
    
    /**
     * Esté método nos ayuda a seleccionar una opción del combo prefijos.
     * @param driver Elemento WebDriver de la prueba.
     * @param textoBuscar Es el valor del texto que se va ingresar al componente.
     * @param contador Es el controlador de pasos ejecutados.
     * @param Config Es el archivo de configuración de la prueba.
     * @param Elementos Es el archivo con los elementos del aplicativo.
     * @param Escenario Nombre del caso de prueba a ejecutar.
     * @throws InterruptedException Cacha si el archivo Config no existe. 
     */
    public void seleccionar_prefijo(RemoteWebDriver driver, String textoBuscar, int contador, Properties Config, Properties Elementos, String Escenario, String navegador) throws InterruptedException{
        this.seleccionar_combo(driver, "id", Elementos.getProperty("id_cbo_prefijo"), textoBuscar);
        this.capturaDriver(driver, Config.getProperty("rutaEvidencia"), contador, Escenario, navegador);
    }
    
    /**
     * Esté método nos ayuda a presionar el .
     * @param driver Elemento WebDriver de la prueba.
     * @param contador Es el controlador de pasos ejecutados.
     * @param Config Es el archivo de configuración de la prueba.
     * @param Elementos Es el archivo con los elementos del aplicativo.
     * @param Escenario Nombre del caso de prueba a ejecutar.
     * @param navegador Es el navegador donde se ejecuto la prueba.
     * @throws InterruptedException Cacha si el archivo Config no existe. 
     */
    public void presionar_btn_mas(RemoteWebDriver driver, int contador, Properties Config, Properties Elementos, String Escenario, String navegador) throws InterruptedException{
        this.clic_btn(driver, "xpath", Elementos.getProperty("xpath_btn_signoMas"));
        this.capturaDriver(driver, Config.getProperty("rutaEvidencia"), contador, Escenario, navegador);
    }
    
    /**
     * Esté método nos ayuda a presionar el .
     * @param driver Elemento WebDriver de la prueba.
     * @param contador Es el controlador de pasos ejecutados.
     * @param Config Es el archivo de configuración de la prueba.
     * @param Elementos Es el archivo con los elementos del aplicativo.
     * @param Escenario Nombre del caso de prueba a ejecutar.
     * @param navegador Es el navegador donde se ejecuto la prueba.
     * @throws InterruptedException Cacha si el archivo Config no existe. 
     */
    public void presionar_Consultar(RemoteWebDriver driver, int contador, Properties Config, Properties Elementos, String Escenario, String navegador) throws InterruptedException{
        this.clic_btn(driver, "xpath", Elementos.getProperty("xpath_btn_consultar"));
        this.capturaDriver(driver, Config.getProperty("rutaEvidencia"), contador, Escenario, navegador);
    }
    
    /**
     * Esté metodo nos ayuda a cerrar el driver.
     * @param driver  
     */
    public void cerrar_Navegador(RemoteWebDriver driver) {
        this.cerrar_driver(driver);
    }
    /**
     * Est´s método nos ayuda a validar el mensaje del resultado esperado. 
     * @param driver Elemento WebDriver de la prueba.
     * @param Datos Es el archivo de datos de la prueba.
     * @param contador Es el controlador de pasos ejecutados.
     * @param Config Es el archivo de configuración de la prueba.
     * @param Escenario Es el nombre del caso de prueba.
     * @return Regresa el resultado Exitoso o Fallido y su detalle. 
     * @throws InterruptedException 
     */
    public String validar_Mensaje_Data(RemoteWebDriver driver, String Dato, Properties Config, Properties Elementos, int contador, String Escenario, String navegador) throws InterruptedException{
        String msj = "";
        try{
            msj = this.AssertMsjElemento(driver, Dato, Elementos.getProperty("id_msj_ErrorLogin"));
        }catch(ComparisonFailure e){
            msj = "Fallido, Resultado Esperado: "+e;
        }
        this.capturaDriver(driver, Config.getProperty("rutaEvidencia"), contador, Escenario, navegador);
        return msj;
    }
    
    /**
     * Captura una evidencia cuando el Test Falla.
     * @param driver Elemento WebDriver de la prueba.
     * @param Config Es el archivo config de la prueba.
     * @param error Es el número de error detectado.
     * @param Escenario Es el nombre del caso de prueba.
     * @throws InterruptedException Cacha cualquier excepción
     */
    public void capturarEvidencia(RemoteWebDriver driver, Properties Config, int error, String Escenario, String navegador) throws InterruptedException{
        
        switch(error) {
            case 1:
                this.capturaDriver(driver, Config.getProperty("rutaEvidencia"), error, Escenario, navegador);
                break;
        }
    }
    
    /**
     * Esté método finaliza el Test Case y genera las evidencias.
     * @param Escenario Es el nombre del Test Case.
     * @param Resultado Es el resultado del Testa Case Exitoso, Fallido o Ejecución Fallida
     * @param contador Es el total de pasos ejecutados.
     * @param Pasos Es el detalle de los pasos ejecutados.
     * @param RutaEvidencia Es la ruta principal de la evidencia.
     * @param Modulo Es el módulo del aplicativo que se ejecuta.
     * @param Version Es la versión del aplicativo.
     * @throws Exception Cacha cualquier excepción en la ejecución.
     */
    public void finalizarTestCase(RemoteWebDriver driver, String Escenario, String Resultado, int contador, List<String> Pasos, String RutaEvidencia, String Modulo, String Version, String navegador) throws Exception{
        this.GenerarEvidencias(driver, Escenario, Resultado, contador, Pasos, RutaEvidencia, Modulo, Version, navegador);
    }
    
    /**
     * 
     * @param Config
     * @throws InterruptedException
     * @throws FileNotFoundException 
     */
    public void ejecucionGrid(Properties Config) throws InterruptedException, FileNotFoundException{
        this.leventarNodosGrid();
    }
    
    /**
     * Cerramos los nodos del grid selenium.
     * @throws InterruptedException Cacha cualquier excepción de ejecución.
     */
    public void finEjecucionGrid() throws InterruptedException{
        this.cierraNodosGrid();
    }
}
