package participants;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import asw.Application;
import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.participants.webService.request.PeticionChangeEmailREST;
import asw.participants.webService.request.PeticionChangePasswordREST;
import asw.participants.webService.request.PeticionInfoREST;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParticipantTest {
	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	@Autowired
	private GetParticipant getParticipant;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	@Test
	public void t1_domainModelEqualsTest() {
		Participant participant1 = getParticipant.getParticipant("pepe@participant.es");
		Participant participant2 = getParticipant.getParticipant("pep@participant.es");
		Participant participant3 = getParticipant.getParticipant("pepe@participant.es");
		Participant participant4 = getParticipant.getParticipant("paco@gmail.es");
		assertFalse(participant1.equals(participant2));
		assertFalse(participant1.equals(4));
		assertTrue(participant1.equals(participant3));
		assertTrue(participant1.equals(participant1));
		assertFalse(participant1.equals(participant4));
		
		participant1.setAdmin(true);
		participant1.setPolitician(true);
		
		assertTrue(participant1.isAdmin());
		assertTrue(participant1.isPolitician());
		
		participant1.setAdmin(false);
		participant1.setPolitician(false);
		
		assertFalse(participant1.isAdmin());
		assertFalse(participant1.isPolitician());
		
		
	}

	@Test
	public void t2_domainModelToString() {
		Participant participant1 = getParticipant.getParticipant("pepe@participant.es");
		assertEquals(participant1.toString(),
				"Participant [id=" + participant1.getId() + ", nombre=" + participant1.getNombre() + ", apellidos="
						+ participant1.getApellidos() + ", password=" + participant1.getPassword()
						+ ", fechaNacimiento=" + participant1.getFechaNacimiento() + ", email="
						+ participant1.getEmail() + ", DNI=" + participant1.getDNI() + ", direccion="
						+ participant1.getDireccion() + ", nacionalidad=" + participant1.getNacionalidad()
						+ ", isAdmin=" + false + ", isPolitician=" + false + "]");
	}

	@Test
	public void t3_domainModelHashCodeTest() {
		Participant participant1 = getParticipant.getParticipant("pepe@participant.es");
		Participant participant3 = getParticipant.getParticipant("pepe@participant.es");
		assertEquals(participant1.hashCode(), participant3.hashCode());
	}

	// GET PARTICIPANT INFO
	
	@Test
	public void t4_participantExistAndCorrectPasssword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";

		response = template.postForEntity(userURI, new PeticionInfoREST("pepe@participant.es", "12345"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"firstName\":\"Pepe\",\"lastName\":\"Gonzalez\",\"edad\":26,\"email\":\"pepe@participant.es\",\"id\":\"66543245T\"}"));
	}

	@Test
	public void t5_participantDoNotExist() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("ofelia@hotmail.com", "ajksdkje"),
				String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI, new PeticionInfoREST("martin@hotmail.com", "shcxhqw"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void t6_incorrectPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String incorrectPassword = "{\"reason\": \"Password do not match\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("pepe@participant.es", "12356"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));
	}

	@Test
	public void t7_emptyEmail() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyEmail = "{\"reason\": \"User email is required\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "1223"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "iewgs"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("   ", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void t8_invalidEmail() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("ajsjc", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("jxjsjd@", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("chdgetc@chhsy", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("sjhwuwsc", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void t9_emptyPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyPassword = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("paco@hotmail.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("pepe@gmail.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("carmen@yahoo.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("isabel@gmail.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));
	}
	
	@Test
	public void t10_incorrectEmail() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String incorrectEmail = "{\"reason\": \"User not found\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("pep@participant.es", "12356"), String.class);
		assertThat(response.getBody(), equalTo(incorrectEmail));
	}
	
	// CHANGE DATA (EMAIL Y PASSWORD)
	
	@Test
	public void t11_emailRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("", "", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("	", "", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("", "", "shfhs"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void t13_newEmailRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "", ""),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "", "   "),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "", "	"),
				String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void t14_invalidEmailChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco", "", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@", "", "   "), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail", "  ", "	"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void t15_newInvalidEmailChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "", "xhhuwi"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "", "fhgythf@"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "", "fhfyg@hotmail"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void t16_emailChangeUserNotFound() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pao@hotmail.com", "123456", "pac@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pee@gmail.com", "123456", "pepe@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pa@hotmail.com", "123456", "fhfyg@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void t17_sameEmailErrorChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String sameEmail = "{\"reason\": \"Same email\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco@hotmail.com", "", "paco@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("pepe@gmail.com", "", "pepe@gmail.com"),
				String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("carmen@yahoo.com", "", "carmen@yahoo.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));
	}

	@Test
	public void t18_emailRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("	", "chsh", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "dfhe", "dhdgx"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void t19_inValidRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("shdgr", "", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("shdgr@", "", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("shdgr@hotmail", "", ""),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void t20_passwordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "", "dkdddd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void t21_newPasswordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djfhr", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djvhrhc", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "dkejd", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void t22_samePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djfhr", "djfhr"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("paco@hotmail.com", "djvhrhc", "djvhrhc"), String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "dkejd", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}

	@Test
	public void t23_notFoundParticipantPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pac@hotmail.com", "djfhr", "djfhrtt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("martin@hotmail.com", "djvhrhc", "tt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("juan@hotmail.com", "dkejd", "tt"),
				String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void t24_notSamePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordIncorrect = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("paco@hotmail.com", "djfhr", "djfhr"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("pepe@gmail.com", "djvhrhc", "djvhrhc"), String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("carmen@yahoo.com", "dkejd", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));
	}

	@Test
	public void z1_emailChangeCorrect() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";

		String correctChange = "{\"participant\":\"paco@participant.es\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("pepe@participant.es", "12345", "paco@participant.es"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));

		correctChange = "{\"participant\":\"pepe@participant.es\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("paco@participant.es", "12345", "pepe@participant.es"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void z2_correctPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		
		String correctPassword = "{\"participant\":\"jualo@participant.es\",\"message\":\"contraseña actualizada correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("jualo@participant.es", "jualo123", "123456"), String.class);
		assertThat(response.getBody(), equalTo(correctPassword));
	}

	@Test
	public void z3_correctPasswordChangeXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ChangeInfoResponse><message>contraseÃ±a actualizada correctamente</message>"
				+ "<participant>jualo@participant.es</participant></ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("jualo@participant.es", "123456", "jualo123"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void z4_emailChangeCorrectXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ChangeInfoResponse><message>email actualizado correctamente</message>"
				+ "<participant>j@participant.es</participant></ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("jualo@participant.es", "jualo123", "j@participant.es"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
		
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("j@participant.es", "jualo123", "jualo@participant.es"), String.class);
	}

	// Cabecera HTTP para pedir respuesta en XML
	public class AcceptInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			HttpHeaders headers = request.getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
			return execution.execute(request, body);
		}
	}
}
