package state;

import studyGroup.*;
import utils.FileConverter;

import java.time.LocalDateTime;
import java.util.HashMap;

public final class CollectionStorage {

    private final static String path = "src/resources/collection.csv";

    public static CollectionStorage storage = new CollectionStorage();
    private static HashMap<String, StudyGroup> collection;
    private final LocalDateTime initDate;

    private CollectionStorage(){
        collection = FileConverter.getCollection(path);
        initDate = LocalDateTime.now();
    }

    public HashMap<String, StudyGroup> getCollection() {
        return collection;
    }

    public void setCollection(HashMap<String, StudyGroup> all) {
        collection.putAll(all);
    }

    public void setSubMap(String subBorder, HashMap<String, StudyGroup> map) {
        boolean isFounded = false;
        String[] keys = map.keySet().toArray(new String[0]);
        for (String key : keys){
            if (map.get(key).getName().equals(subBorder))
                isFounded = true;
            if (!isFounded)
                map.remove(key);
        }

        collection = map;
    }

    public void saveCollection(){
        FileConverter.saveCollection(collection, path);
    }

    public String getCollectionInfo(){
        return "Тип коллекции: " + HashMap.class + "\n"
                +"Дата инициализации: " + initDate + "\n"
                +"Количество элементов: " + collection.size();
    }
}
