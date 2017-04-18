package selenium;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import selenium.page_objects.PO_LoginForm;
import utils.ThreadUtil;

//@Clean posibles modificaciones en los id al tener prime faces
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTest {

	// NAVEGADOR HTMLUnit
	private static WebDriver driver = new HtmlUnitDriver();
	// FIREFOX
	// private static WebDriver driver = new FirefoxDriver();

	// private static WebDriver driver = getDriver();
	private static String URLInicio = "http://localhost:8090/";

	/*
	 * Si alguno teneis un firefox portable y quereis lanzar ese mismo
	 * descomentar el driver comentado e importar las librerias reocordar que se
	 * puede definir un buscador por defecto en preferences web Browser
	 * 
	 */
	// public static WebDriver getDriver() {
	// // Ruta al firefox portable
	// File pathToBinary = new File("D:\\firefox\\FirefoxPortable.exe");
	// // firefox de nuestro ordenador
	//
	// FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
	// FirefoxProfile firefoxProfile = new FirefoxProfile();
	//
	// return driver = new FirefoxDriver(ffBinary, firefoxProfile);
	// }

	@Before
	public void setUp() {
		driver.navigate().to(URLInicio);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@AfterClass
	static public void end() {
		// Espera para que la última prueba borre las cookies
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.quit();
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	/*
	 * ========= PRUEBAS =========
	 */

	@Test
	public void t1_testLoginIncorrecto() {
		// (1) Comprobamos que estamos en la página de login

		assertTrue("Titulo de pagina no coincide", driver.getTitle().equals("Login"));

		WebElement texto = driver.findElement(By.id("inputEmail"));
		assertTrue("placeholder no coincide",
				texto.getAttribute("placeholder").equals("Email address"));

		texto = driver.findElement(By.id("inputPassword"));
		assertTrue("placeholder no coincide", texto.getAttribute("placeholder").equals("Password"));

		texto = driver.findElement(By.id("boton_login"));
		assertTrue("texto del boton no coincide", texto.getText().equals("Sign in"));

		// (2) rellenamos el formulario con datos no válidos
		new PO_LoginForm().completeForm(driver, "noexisto@hotmail.com", "asdfghjl");
		// (3) comprobamos que estemos en la página de error
		WebElement mensajeError = driver.findElement(By.id("error_div"));
		assertTrue("El mensaje de error no coincide",
				mensajeError.getText().equals("Ha ocurrido el siguiente error: User not found"));

		ThreadUtil.wait(2000);

	}

	@Test
	public void t2_testLoginAdmin() {
		// (1) Comprobamos que estamos en la página de login
		assertTrue("Titulo de pagina no coincide", driver.getTitle().equals("Login"));

		WebElement texto = driver.findElement(By.id("inputEmail"));
		assertTrue("placeholder no coincide",
				texto.getAttribute("placeholder").equals("Email address"));

		texto = driver.findElement(By.id("inputPassword"));
		assertTrue("placeholder no coincide", texto.getAttribute("placeholder").equals("Password"));

		texto = driver.findElement(By.id("boton_login"));
		assertTrue("texto del boton no coincide", texto.getText().equals("Sign in"));

		// (2) nos logeamos como administrador correctamente
		new PO_LoginForm().completeForm(driver, "maria@gmail.com", "123456");

		// (3) Comprobamos que estamos en la página del administrador
		assertTrue("Titulo no se corresponde", driver.getTitle().equals("Dashboard"));
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h2.sub-header")).getText().equals("Suggestions"));
		ThreadUtil.wait(2000);
	}

	@Test
	public void t3_testEntrarEnSugerencia() {
		// (1) realizamos un login
		assertTrue("Titulo de pagina no coincide", driver.getTitle().equals("Login"));

		WebElement texto = driver.findElement(By.id("inputEmail"));
		assertTrue("placeholder no coincide",
				texto.getAttribute("placeholder").equals("Email address"));

		texto = driver.findElement(By.id("inputPassword"));
		assertTrue("placeholder no coincide", texto.getAttribute("placeholder").equals("Password"));

		texto = driver.findElement(By.id("boton_login"));
		assertTrue("texto del boton no coincide", texto.getText().equals("Sign in"));

		new PO_LoginForm().completeForm(driver, "maria@gmail.com", "123456");

		// (2) validamos que estamos en la ventana adecuada
		assertTrue("Titulo no se corresponde", driver.getTitle().equals("Dashboard"));
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h2.sub-header")).getText().equals("Suggestions"));

		// (3) entramos en la primera sugerencia
		texto = driver.findElements(By.className("sugerencia")).get(0);
		texto.click();
		assertTrue("Subtitulo no tiene el mismo texto",
				driver.findElement(By.cssSelector("h3.sub-header")).getText().equals("Comments"));
	}

	@Test
	public void t4_testEntrarEnGraficas() {
		// (1) realizamos un login
		assertTrue("Titulo de pagina no coincide", driver.getTitle().equals("Login"));

		WebElement texto = driver.findElement(By.id("inputEmail"));
		assertTrue("placeholder no coincide",
				texto.getAttribute("placeholder").equals("Email address"));

		texto = driver.findElement(By.id("inputPassword"));
		assertTrue("placeholder no coincide", texto.getAttribute("placeholder").equals("Password"));

		texto = driver.findElement(By.id("boton_login"));
		assertTrue("texto del boton no coincide", texto.getText().equals("Sign in"));

		new PO_LoginForm().completeForm(driver, "maria@gmail.com", "123456");

		// (2) validamos que estamos en la ventana adecuada
		assertTrue("Titulo no se corresponde", driver.getTitle().equals("Dashboard"));
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h2.sub-header")).getText().equals("Suggestions"));
		
		// (3) entramos en la pantalla de graficas
		texto = driver.findElement(By.id("enlaceGraficas"));
		texto.click();
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h1.page-header")).getText().equals("Graphics"));
	}
}
