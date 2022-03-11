package utils;

import studyGroup.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FileConverter {

    public static HashMap<String, StudyGroup> getCollection(String path){
        String file = readFile(path);
        String[] groups = file.trim().split("\n");

        StudyGroup.setCount(groups.length);

        HashMap<String, StudyGroup> collection = new HashMap<>();

        for (String group : groups) {
            String[] props = group.split(",");
            StudyGroup newGroup = convertToSG(props);
            collection.put(props[0], newGroup);
        }


        return collection;
    }

    public static void saveCollection(HashMap<String, StudyGroup> collection, String path){
        StringBuilder fileString = new StringBuilder();
        for (Map.Entry<String, StudyGroup> val : collection.entrySet()){
            String groupToFile = convertToStr(val.getValue(), val.getKey());
            fileString.append(groupToFile);
        }
        writeFile(path, fileString.toString());
    }

    public static String readFile(String path){

        StringBuilder file = new StringBuilder();
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(path))) {
            int data = in.read();
            char ch;
            while (data != -1) {
                ch = (char) data;
                file.append(ch);
                data = in.read();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "#fail#";
        }
        return file.toString();
    }

    private static StudyGroup convertToSG(String[] props){
        StudyGroup newGroup = new StudyGroup();

        newGroup.setId(Integer.parseInt(props[1]));
        newGroup.setName(props[2]);

        String[] coords = props[3].split(";");
        newGroup.setCoordinates(new Coordinates(Integer.parseInt(coords[0]), Float.parseFloat(coords[1])));

        newGroup.setCreationDate(LocalDateTime.parse(props[4]));
        newGroup.setStudentsCount(Long.parseLong(props[5]));
        newGroup.setAverageMark(Integer.parseInt(props[6]));
        newGroup.setFormOfEducation(FormOfEducation.valueOf(props[7]));
        newGroup.setSemesterEnum(Semester.valueOf(props[8]));

        if (props[9].equals("\"\""))
            newGroup.setGroupAdmin(null);
        else {
            Person admin = new Person();
            String[] adminProps = props[9].split(";");
            String[] adminLocation = adminProps[2].split(" ");

            Location location = new Location(Double.parseDouble(adminLocation[0]),
                    Integer.parseInt(adminLocation[1]), Double.parseDouble(adminLocation[2]));

            admin.setName(adminProps[0]);
            admin.setPassportID(adminProps[1]);
            admin.setLocation(location);

            newGroup.setGroupAdmin(admin);
        }

        return newGroup;
    }

    private static void writeFile(String path, String file){
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(file);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertToStr(StudyGroup group, String key){
        StringBuilder converted = new StringBuilder(key);
        converted.append(",");

        String[] toFile = new String[9];

        toFile[0] = group.getId() + ",";
        toFile[1] = group.getName() + ",";
        toFile[2] = group.getCoordinates().toString() + ",";
        toFile[3] = group.getCreationDate().toString() + ",";
        toFile[4] = group.getStudentsCount() + ",";
        toFile[5] = group.getAverageMark() + ",";
        toFile[6] = (group.getFormOfEducation() != null) ? group.getFormOfEducation().toString() + "," : "\"\"";
        toFile[7] = group.getSemesterEnum().toString() + ",";
        toFile[8] = (group.getGroupAdmin() != null) ? group.getGroupAdmin().toString() : "\"\"";

        for (String s : toFile) {
            converted.append(s);
        }

        converted.append("\n");

        return converted.toString();
    }
}
