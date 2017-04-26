package cucmber.steps;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import selenium.page_objects.PO_LoginForm;

public class VerSugerencias {
	private WebDriver driver = null;
	private String url = "http://localhost:8090/";
	private String login = "maria@gmail.com";
	private String password = "123456";

	@Cuando("^el administrador se logea en la aplicacion$")
	public void el_administrador_se_logea_en_la_aplicacion() throws Throwable {
		driver = new HtmlUnitDriver();
		driver.get(url);
		driver.navigate().to(url);
		assertTrue("titulo no coincide", driver.getTitle().equals("Login"));
		new PO_LoginForm().completeForm(driver, login, password);
	}
	
	@Entonces("^puede ver las sugerencias$")
	public void puede_ver_las_sugerencias() throws Throwable {
		assertTrue("Titulo no se corresponde", driver.getTitle().equals("Dashboard"));
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h2.sub-header")).getText().equals("Suggestions"));
		driver.quit();
	}
}
