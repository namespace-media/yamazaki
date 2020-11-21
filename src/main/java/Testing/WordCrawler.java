package Testing;

import Tools.JSONWordsDatabase;
import Tools.Tags;
import Tools.WebsiteTools;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class WordCrawler {

    private static boolean stopCrawler = false;
    private static boolean restartCrawler = false;
    private static String restartCrawlerURL = "";

    // Interface
    public static void beginCatchingWords(String url) throws IOException {
        if (url.equalsIgnoreCase("")) {
            url = getRandomWiki();
        }
        List<String> words = gatherWebsiteWords(url);
        int size = words.size();
        System.out.println("[INFO] Beginning to crawl for words at: " + url + "\n" +
                "[INFO] URL contains " + size + " words to catch...");

        for (int i = 0; i < size; i++) {
            if(stopCrawler) {
                System.out.println("[WARNING] Crawler has been manually stopped!");
                stopCrawler = false;
                break;
            }
            if(restartCrawler) {
                System.out.println("[WARNING] Crawler has been manually restarted!");
                restartCrawler = false;
                beginCatchingWords(restartCrawlerURL);
                break;
            }
            if ((i + 1) == words.size()) {
                i = 0;
                String word = JSONWordsDatabase.lastRegisteredWord;
                if (word.equalsIgnoreCase("")) {
                    System.out.println("[WARNING] Last word has been empty! Choosing a random Word...");
                    beginCatchingWords(getRandomWiki());
                } else {
                    beginCatchingWords(getWordWiki(word));
                }
                break;
            }
            if (!words.get(i).equalsIgnoreCase(""))
                declareToS(words.get(i));
        }
    }

    public static void stopCatchingWords() throws IOException {
        stopCrawler = true;
    }

    public static void changeCatchingWordsURL(String url) throws IOException {
        restartCrawlerURL = url;
        System.out.println("[INFO] Crawler-URL has been changed to " + url);
        restartCrawler = true;
    }

    public static String changeCatchingWordsURLWiki(String word) throws IOException {
        String url = getWordWiki(word);
        changeCatchingWordsURL(url);
        return url;
    }


    // Tools

    public static String getRandomWiki() {
        JSONArray nounsArray = JSONWordsDatabase.WordRegister.GetRegisteredArray("noun");
        if (nounsArray == null)
            return "https://namespace.media/";

        int rnd = new Random().nextInt(nounsArray.size());
        return "https://en.wikipedia.org/wiki/" + nounsArray.get(rnd);
    }

    public static String getWordWiki(String word) {
        return "https://en.wikipedia.org/wiki/" + word;
    }

    private static List<String> gatherWebsiteWords(String url) throws IOException {
        Document doc = WebsiteTools.getDoc(url);
        try {
            if (doc == null) {
                return new ArrayList<>();
            }
            String allText = doc.text();
            String[] splitText = allText.split("[^A-Za-z0-9][\\s]*");
            List<String> allWords = new ArrayList<String>();
            for (int i = 0; i < splitText.length; i++) {
                allWords.add(splitText[i].replaceAll("[^A-Za-z]", ""));
            }
            return allWords;
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

//    private static void declareToS(String word) throws IOException {
////        List<String> wordsList = getTagsOfURL("http://wordnetweb.princeton.edu/perl/webwn?s=" + word, "h3");
//        System.out.println("URL - https://www.google.com/search?&q=" + word.toLowerCase() + "+definition");
//        List<String> wordsList = GetElementsByCSSQ("https://www.google.com/search?&q=" + word.toLowerCase() + "+definition", ".pgRvse.vdBwhd");
//        System.out.println("HEHE - " + wordsList.toString());
//            String ToS = wordsList.get(0);
//        System.out.println("THIS IS - " + ToS);
//            try {
//                JSONWordsDatabase.WordRegister.RegisterWord(ToS, word);
//
//                List<String> tags = Tags.getTags(word);
//
//                for (int j = 0; j < tags.size(); j++) {
//                    if (!tags.get(j).equalsIgnoreCase(""))
//                        JSONWordsDatabase.WordDetails.WordTagging.RegisterWordsTag(word, tags.get(j));
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//    }

    private static void declareToS(String word) throws IOException {
//        List<String> wordsList = getTagsOfURL("http://wordnetweb.princeton.edu/perl/webwn?s=" + word, "h3");
        List<String> wordsList = WebsiteTools.GetElementsByCSSQ("https://www.ldoceonline.com/dictionary/" + word.toLowerCase() + "_2", ".dictentry .POS");
        List<String> trademarksList = WebsiteTools.GetElementsByCSSQ("https://www.ldoceonline.com/dictionary/" + word.toLowerCase() + "_2", ".dictentry .REGISTERLAB");
        for (int i = 0; i < trademarksList.size(); i++) {
            if (trademarksList.get(i).equalsIgnoreCase("trademark"))
                wordsList.add(trademarksList.get(i));
        }
        if (wordsList.size() == 0 || wordsList.toString().equalsIgnoreCase("[]")) {
            wordsList.add("unidentified");
        }

        for (int i = 0; i < wordsList.size(); i++) {
            String ToS = wordsList.get(i);
            try {
                JSONWordsDatabase.WordRegister.RegisterWord(ToS.replaceAll("[^A-Za-z]", ""), word);

                List<String> tags = Tags.getTags(word);

                for (int j = 0; j < tags.size(); j++) {
                    if (!tags.get(j).equalsIgnoreCase(""))
                        JSONWordsDatabase.WordDetails.WordTagging.RegisterWordsTag(word, tags.get(j));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
