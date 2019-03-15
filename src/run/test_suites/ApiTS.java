package run.test_suites;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import run.test_objects.ApiTests;


@Feature("ApiTest")
public class ApiTS
{

	ApiTests apiTests = new ApiTests();

	@Severity(SeverityLevel.CRITICAL)
	@Description("Get list of user via API test")
	@Test( priority = 1, enabled = true , description = "LIST USERS")
	public void getListOfUsers()
	{
		apiTests.getListOfUsers();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Register via API test")
	@Test( priority = 1, enabled = true , description = "POST REGISTER - SUCCESSFUL")
	public void postRegisterSuccessful()
	{
		apiTests.postRegisterSuccessful();
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Post Login via API test")
	@Test( priority = 1, enabled = true , description = "POST LOGIN - SUCCESSFUL")
	public void postLoginSuccessful()
	{
		apiTests.postRegisterSuccessful();
	}
}