package Tools;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class WordsFile {

    private static final File fileLocation = new File("words.json");

    private static JSONObject generalJO = new JSONObject();

    public static class InitialJsonStuff {
        public static void init() {
            if (!fileLocation.exists()) {
                CreateSaveFile();
                return;
            }
            JSONParser parser = new JSONParser();
            try (Reader reader = new FileReader(fileLocation)) {

                generalJO = (JSONObject) parser.parse(reader);

                // loop array
                //JSONArray msg = (JSONArray) jsonObject.get("messages");
                //Iterator<Object> iterator = msg.iterator();
                //while (iterator.hasNext()) {
                //    System.out.println(iterator.next());
                //}
                SaveToFile();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
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

    public static JSONArray GetRegisteredArray(String ToS) throws ParseException {
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
        if(useTOS.contains("your search did not return any results"))
            useTOS = "unspecified";

        if (!WordRegistered(useTOS, useWord)) {
            JSONArray wordsTOS = new JSONArray();
            if (generalJO.containsKey(useTOS)) {
                wordsTOS = (JSONArray) generalJO.get(useTOS);
            }
            wordsTOS.add(useWord);
            generalJO.put(useTOS, wordsTOS);
            WordsFile.InitialJsonStuff.SaveToFile();

            System.out.println("Registered Word! | " + useTOS + " -> " + useWord + "\n");
        }
    }

}
