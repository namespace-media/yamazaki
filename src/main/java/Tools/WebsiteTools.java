package Tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebsiteTools {

    public static List<String> getTagsOfURL(String url, String tag) throws IOException {
        Elements links = getDoc(url).getElementsByTag(tag);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.text());
        }
        return wordsList;
    }

    public static Document getDoc(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}
