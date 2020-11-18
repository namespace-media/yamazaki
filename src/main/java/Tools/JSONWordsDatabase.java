package Tools;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JSONWordsDatabase {

    private static final File fileLocation = new File("words.json");

    private static JSONObject generalJO = new JSONObject();
    public static String lastRegisteredWord = "";

    public static class InitialJsonStuff {
        public static void init() {
            if (!fileLocation.exists()) {
                CreateSaveFile();
                return;
            }
            JSONParser parser = new JSONParser();
            try (Reader reader = new FileReader(fileLocation)) {

                generalJO = (JSONObject) parser.parse(reader);
                SaveToFile();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("Error: words JSON corrupted or not created. Creating...");
                CreateSaveFile();
//                e.printStackTrace();
            }
        }

        private static void CreateSaveFile() {
            if (fileLocation.exists()) {
                System.out.println("Info: words JSON file existed, backing up...");
                fileLocation.renameTo(new File(fileLocation.getName() + "_backedup"));
            }
            generalJO = new JSONObject();
            SaveToFile();
            init();
        }

        protected static void SaveToFile() {
            try (FileWriter file = new FileWriter(fileLocation)) {
                file.write(generalJO.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class WortRegister {
        public static JSONArray GetRegisteredArray(String ToS) {
            if (generalJO.containsKey(ToS)) {
                JSONArray wordsTOS = (JSONArray) generalJO.get(ToS);
                return wordsTOS;
            }
            return null;
        }

        public static boolean WordRegistered(String ToS, String word) throws ParseException {
            if (generalJO.containsKey(ToS)) {
                JSONArray wordsTOS = (JSONArray) generalJO.get(ToS);
                for (int i = 0; i < wordsTOS.size(); i++) {
                    if (wordsTOS.get(i).toString().equalsIgnoreCase(word))
                        return true;
                }
            }
            return false;
        }

        public static void RegisterWord(String ToS, String word) throws ParseException {
            String useTOS = ToS.toLowerCase();
            String useWord = word.toLowerCase();
            JSONObject detailsOBJ = new JSONObject();
            JSONObject detailsContentOBJ = new JSONObject();
            if (useTOS.contains("your search did not return any results"))
                useTOS = "unspecified";

            if (generalJO.get("details") != null)
                detailsOBJ = (JSONObject) generalJO.get("details");

            if (!WordRegistered(useTOS, useWord)) {
                JSONArray wordsTOS = new JSONArray();
                if (generalJO.containsKey(useTOS)) {
                    wordsTOS = (JSONArray) generalJO.get(useTOS);
                }


                wordsTOS.add(useWord);
                generalJO.put(useTOS, wordsTOS);


                detailsContentOBJ.put("bad", 0);
                detailsContentOBJ.put("neutral", 0);
                detailsContentOBJ.put("tags", new JSONArray());
                detailsOBJ.put(word, detailsContentOBJ);
                generalJO.put("details", detailsOBJ);


                JSONWordsDatabase.InitialJsonStuff.SaveToFile();

                System.out.println("Registered Word! | " + useTOS + " -> " + useWord + "\n");
                lastRegisteredWord = useWord;
            }
        }
    }

    public static class WordRating {
        private static JSONObject GetDetailsContent(String word) {
            JSONObject detailsOBJ = new JSONObject();
            JSONObject detailsContentOBJ = new JSONObject();

            if (generalJO.get("details") == null)
                return null;

            detailsOBJ = (JSONObject) generalJO.get("details");
            if (detailsOBJ.containsKey(word)) {
                detailsContentOBJ = (JSONObject) detailsOBJ.get(word);
                System.out.println("1 - Word details does exist!");
            } else {
                System.out.println("2 - Word details didn't exist!");
                detailsContentOBJ.put("bad", (long) 0);
                detailsContentOBJ.put("neutral", (long) 0);
                detailsContentOBJ.put("tags", new JSONArray());
                detailsOBJ.put(word, detailsContentOBJ);
                generalJO.put("details", detailsOBJ);
                JSONWordsDatabase.InitialJsonStuff.SaveToFile();
            }
            return detailsContentOBJ;
        }

        public static void RateWord(String word, boolean neutral) {
            if (generalJO.get("details") == null)
                return;
            JSONObject detailsContentOBJ = GetDetailsContent(word);
            JSONObject detailsOBJ = new JSONObject();
            detailsOBJ = (JSONObject) generalJO.get("details");

            if (neutral) {
                System.out.println("3 - Word is neutral!");
                Long neutralINT = (Long) detailsContentOBJ.get("neutral");
                System.out.println("4 - Word is neutral! - Was " + neutralINT);
                neutralINT++;
                detailsContentOBJ.put("neutral", neutralINT);
                System.out.println("4 - Word is neutral! - Now is " + detailsContentOBJ.get("neutral"));
            } else {
                Long badINT = (Long) detailsContentOBJ.get("bad");
                System.out.println("4 - Word is bad! - Was " + badINT);
                badINT++;
                detailsContentOBJ.put("bad", badINT);
                System.out.println("4 - Word is bad! - Now is " + detailsContentOBJ.get("bad"));
            }

            detailsOBJ.put(word, detailsContentOBJ);
            generalJO.put("details", detailsOBJ);
            JSONWordsDatabase.InitialJsonStuff.SaveToFile();
        }

        public static double getWordsRating(String word) {
            try {
                JSONObject detailContentOBJ = GetDetailsContent(word);

                Long neutral = (Long) detailContentOBJ.get("neutral");
                Long bad = (Long) detailContentOBJ.get("bad");
                double overall = neutral + bad;
                System.out.println("neutral - " + neutral);
                System.out.println("bad - " + bad);
                System.out.println("overall - " + overall);



                if (overall == 0)
                    return 0;

                double calced = 1.0 / overall;

                return calced;
            } catch (ClassCastException e) {
                e.printStackTrace();
                System.out.println("ne");
                return 0;
            }
        }
    }


}
