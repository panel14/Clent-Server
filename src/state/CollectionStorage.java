package state;

import server.service.DataBase;
import studyGroup.*;
import utils.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс, предоставляет коллекцию и методы работы с ней
 * */
public final class CollectionStorage {

    public static CollectionStorage storage = new CollectionStorage();
    private static HashMap<String, StudyGroup> collection;
    private final LocalDateTime initDate;

    private CollectionStorage(){
        collection = DataBase.getCollection();
        initDate = LocalDateTime.now();
    }

    /**
     * Метод, возвращает всю коллекцию
     * @return HashMap - коллекция объектов
     * */
    public synchronized HashMap<String, StudyGroup> getAllCollection() {
        if (collection.isEmpty())
            collection = DataBase.getCollection();
        return collection;
    }

    /**
     * Метод, дополняет коллекцию новыми элементами коллекции параметра
     * @param all - дополнительные элементы для добавления в основную коллекцию
     * */
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

    /**
     * Метод, сохраняет коллекцию в базу данных
     * */
    public static synchronized void saveCollection(){
        DataBase.saveCollection(collection);
    }

    /**
     * Метод, возвращает срез данной HashMap относительно граничного элемента subBorder
     * @param subBorder - граничный элемент
     * @param map - данная HashMap
     * */
    public synchronized HashMap<String, StudyGroup> setSubMap(String subBorder, HashMap<String, StudyGroup> map) {
        String[] keys = map.keySet().toArray(new String[0]);
        boolean isFounded = false;
        for (String key : keys){
            if (map.get(key).getName().equals(subBorder)) isFounded = true;
            if (isFounded) map.remove(key);
        }
        return map;
    }

    /**
     * Метод, возвращает информацию о коллекции
     * */
    public synchronized String getCollectionInfo(){
        return "Тип коллекции: " + HashMap.class + "\n"
                +"Дата инициализации: " + initDate + "\n"
                +"Количество элементов: " + collection.size();
    }
}
