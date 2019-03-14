package run.locators;

import org.openqa.selenium.By;

public class NoteBookLocatorsBase
{
	public By blockNotebooksAndComputers  = By.xpath("//*[contains(@class,'menu-categories_type_main')]//a[contains(@href,'computers-notebooks')]");
	public By blockNotebooks  = By.xpath("//*[contains(@class,'background_gray') and contains(text(),'Ноутбуки')]");
	public By blockNoteBooksWithSsd  = By.xpath("//*[contains(@class,'portal-notebooks')]//*[contains(text(),'Ноутбуки с SSD')]");
	public By blockSwitchViewList  = By.xpath("//*[contains(@class,'view-list-btn')]");

	public By blockNoteBookItemInListView = By.xpath("//*[contains(@class,'g-i-list available')]");
	public By blockHeaderCompareCounter = By.xpath("//*[contains(@id,'comparison-header')]//span[contains(text(),'Сравнение')]/following::span[1]");
	public By blockCompareGoodsHeaderLink = By.xpath("//div[(@id='comparison')]//*[contains(@href,'comparison')]");
	public By buttonCompareCurrentGoods = By.xpath("//*[contains(@class,'btn-link-to-compare')]//*[contains(text(),'Сравнить эти товары')]");
	public By blockComparingGoods = By.xpath("//div[(@name='comparison_container')]");
	public By listOfTheComparingItems = By.xpath("//div[contains(@class,'comparison-t-cell-first')]");
	public By blockShowOnlyDifferences = By.xpath("//div[(@id='compare-menu')]//li[2]");
	public By listOfOnlyDifferences = By.xpath("//div[(@name='different')]");

	public By blockAddtoCompareByListNumber(Integer p_value)
	{
		return By.xpath("//*[contains(@class,'g-i-list available')][" + p_value + "]//span[contains(@class,'g-compare')][1]");
	}

	public By comparingItemOfTheGood(Integer p_value)
	{
		return By.xpath("//div[contains(@class,'comparison-t-cell-first')]/following::span["+ p_value + "]");
	}
}
