package Tools;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

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
                System.out.println("[INFO] Preparing Database file for being loaded into Memory...");

                generalJO = (JSONObject) parser.parse(reader);
                System.out.println("[SUCCESS] Database file successfully loaded into Memory!");
//                SaveToFile();

                Timer timer = new Timer();
                TimerTask task = new BackUpWordsDatabase();

//                timer.schedule(task, 60000, 300000);
                timer.schedule(task, 60000, 1800000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("[ERROR] Words Database corrupted or not created. Creating...");
                CreateSaveFile();
//                e.printStackTrace();
            }
        }

        private static class BackUpWordsDatabase extends TimerTask {
            public static int i = 0;

            public void run() {
                System.out.println("[INFO] [" + i + "] Preparing backup of Database...");
                JSONWordsDatabase.InitialJsonStuff.SaveToFile();
                i++;
            }
        }

        private static void CreateSaveFile() {
            if (fileLocation.exists()) {
                System.out.println("[INFO] Database file existed, backing up...");
                fileLocation.renameTo(new File(fileLocation.getName() + "_backedup"));
            }
            generalJO = new JSONObject();
            SaveToFile();
            init();
        }

        public static void SaveToFile() {
            try (FileWriter file = new FileWriter(fileLocation)) {
                JSONObject use = generalJO;
                file.write(use.toJSONString());
                file.flush();
                System.out.println("[SUCCESS] Saved Database!");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ConcurrentModificationException ex) {
                try {
                    Thread.sleep(5000);
                    SaveToFile();
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class WordRegister {
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

            if (generalJO.get("details") != null)
                detailsOBJ = (JSONObject) generalJO.get("details");

            if (useTOS.equalsIgnoreCase(""))
                return;

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
                detailsOBJ.put(useWord, detailsContentOBJ);
                generalJO.put("details", detailsOBJ);


//                JSONWordsDatabase.InitialJsonStuff.SaveToFile();

//                System.out.println("Registered Word! | " + useWord + "  ->   " + useTOS);

                if(!lastRegisteredWord.equalsIgnoreCase(useWord)){
                    System.out.println();
                    System.out.format("%14s %15s %20s", "              ", "Word", "ToS");
                    System.out.println();
                }
                System.out.format("%14s %15s %20s", "Registered ToS", useWord, useTOS);
                    System.out.println();

//
//                System.out.printf("%40s %20s", "Word", "Type Of Speech");
//                System.out.println();
//                System.out.printf("%5s %15s %20s", "[SYSTEM] Registered Word!", useWord, useTOS);
//                System.out.printf("%10s %30s %20s %5s %5s", "STUDENT ID", "EMAIL ID", "NAME", "AGE", "GRADE");
                lastRegisteredWord = useWord;
            }
        }
    }

    public static class WordDetails {
        private static JSONObject GetDetailsContent(String word) {
            JSONObject detailsOBJ = new JSONObject();
            JSONObject detailsContentOBJ = new JSONObject();

            if (generalJO.get("details") == null)
                return null;

            detailsOBJ = (JSONObject) generalJO.get("details");
            if (detailsOBJ.containsKey(word)) {
                detailsContentOBJ = (JSONObject) detailsOBJ.get(word);
            } else {
                detailsContentOBJ.put("bad", (long) 0);
                detailsContentOBJ.put("neutral", (long) 0);
                detailsContentOBJ.put("tags", new JSONArray());
                detailsOBJ.put(word.toLowerCase(), detailsContentOBJ);
                generalJO.put("details", detailsOBJ);
//                JSONWordsDatabase.InitialJsonStuff.SaveToFile();
            }
            return detailsContentOBJ;
        }

        public static class WordRating {

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

                detailsOBJ.put(word.toLowerCase(), detailsContentOBJ);
                generalJO.put("details", detailsOBJ);
//                JSONWordsDatabase.InitialJsonStuff.SaveToFile();
            }

            public static long GetWordsRating(String word) {
                try {
                    JSONObject detailContentOBJ = GetDetailsContent(word);

                    long neutral = (Long) detailContentOBJ.get("neutral");
                    long bad = (Long) detailContentOBJ.get("bad");
                    long overall = neutral + bad;
                    System.out.println("neutral - " + neutral);
                    System.out.println("bad - " + bad);
                    System.out.println("overall - " + overall);


                    if (overall == 0)
                        return 50;

                    if (bad == 0 && neutral > 0)
                        return 0;

                    return (100 / overall) * bad;
                } catch (ClassCastException e) {
                    return 0;
                }
            }
        }

        public static class WordTagging {
            static JSONObject detailsContentOBJ = new JSONObject();

            private static boolean AlreadyGotTag(String word, String tag) {
                JSONObject detailsOBJ;
                detailsContentOBJ = GetDetailsContent(word);
                JSONArray tagsArray = (JSONArray) detailsContentOBJ.get("tags");
                return tagsArray.contains(tag);
            }

            public static void RegisterWordsTag(String word, String tag) {
                if (generalJO.get("details") == null)
                    return;
                if (AlreadyGotTag(word, tag))
                    return;
                JSONObject detailsOBJ;
                JSONArray tagsArray = (JSONArray) detailsContentOBJ.get("tags");
                detailsOBJ = (JSONObject) generalJO.get("details");

                tagsArray.add(tag);

                detailsOBJ.put(word.toLowerCase(), detailsContentOBJ);
                generalJO.put("details", detailsOBJ);
//                JSONWordsDatabase.InitialJsonStuff.SaveToFile();
            }

            public static JSONArray GetWordsTags(String word) {
                try {
                    detailsContentOBJ = GetDetailsContent(word);
                    JSONArray tagsArray = (JSONArray) detailsContentOBJ.get("tags");

                    if (tagsArray == null)
                        return new JSONArray();

                    return tagsArray;
                } catch (ClassCastException e) {
                    return new JSONArray();
                }
            }
        }

    }


}
