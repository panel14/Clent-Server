package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveGreaterCommand extends Command {
    @Override
    public String execute() {
        if (isNameExist(args[0]))
            return "Элемента с заданным именем нет в коллекции пользователя " + user.login;

        HashMap<String, StudyGroup> groups = CollectionStorage.storage.getUserCollection(user);

        HashMap<String, StudyGroup> sortedGroups = groups.entrySet().stream()
                .sorted(Map.Entry.<String, StudyGroup>comparingByValue().reversed()).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));

        HashMap<String, StudyGroup> subMap = CollectionStorage.storage.setSubMap(args[0], sortedGroups);
        CollectionStorage.storage.getAllCollection().keySet().removeAll(subMap.keySet());

        return "Элементы, большие чем " + args[0] + " были удалены из коллекции " + user.login;
    }
}
