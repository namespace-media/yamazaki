package Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Tools.WebsiteTools.getRawTagsOfURL;

public class Tags {

    public static List<String> getTags(String word) throws IOException {

        List<String> wordsList = WebsiteTools.GetElementsByTag("http://wordnetweb.princeton.edu/perl/webwn?s=" + word, "li");

        List<String> aRawList = getRawTagsOfURL("http://wordnetweb.princeton.edu/perl/webwn?s=" + word, "li");
        List<String> bRawList = getRawTagsOfURL("http://wordnetweb.princeton.edu/perl/webwn?s=" + word, "li");

        List<String> parsedTags = new ArrayList<String>();
        for (int i = 0; i < wordsList.size(); i++) {
            String list;

            list = wordsList.get(i);
            list =  list.replace("::marker", "")
                    .replaceAll("\\(.*?\\)", "")
                    .replace(aRawList.get(i).toString(), "")
                    .replace(bRawList.get(i).toString(), "")
                    .replace("<i>", "")
                    .replace("</i>", "")
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    .replace("S:", "");
            String[] tags = list.split(" ");
            if(!(tags.length == 0)) {
                for (int a = 0; a < tags.length; a++) {
                    parsedTags.add(a, tags[a].replaceAll("[^A-Za-z0-9 ]", ""));
                }
            }
        }

//        System.out.println(word + " {");
//        for (int i = 0; i < parsedTags.size(); i++) {
//            if(!parsedTags.get(i).isEmpty()) {
//                System.out.println("    " + parsedTags.get(i));
//            }
//        }
//        System.out.println("}");

        return parsedTags;
    }

}
