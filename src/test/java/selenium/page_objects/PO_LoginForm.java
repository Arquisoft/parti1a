package selenium.page_objects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.SeleniumUtils;
import utils.ThreadUtil;

public class PO_LoginForm {
	
	/*
	 * @Clean : posibles incosistencias en los id , ya que se va a utilizar prime faces
	 * 
	 * Clase creada para rellenar logins y así separa el codigo
	 */
	
	public void completeForm(WebDriver driver, String loginValue,String passwordValue){
		//(1) encontramos el input para el correo y lo rellenamos
		List<WebElement> login = SeleniumUtils.EsperaCargaPagina(driver, "id", "inputEmail" , 10);
		
		login.get(0).click();
		login.get(0).clear();
		login.get(0).sendKeys(loginValue);
		
		ThreadUtil.wait(500);
		//(2) encontramos el input para la password y lo rellenamos
		List<WebElement> password =  SeleniumUtils.EsperaCargaPagina(driver, "id","inputPassword" , 10);
		
		password.get(0).click();
		password.get(0).clear();
		password.get(0).sendKeys(passwordValue);
		
		ThreadUtil.wait(500);
		//(3) encontramos el boton de login y clicamos en el
		List<WebElement> boton = SeleniumUtils.EsperaCargaPagina(driver, "id", "boton_login", 10);
		
		boton.get(0).click();
		
		ThreadUtil.wait(700);
		
	}

}
