package stepDefinitions;

import cucumber.api.java8.En;
import cucumber.runtime.java.StepDefAnnotation;

@StepDefAnnotation
public class StepDefinitionLambda implements En{


	public StepDefinitionLambda() {

		Given("testlambda", () -> {
		    System.out.println("asd1");

		});

	}

	// Uncomment this below section to remove the warning

//	@Given("testlambda")
//	public void given1() {
//
//		System.out.println("asd2");
//	}



}
