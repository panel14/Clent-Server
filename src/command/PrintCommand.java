package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PrintCommand extends Command {
    @Override
    public String execute() {

        HashMap<String, StudyGroup> groups = CollectionStorage.storage.getAllCollection();

        Optional<String> result = groups.entrySet().stream()
                .sorted(Map.Entry.<String, StudyGroup>comparingByValue().reversed())
                .map(s -> s.getValue().toString() + ". Key: " + s.getKey() + "\n")
                .reduce((x, y) -> x + y);

        return result.orElse("Не вышло.");
    }
}
