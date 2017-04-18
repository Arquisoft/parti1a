package cucmber.steps;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import selenium.page_objects.PO_LoginForm;

public class LoginIncorrectoSteps {

	private WebDriver driver = null;
	private String url = "http://localhost:8090/";

	@Cuando("^el usuario se encuentra en la pagina de login$")
	public void el_usuario_se_encuentra_en_la_pagina_de_login() {
		driver = new HtmlUnitDriver();
		driver.get(url);
		driver.navigate().to(url);
		assertTrue("titulo no coincide", driver.getTitle().equals("Login"));
	}

	@Entonces("^inserta su mail incorrecto \"([^\"]*)\" y su password \"([^\"]*)\"$")
	public void inserta_su_mail_incorrecto_y_su_password(String name, String password) {
		new PO_LoginForm().completeForm(driver, name, password);
	}

	@Entonces("^se logea de manera incorrecta$")
	public void se_logea_de_manera_incorrecta() {
		WebElement mensajeError = driver.findElement(By.id("error_div"));
		assertTrue("mensaje de error no coincide",
				mensajeError.getText().equals("Ha ocurrido el siguiente error: Wrong mail style"));
		driver.quit();
	}

}
