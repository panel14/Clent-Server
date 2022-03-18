package command;

import state.CollectionStorage;

public class ClearCommand extends Command {

    @Override
    public String execute(){
        CollectionStorage.storage.getUserCollection(user).clear();
        return "Коллекция пользователя" + user.login + " очищена";
    }
}
