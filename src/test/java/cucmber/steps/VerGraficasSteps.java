package cucmber.steps;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import selenium.page_objects.PO_LoginForm;
import utils.SeleniumUtils;

public class VerGraficasSteps {
	private WebDriver driver = null;
	private String url = "http://localhost:8090/";
	private String login = "maria@gmail.com";
	private String password = "123456";

	@Cuando("^el administrador esta en la pantalla de inicio$")
	public void el_administrador_se_logea_en_la_aplicacion() throws Throwable {
		driver = new HtmlUnitDriver();
		driver.get(url);
		driver.navigate().to(url);
		assertTrue("titulo no coincide", driver.getTitle().equals("Login"));
		new PO_LoginForm().completeForm(driver, login, password);
		
		assertTrue("Titulo no se corresponde", driver.getTitle().equals("Administration"));
	}

	@Entonces("^puede ver el menu$")
	public void puede_ver_el_menu() throws Throwable {
		SeleniumUtils.textoPresentePagina(driver, "Dashboard");
		SeleniumUtils.textoPresentePagina(driver, "Graphics");
		SeleniumUtils.textoPresentePagina(driver, "Parameter configuration");
		SeleniumUtils.textoPresentePagina(driver, "Accepted Suggestions");
		SeleniumUtils.textoPresentePagina(driver, "Rejected Suggestions");
		SeleniumUtils.textoPresentePagina(driver, "Suggestions taking support");
		SeleniumUtils.textoPresentePagina(driver, "Suggestions on vote");
	}

	@Entonces("^puede hacer click para ver las graficas$")
	public void puede_hacer_click_para_ver_las_graficas() throws Throwable {
		WebElement elto = driver.findElement(By.id("enlaceGraficas"));
		elto.click();
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h1.page-header")).getText().equals("Graphics"));
	}
}
