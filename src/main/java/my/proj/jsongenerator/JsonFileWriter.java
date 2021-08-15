package my.proj.jsongenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JsonFileWriter {

    private FileWriter file;
    JSONObject obj = new JSONObject();
    JSONObject tableConfig = new JSONObject();
    JSONArray header = new JSONArray();
    JSONArray categories = new JSONArray();
    int index = 1;

    private String generateFileIndex() {
        String fileIndex;
        if (index < 10){
            fileIndex = "00"+index;
        }
        else if(index < 100){
            fileIndex = "0"+index;
        }
        else{
            fileIndex = ""+index;
        }
        return fileIndex;
    }

    public void writeToFile() {
        generateHeader();
        generateCategories("entrance", "ඇතුල්වීමේ ගී", "EntranceHymn");
        generateCategories("meditation", "මෙනෙහි කිරීමේ ගී", "MeditationHymn");
        generateCategories("offering", "පිදීමේ ගී", "OfferingHymn");
        generateCategories("communion", "බොදුන් ගී", "CommunionHymn");
        generateCategories("finishing","සමුගැනීමේ ගී","FinishingHymn");
        generateCategories("singingMass","දිව්\u200Dය පූජා සඳහා ගයනා ගී","SingingMassHymn");
        generateCategories("jesus","ජේසුතුමන්ට ගී","JesusHymn");
        generateCategories("eucharist","සත්ප්\u200Dරසාදීය ගී","EucharistHymn");
        generateCategories("mary","දේව මෑණියන්ට ගී","MaryHymn");
        generateCategories("holySpirit","ශුද්ධාත්මයාණන්ට ගී","HolySpiritHymn");
        generateCategories("christmas","නත්තල් ගී","ChristmasHymn");
        generateCategories("saints","සාන්තුවර ගී","SaintsHymn");
        generateCategories("various","විවිධ ගී","VariousHymn");



        try {
            file = new FileWriter("src/main/resources/json/hymns.json");
            file.write(new ObjectMapper().writeValueAsString(obj));
        } catch (
                IOException e) {
            e.printStackTrace();

        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void generateCategories(String categoryName, String displayText, String fileName) {
        JSONObject category = new JSONObject();
        category.put("category", categoryName);
        category.put("displayText", displayText);
        category.put("hymnList", generateHymnList(fileName));
        categories.add(category);
        obj.put("categories", categories);
    }

    private JSONArray generateHymnList(String fileName){
        JSONArray hymnList = new JSONArray();
        try {
            File myObj = new File("src/main/resources/text/"+fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String name = myReader.nextLine();
                String fileIndex = generateFileIndex();
                hymnList.add(generateHymn(index, name, fileIndex));
                index++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return hymnList;
    }

    private JSONObject generateHymn(int index, String name, String fileIndex) {
        JSONObject hymn = new JSONObject();
        hymn.put("index", index);
        hymn.put("name", name);
        hymn.put("audioLocations", generateList("/web/HYMNS/SINHALA/KG/AUDIO/"+fileIndex+".mp3"));
        hymn.put("mp3Location", generateList("/web/HYMNS/SINHALA/KG/MP3/"+fileIndex+".mp3"));
        hymn.put("midiLocation", generateList("/web/HYMNS/SINHALA/KG/MIDI/"+fileIndex+".mid"));
        hymn.put("pdf", generateList("/web/HYMNS/SINHALA/KG/PDF/"+fileIndex+".pdf"));
        return hymn;
    }

    private JSONArray generateList(String filePathName){
        JSONArray audioLocationList = new JSONArray();
        audioLocationList.add(filePathName);
        return audioLocationList;
    }

    private void generateHeader() {
        header.add("අංකය");
        header.add("ගීතිකාව");
        header.add("audio");
        header.add("mp3");
        tableConfig.put("header", header);
        obj.put("tableConfig", tableConfig);
    }

}
