package command;

import state.CollectionStorage;

public class RemoveKeyCommand extends Command {
    @Override
    public String execute() {
        if (CollectionStorage.storage.getCollection().keySet().stream().noneMatch(key -> key.equals(args[0])))
            return "В коллекции нет элемента с заданным ключом";
        CollectionStorage.storage.getCollection().remove(args[0]);
        return "Элемент с ключом " + args[0] + " удален из коллекции";
    }
}
