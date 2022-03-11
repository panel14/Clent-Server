package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveLowerCommand extends Command {
    @Override
    public String execute() {
        if (isNameExist(args[0]))
            return "Элемента с заданным именем нет в коллекции.";

        HashMap<String, StudyGroup> groups = CollectionStorage.storage.getCollection();

        HashMap<String, StudyGroup> sortedGroups = groups.entrySet().stream()
                .sorted(Map.Entry.<String, StudyGroup>comparingByValue().reversed()).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));

        CollectionStorage.storage.setSubMap(args[0], sortedGroups);

        return "Элементы, меньшие чем " + args[0] + " были удалены из коллекции.";
    }
}
