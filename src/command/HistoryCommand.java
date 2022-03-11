package command;

import java.util.ArrayDeque;

public class HistoryCommand extends Command {

    private final ArrayDeque<String> history = new ArrayDeque<>();

    public void updateHistory(String command){
        history.addLast(command);
        if (history.size() > 8)
            history.removeFirst();
    }

    @Override
    public String execute() {
        StringBuilder stringBuilder = new StringBuilder();
        history.stream().map(c -> c + "\n").forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
