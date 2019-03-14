package run.test_suites;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import run.Browser;
import run.test_objects.NoteBookDataProviders;
import run.test_objects.NoteBookTestsBase;

@Feature("NoteBookTS")
public class NoteBookTS
{
	NoteBookDataProviders data = new NoteBookDataProviders();
	NoteBookTestsBase test = new NoteBookTestsBase();
	Browser browser = Browser.CHROME;

	@DataProvider
	public Object[][] noteBookSsdData()
	{
		return data.noteBookSsdData();
	}

	@BeforeMethod
	public void initData()
	{
		test.initData(browser);
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("Choose NoteBook with SSD and assert output data")
	@Test(dataProvider = "noteBookSsdData", priority = 1, enabled = true)
	public void chooseNoteBookWithSsd(Integer p_numberOfNotebooks)
	{
		test.chooseNoteBookWithSsd(p_numberOfNotebooks);
	}

	@AfterMethod
	public void quit()
	{
		test.endTS();
	}
}