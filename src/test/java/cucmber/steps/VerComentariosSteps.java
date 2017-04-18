package cucmber.steps;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import selenium.page_objects.PO_LoginForm;

public class VerComentariosSteps {
	private WebDriver driver = null;
	private String url = "http://localhost:8090/";
	private String login = "maria@gmail.com";
	private String password = "123456";

	@Cuando("^el administrador esta viendo las sugerencias$")
	public void el_administrador_se_logea_en_la_aplicacion() throws Throwable {
		driver = new HtmlUnitDriver();
		driver.get(url);
		driver.navigate().to(url);
		assertTrue("titulo no coincide", driver.getTitle().equals("Login"));
		new PO_LoginForm().completeForm(driver, login, password);
		assertTrue("Titulo no se corresponde", driver.getTitle().equals("Dashboard"));
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h2.sub-header")).getText().equals("Suggestions"));
	}

	@Entonces("^puede hacer click en una para ver sus comentarios$")
	public void puede_hacer_click_en_una_para_ver_sus_comentarios() throws Throwable {
		WebElement elto = driver.findElements(By.className("sugerencia")).get(0);
		elto.click();
		assertTrue("Subtitulo no tiene el mismo texto",
				driver.findElement(By.cssSelector("h3.sub-header")).getText().equals("Comments"));
		driver.quit();
	}
}
