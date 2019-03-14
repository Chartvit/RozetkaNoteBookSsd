package run.test_objects;

import org.apache.log4j.Logger;
import org.testng.Assert;
import run.Browser;
import run.DriverHelper;
import run.page_objects.NoteBookPageBase;

public class NoteBookTestsBase
{
	NoteBookPageBase page = new NoteBookPageBase();
	final static org.apache.log4j.Logger logger = Logger.getLogger(NoteBookTestsBase.class);
	String url = "https://rozetka.com.ua/";

	public void chooseNoteBookWithSsd( Integer p_numberOfNotebooks)
	{
		page.chooseBlockNotebooksAndComputers();
		page.chooseBlockNotebooks();
		page.chooseBlockNotebooksWithSsd();
		page.clickSwitchViewList();
		logger.info("Found "+ page.getCurrentListOfNoteBooks() + " notebooks in the current list view");

		// add notebooks to compare and verify the icon after action
		for(int i = 1 ; i<= p_numberOfNotebooks; i++)
		{
			page.clickAddNotebookToCompareByNumber(i);
			Assert.assertTrue(i == Integer.valueOf(page.getHeaderCompareCounter()), "Notebook has successfully added to comparing list");
		}

		page.clickCompageGoodsHeaderLink();
		page.clickCompareCurrentChosenGoods();

		int parametersCount = page.getAmoutOfComparingParameters();
		int counter = 0;

		// get differences
		for(int i=1; i<parametersCount; i++)
		{
			if(!page.getTextComparingData(p_numberOfNotebooks).get(i).equals(page.getTextComparingData(p_numberOfNotebooks-1).get(i)))
			{
				System.out.println(page.getTextComparingData(p_numberOfNotebooks).get(i));
				System.out.println(page.getTextComparingData(p_numberOfNotebooks-1).get(i));
				counter +=1;
				System.out.println("Current number of differences is => " + counter);
			}
		}
		// select only differences assert final data
		page.clickShowOnlyDifferences();
		Assert.assertTrue(counter == page.getOnlyDifferences(), "Number of differences is equal");
	}

	public static DriverHelper wd = new DriverHelper();

	protected static void startTS(String browser, String platform) throws Exception {
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> START TEST SUITE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		wd.run(browser, platform);
	}

	public static void endTS()
	{
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END TEST SUITE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		wd.stop();
	}

	public void initData(Browser p_data)
	{
		try
		{
			startTS(p_data.getValue(), "web");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		/************** START TEST SUITE **************/
		wd.openURL(url);
		wd.wait(3);
	}

}
