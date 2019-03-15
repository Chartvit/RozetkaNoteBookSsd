package run.page_objects;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import run.locators.NoteBookLocatorsBase;

import java.util.ArrayList;
import java.util.List;

import static run.test_objects.NoteBookTestsBase.wd;

public class NoteBookPageBase
{
	NoteBookLocatorsBase locators = new NoteBookLocatorsBase();

	@Step("Wait for presence and focus element 'Ноутбуки и компьютеры'")
	public void chooseBlockNotebooksAndComputers()
	{
		wd.waitForPresence(locators.blockNotebooksAndComputers);
		wd.mouseOver(locators.blockNotebooksAndComputers, 0,0,"");
	}

	@Step("Wait for presence and click element 'Ноутбуки")
	public void chooseBlockNotebooks()
	{
		wd.waitForPresence(locators.blockNotebooks);
		wd.click(locators.blockNotebooks);
	}

	@Step("Wait for presence and click element 'Ноутбуки с SSD")
	public void chooseBlockNotebooksWithSsd()
	{
		wd.waitForPresence(locators.blockNoteBooksWithSsd);
		wd.click(locators.blockNoteBooksWithSsd);
	}

	@Step("Wait for presence and click element 'Отобразить списком")
	public void clickSwitchViewList()
	{
		wd.waitForPresence(locators.blockSwitchViewList);
		wd.click(locators.blockSwitchViewList);
		wd.waitForPresence(locators.blockNoteBookItemInListView);
	}

	@Step("Wait for presence and click element 'Сравнение товаров")
	public void clickCompageGoodsHeaderLink()
	{
		wd.waitForPresence(locators.blockCompareGoodsHeaderLink);
		wd.click(locators.blockCompareGoodsHeaderLink);
		wd.waitForPresence(locators.buttonCompareCurrentGoods);
	}

	@Step("Wait for presence and click element 'Только Отличия")
	public void clickShowOnlyDifferences()
	{
		wd.waitForPresence(locators.blockShowOnlyDifferences);
		wd.click(locators.blockShowOnlyDifferences);
		wd.waitForPresence(locators.listOfTheComparingItems);
	}

	@Step("Wait for presence and click element 'Сравнить эти товары")
	public void clickCompareCurrentChosenGoods()
	{
		wd.waitForPresence(locators.buttonCompareCurrentGoods);
		wd.click(locators.buttonCompareCurrentGoods);
		wd.waitForPresence(locators.blockComparingGoods);
	}

	@Step("Wait for presence and click element 'Отобразить списком")
	public void clickAddNotebookToCompareByNumber(Integer p_numberOfNotebooks)
	{
		wd.waitForPresence(locators.blockAddtoCompareByListNumber(p_numberOfNotebooks));
		wd.click(locators.blockAddtoCompareByListNumber(p_numberOfNotebooks));
		wd.waitForPresence(locators.blockNoteBookItemInListView);
		wd.wait(2);
	}

	@Step("Wait for presence and click element 'Отобразить списком")
	public String getHeaderCompareCounter()
	{
		wd.waitForPresence(locators.blockHeaderCompareCounter);
		return wd.getText(locators.blockHeaderCompareCounter,"Get the number of comparing goods");
	}

	@Step("Get text data of each parameter")
	public List<String> getTextComparingData(Integer p_numberOfNotebooks)
	{
		wd.waitForPresence(locators.listOfTheComparingItems);
		List<WebElement> elements = wd.getWebElements(locators.comparingItemOfTheGood(p_numberOfNotebooks), "Current number of comparing parameters in the list");
		List<String> textData = new ArrayList();
		for (WebElement l_element : elements)
			textData.add(l_element.getText());
		return textData;
	}

	@Step("Get amount of comparing parameters")
	public Integer getAmoutOfComparingParameters()
	{
		wd.waitForPresence(locators.listOfTheComparingItems);
		List<WebElement> elements = wd.getWebElements(locators.listOfTheComparingItems, "Current number notebooks in the list");
		return elements.size();
	}

	@Step("Get amount of different parameters")
	public Integer getOnlyDifferences()
	{
		wd.waitForPresence(locators.listOfOnlyDifferences);
		List<WebElement> elements = wd.getWebElements(locators.listOfOnlyDifferences, "Current number of differences in the list");
		return elements.size();
	}

	@Step("Get element data 'Количество элементов в текущем списке")
	public Integer getCurrentListOfNoteBooks()
	{
		List<WebElement> elements = wd.getWebElements(locators.blockNoteBookItemInListView, "Current number notebooks in the list");
		return elements.size();
	}

}