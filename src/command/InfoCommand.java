package command;

import state.CollectionStorage;

public class InfoCommand extends Command {

    @Override
    public String execute() {
        return CollectionStorage.storage.getCollectionInfo();
    }
}
