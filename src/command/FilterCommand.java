package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.Collection;
import java.util.Optional;

public class FilterCommand extends Command {
    @Override
    public String execute() {
        Collection<StudyGroup> groups = CollectionStorage.storage.getCollection().values();

        Optional<String> result = groups.stream().map(StudyGroup::getName)
                .filter(name -> name.startsWith(args[0]))
                .reduce((x, y) -> x + y + "\n");

        return result.orElse("Не найдено элементов");
    }
}
