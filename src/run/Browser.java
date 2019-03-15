package run;
import java.util.Locale;

/**
 * Created by Fly on 14.07.2015.
 */
public enum Browser
{
	FIREFOX("firefox"),
	CHROME("chrome"),
	IEXPLORER("IE"),
	SAFARI("safari"),
	OPERA("opera"),
	EDGE("Edge");

	public final String value;

	Browser(final String value)
	{
		this.value = value;
	}

	public static Browser getBrowser(String p_value)
	{
		return Browser.valueOf(p_value);
	}

	public static Browser getBrowserByName(String p_value)
	{
		return Browser.valueOf(p_value.toUpperCase(Locale.ENGLISH));
	}

	public String getValue()
	{
		return value;
	}
}
