package Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Tools.WebsiteTools.*;

public class Tags {

    public static List<String> getTags(String word) throws IOException {

        List<String> wordsList = GetElementsByClass("https://www.ldoceonline.com/dictionary/" + word.toLowerCase() + "_2", "EXAMPLE");

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < wordsList.size(); i++) {
            String[] words = wordsList.get(i).split(" ");

            for (int j = 0; j < words.length; j++) {
                String replace = words[j].replaceAll("[^A-Za-z]", "");
                if(!replace.equalsIgnoreCase("")) {
                    tags.add(replace.toLowerCase());
                }
            }
        }

        return tags;
    }

    public static List<String> getMutualWords(String sentence) {
        List<String> words = Arrays.asList(sentence.toLowerCase().split(" "));

        if(words.size() >= 2) {

            List<List<String>> compareList = new ArrayList<>();

            //List
            //      List
            //          tags1
            //      List
            //          tags2

            for (int i = 0; i < words.size(); i++) {
                compareList.add(JSONWordsDatabase.WordDetails.WordTagging.GetWordsTags(words.get(i)));
            }
            System.out.println("HI");
           /* for (int i = 0; i < compareList.size(); i++) {
                for (int j = 0; j < compareList.get(i).size(); j++) {
                    compareList.get(i).equals(compareList.get(i).get(j))
                }
            }*/

        }else {
            return null;
        }

        List<String> mutuals = new ArrayList<>();

        return mutuals;
    }

    //TODO onMessage
    //TODO |
    //TODO content -> split per word
    //TODO |
    //TODO fori word -> get tags & compare to the other words
    //TODO |
    //TODO sendMessage(List top mutual tags)
    //stonks peter
    //stonks: souiehf pouhrgpiueshg so0egouhweioup
    //peter: ieuysbg 0-[s9thie50h pouhrgpiueshg
}
