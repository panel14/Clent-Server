package state;

import server.service.DataBase;
import studyGroup.*;
import utils.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class CollectionStorage {

    public static CollectionStorage storage = new CollectionStorage();
    private static HashMap<String, StudyGroup> collection;
    private final LocalDateTime initDate;

    private CollectionStorage(){
        collection = DataBase.getCollection();
        initDate = LocalDateTime.now();
    }

    public synchronized HashMap<String, StudyGroup> getAllCollection() {
        if (collection.isEmpty())
            collection = DataBase.getCollection();
        return collection;
    }

    public static synchronized void supplementAllCollection(HashMap<String, StudyGroup> all) {
        collection.putAll(all);
    }

    public synchronized HashMap<String, StudyGroup> getUserCollection(User user){
        HashMap<String, StudyGroup> userCol = getAllCollection();
        return userCol.entrySet().stream()
                .filter(x -> x.getValue().getOwner().equals(user.login)).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));
    }

    public static synchronized void saveCollection(){
        DataBase.saveCollection(collection);
    }

    public synchronized HashMap<String, StudyGroup> setSubMap(String subBorder, HashMap<String, StudyGroup> map) {
        boolean isFounded = false;
        String[] keys = map.keySet().toArray(new String[0]);
        for (String key : keys){
            if (map.get(key).getName().equals(subBorder))
                isFounded = true;
            if (!isFounded)
                map.remove(key);
        }
        return map;
    }

    public synchronized String getCollectionInfo(){
        return "Тип коллекции: " + HashMap.class + "\n"
                +"Дата инициализации: " + initDate + "\n"
                +"Количество элементов: " + collection.size();
    }
}
