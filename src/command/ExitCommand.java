package command;

import state.CollectionStorage;

public class ExitCommand extends Command {

    @Override
    public String execute(){
        CollectionStorage.storage.saveCollection();
        return "Коллекция сохранена.";
    }
}
