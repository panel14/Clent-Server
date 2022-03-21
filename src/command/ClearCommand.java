package command;

import state.CollectionStorage;
import java.util.Set;

public class ClearCommand extends Command {

    @Override
    public String execute(){
        Set<String> keys =  CollectionStorage.storage.getUserCollection(user).keySet();
        CollectionStorage.storage.getAllCollection().keySet().removeAll(keys);

        return "Коллекция пользователя " + user.login + " очищена";
    }
}
