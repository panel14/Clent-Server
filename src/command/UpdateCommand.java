package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.HashMap;

public class UpdateCommand extends Command {

    @Override
    public String execute(){
        HashMap<String, StudyGroup> map = CollectionStorage.storage.getUserCollection(user);
        if (map.entrySet().stream()
                .noneMatch(s -> s.getValue().getId() == Integer.parseInt(args[7])))
            return "Элемента с заданным id не существует";
        String key = map.entrySet().stream()
                .filter(s -> s.getValue().getId() == Integer.parseInt(args[7])).findFirst().get().getKey();

        args[0] = map.get(key).getName();
        map.remove(key);

        StudyGroup group = parseStudyGroup(args);
        group.setId(Integer.parseInt(args[7]));
        group.setOwner(user.login);
        map.put(key, group);

        CollectionStorage.supplementAllCollection(map);

        return "Элемент с id " + args[7] + " в коллекции " + user.login + " был обновлён";
    }
}
