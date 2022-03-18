package command;

import state.CollectionStorage;

public class ShowCommand extends Command {

    @Override
    public String execute() {
        StringBuilder answer = new StringBuilder();
        CollectionStorage.storage.getAllCollection().entrySet().stream()
                .map(studyGroup -> studyGroup.getValue().toString() + ". Key: " + studyGroup.getKey() +
                        ". Owner: " + studyGroup.getValue().getOwner() + "\n")
                .forEach(answer::append);
        return answer.toString();
    }
}
