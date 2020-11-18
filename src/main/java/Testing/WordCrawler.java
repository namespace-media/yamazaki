package Testing;

import Tools.JSONWordsDatabase;
import Tools.Tags;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordCrawler {

    // Interface
    public static void beginCatchingWords(String url) throws IOException {
        if(url.equalsIgnoreCase("")) {
            url = getRandomWiki();
        }
        List<String> words = gatherWebsiteWords(url);
        int size = words.size();
        System.out.println("Beginning to catch words at: " + url);

        for (int i = 0; i < size; i++) {
            if ((i+1) == words.size()) {
                i = 0;
                String word = JSONWordsDatabase.lastRegisteredWord;
                if(word.equalsIgnoreCase("")) {
                    System.out.println("[WARNING] Last word has been empty! Choosing a random Word...");
                }
                beginCatchingWords(getRandomWiki());
                break;
            }
            declareToS(words.get(i));
        }
    }


    // Tools

    private static String getRandomWiki() {
        JSONArray nounsArray = JSONWordsDatabase.WortRegister.GetRegisteredArray("noun");
        int rnd = new Random().nextInt(nounsArray.size());
        return "https://en.wikipedia.org/wiki/" + nounsArray.get(rnd);
    }

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
                JSONWordsDatabase.WortRegister.RegisterWord(ToS, word);

                List<String> tags = Tags.getTags(word);

                for (int j = 0; j < tags.size(); j++) {
                    //JSONWordsDatabase.RegisterTags(ToS, word, tags.get(j));
                }

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
