package cucmber.steps;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import asw.Application;
import asw.dbManagement.CategoryService;
import asw.dbManagement.ParticipantService;
import asw.dbManagement.SuggestionService;
import asw.dbManagement.model.Category;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.streamKafka.productor.KafkaProducer;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import selenium.util.PO_LoginForm;

@SuppressWarnings("deprecation")
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class ActualizarDashboard1Steps {
	private static SecureRandom random = new SecureRandom();
	
	@Autowired
	private SuggestionService suggestionService;
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private KafkaProducer kafka;
	
	private WebDriver driver = null;
	private String url = "http://localhost:8090/";
	private String login = "maria@gmail.com";
	private String password = "123456";
	
	protected static Suggestion s;
	
	@Cuando("^el administrador esta en el dashboard$")
	public void el_administrador_esta_en_el_dashboard() throws Throwable {
		driver = new HtmlUnitDriver();
		driver.get(url);
		driver.navigate().to(url);
		assertTrue("titulo no coincide", driver.getTitle().equals("Login"));
		new PO_LoginForm().completeForm(driver, login, password);

		assertTrue("Titulo no se corresponde", driver.getTitle().equals("Administration"));
		
		WebElement elto = driver.findElements(By.id("enlaceDashboard")).get(0);
		elto.click();
		assertTrue("Titulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h1.page-header")).getText().equals("Dashboard"));
		assertTrue("Subtitulo no tiene el mismo texto", driver
				.findElement(By.cssSelector("h2.sub-header")).getText().equals("Suggestions"));
	}
	
	@Entonces("^puede ver nuevas sugerencias$")
	public void puede_ver_nuevas_sugerencias() throws Throwable {
		s = newSuggestion();
		// TODO falta comprobar html, problemas con javascript para las pruebas
	}
	
	@Entonces("^puede ver el numero de votos de las sugerencias$")
	public void puede_ver_el_numero_de_votos_de_las_sugerencias() throws Throwable {
		voteSuggestion(s.getIdentificador());
		// TODO falta comprobar html
	}
	
	@Entonces("^puede ver el numero de comentarios de las sugerencias$")
	public void puede_ver_el_numero_de_comentarios_de_las_sugerencias() throws Throwable {
		commentSuggestion(nextId(), s.getIdentificador());
		// TODO falta comprobar html
	}
	
	@Entonces("^puede ver las alertas a las sugerencias$")
	public void puede_ver_las_alertas_de_las_sugerencias() throws Throwable {
		alertSuggestion(s.getIdentificador());
		// TODO falta comprobar html
	}
	
	@Entonces("^puede ver las sugerencias eliminadas$")
	public void puede_ver_las_sugerencias_eliminadas() throws Throwable {
		deleteSuggestion(s.getIdentificador());
		// TODO falta comprobar html
	}
	
	private Suggestion newSuggestion() {
		String id = nextId();
		Participant p = participantService.getParticipant("pepe@participant.es", "12345");
		Category c = categoryService.getCategoryByName("Ocio");
		Suggestion s = suggestionService
				.saveSuggestion(new Suggestion(id, "prueba sugerencia", "prueba", p, c));
		kafka.sendNewSuggestion(s.getIdentificador(), s.getTitulo());
		return s;
	}
	
	private void voteSuggestion(String ident){
		kafka.sendPositiveSuggestion(ident);
	}
	
	private void commentSuggestion(String cIdent, String sIdent){
		kafka.sendNewComment(cIdent, sIdent);
	}
	
	private void alertSuggestion(String ident){
		kafka.sendAlertSuggestion(ident);
	}
	
	private void deleteSuggestion(String ident){
		kafka.sendDeleteSuggestion(ident);
	}

	private String nextId() {
		return new BigInteger(130, random).toString(32);
	}
}
