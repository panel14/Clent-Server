package command;

import state.CollectionStorage;

public class ShowCommand extends Command {

    @Override
    public String execute() {
        StringBuilder answer = new StringBuilder();
        CollectionStorage.storage.getCollection().entrySet().stream()
                .map(studyGroup -> studyGroup.getValue().toString() + ". Key: " + studyGroup.getKey() + "\n")
                .forEach(answer::append);
        return answer.toString();
    }
}
