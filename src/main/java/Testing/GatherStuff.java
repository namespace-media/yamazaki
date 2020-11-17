package Testing;

import Tools.WordsFile;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GatherStuff {

    // Interface
    public static void beginCatchingWords(String url) throws IOException {
        List<String> words = gatherWebsiteWords(url);

        for (int i = 0; i < words.size(); i++) {
            declareToS(words.get(i));
        }
    }


    // Tools
    private static List<String> gatherWebsiteWords(String url) throws IOException {
        Document doc = getDoc(url);
        String allText = doc.text();
        String[] splitText = allText.split("[^A-Za-z0-9][\\s]*");
        List<String> allWords = new ArrayList<String>();
        for (int i = 0; i < splitText.length; i++) {
            allWords.add(splitText[i].replaceAll("[^A-Za-z0-9 ]", ""));
        }
        return allWords;
    }

    private static void declareToS(String word) throws IOException {
        List<String> wordsList = getTagsOfURL("http://wordnetweb.princeton.edu/perl/webwn?s=" + word, "h3");
        for (int i = 0; i < wordsList.size(); i++) {
            String ToS = wordsList.get(i);
            try {
                WordsFile.RegisterWord(ToS, word);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> getTagsOfURL(String url, String tag) throws IOException {
        Elements links = getDoc(url).getElementsByTag(tag);
        List<String> wordsList = new ArrayList<String>();
        for (Element link : links) {
            wordsList.add(link.text());
        }
        return wordsList;
    }







    private static Document getDoc(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}
