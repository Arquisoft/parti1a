package cucmber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

// El nombre del paquete debe ser diferente de cucumber porque si no, no se lanza bien

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class CucumberMainTest {
}
