package fi.eis.applications.dom4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;

@SuppressWarnings("unchecked")
public class Example {
	public static void main(String args[]) throws Exception {

		System.out.println("Running");

		// Create a null Document Object
		Document theXML = null;

		// Get the document of the XML and assign to Document object
		theXML = fromUrl("http://www.iii.co.uk/rss/news/cotn%3aAAU.L.xml");

		// Place the root element of theXML into a variable
		List<? extends Node> items = (List<? extends Node>) theXML
				.selectNodes("//rss/channel/item");

		// RFC-dictated date format used with RSS
		DateFormat dateFormatterRssPubDate = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

		// today started at this time
		DateTime timeTodayStartedAt = new DateTime().withTimeAtStartOfDay();

		for (Node node : items) {
			String pubDate = node.valueOf("pubDate");
			DateTime date = new DateTime(dateFormatterRssPubDate.parse(pubDate));
			if ((date).isAfter(timeTodayStartedAt)) {
				// it's today, do something!
				System.out.println("Today: " + date);
			} else {
				System.out.println("Not today: " + date);
			}
		}

	}

	public static Document fromUrl(String url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}
}