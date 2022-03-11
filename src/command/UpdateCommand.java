package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

public class UpdateCommand extends Command {

    @Override
    public String execute(){
        if (CollectionStorage.storage.getCollection().entrySet().stream()
                .noneMatch(s -> s.getValue().getId() == Integer.parseInt(args[7])))
            return "Элемента с заданным id не существует";
        String key = CollectionStorage.storage.getCollection().entrySet().stream()
                .filter(s -> s.getValue().getId() == Integer.parseInt(args[7])).findFirst().get().getKey();

        args[0] = CollectionStorage.storage.getCollection().get(key).getName();
        CollectionStorage.storage.getCollection().remove(key);

        StudyGroup group = parseStudyGroup(args);
        group.setId(Integer.parseInt(args[7]));
        CollectionStorage.storage.getCollection().put(key, group);

        return "Элемент с id " + args[7] + " был обновлён";
    }
}
