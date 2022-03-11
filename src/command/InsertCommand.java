package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

public class InsertCommand extends Command {

    @Override
    public String execute() {

        StudyGroup group = parseStudyGroup(args);
        CollectionStorage.storage.getCollection().put(args[7], group);

        return "Элемент добавлен.";
    }
}
