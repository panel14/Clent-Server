package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.HashMap;

public class RemoveKeyCommand extends Command {
    @Override
    public String execute() {
        HashMap<String, StudyGroup> map = CollectionStorage.storage.getUserCollection(user);
        if (map.keySet().stream().noneMatch(key -> key.equals(args[0])))
            return "В коллекции нет элемента с заданным ключом";
        CollectionStorage.storage.getAllCollection().remove(args[0]);
        return "Элемент с ключом " + args[0] + " удален из коллекции " + user.login;
    }
}
