package Tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebsiteTools {


    public static Document getDoc(String url) throws IOException {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> GetElementsByClass(String url, String classS) throws IOException {
        Elements links = getDoc(url).getElementsByClass(classS);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.text());
        }
        return wordsList;
    }

    public static List<String> GetElementsByTag(String url, String tag) throws IOException {
        Elements links = getDoc(url).getElementsByTag(tag);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.text());
        }
        return wordsList;
    }

    public static List<String> GetElementsByCSSQ(String url, String cssQ) {
        try {
            Elements links = getDoc(url).select(cssQ);
            List<String> wordsList = new ArrayList<String>();
            for (Element link : links) {
                wordsList.add(link.text());
            }
            return wordsList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public static List<String> getRawTagsOfURL(String url, String tag) throws IOException {
        Elements links = getDoc(url).getElementsByTag(tag);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.toString());
        }
        return wordsList;
    }

}
