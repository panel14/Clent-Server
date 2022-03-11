package command;

import state.CollectionStorage;
import studyGroup.StudyGroup;

import java.util.Collection;

public class AverageOfCommand extends Command {
    @Override
    public String execute() {

        Collection<StudyGroup> groups = CollectionStorage.storage.getCollection().values();
        long sum = groups.stream()
                .map(StudyGroup::getStudentsCount).reduce(Long::sum).get();

        return "Среднее значение studentsCount: " + (sum / groups.size());
    }
}
