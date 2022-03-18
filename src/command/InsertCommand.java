package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.HashMap;

public class InsertCommand extends Command {

    @Override
    public String execute() {

        StudyGroup group = parseStudyGroup(args);
        group.setOwner(user.login);
        HashMap<String, StudyGroup> collection = CollectionStorage.storage.getUserCollection(user);

        collection.put(args[7], group);
        CollectionStorage.supplementAllCollection(collection);
        return "Элемент добавлен в коллекцию пользователя " + user.login;
    }
}
