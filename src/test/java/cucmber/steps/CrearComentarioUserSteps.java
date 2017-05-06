package cucmber.steps;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.Before;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import selenium.util.SeleniumUtils;

public class CrearComentarioUserSteps {

	private WebDriver driver= new HtmlUnitDriver();
	private String baseUrl = "http://localhost:8090/";
	
	private Map<String, String> jualo;
	
	@Before
	public void setUp() {

		driver.get(baseUrl); 

		jualo = new HashMap<String, String>();
		jualo.put("user", "jualo@participant.es");
		jualo.put("pass", "jualo123");
		
	}
	
	@Dado("^el usuario logueado \"([^\"]*)\"$")
	public void el_usuario_logueado(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// throw new PendingException();

		driver.findElement(By.id("inputEmail")).sendKeys(jualo.get("user"));
		driver.findElement(By.id("inputPassword")).sendKeys(jualo.get("pass"));
		driver.findElement(By.id("boton_login")).click();
	}

	@Cuando("^ese usuario comenta una sugerencia$")
	public void ese_usuario_comenta_una_sugerencia() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// throw new PendingException();
//		String s = driver.getCurrentUrl();
		
		driver.findElement(By.xpath("/html/body/div/div/table/tbody/tr/td[6]/a")).click();
		SeleniumUtils.EsperaCargaPagina(driver, "id", "comment", 2000);
		driver.findElement(By.id("comment")).sendKeys("comentario de prueba");
		driver.findElement(By.id("login")).click();
		
	}

	@Entonces("^al acceder a los comentarios de la sugerencia puede ver su comentario$")
	public void al_acceder_a_los_comentarios_de_la_sugerencia_puede_ver_su_comentario() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// throw new PendingException();

		SeleniumUtils.EsperaCargaPagina(driver, "text", "comentario de prueba", 1500);
		
	}

	
}
