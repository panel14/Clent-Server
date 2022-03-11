package command;

import state.CollectionStorage;

public class ClearCommand extends Command {

    @Override
    public String execute(){
        CollectionStorage.storage.getCollection().clear();
        return "Коллекция очищена.";
    }
}
