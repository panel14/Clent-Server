package command;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
    HELP("help"),
    SHOW("show"),
    INFO("info"),
    INSERT("insert"),
    UPDATE("update"),
    REMOVE_KEY("remove_key"),
    CLEAR("clear"),
    EXIT("exit"),
    REMOVE_GREATER("remove_greater"),
    REMOVE_LOWER("remove_lower"),
    HISTORY("history"),
    AVERAGE_OF_STUDENTS_COUNT("average_of_students_count"),
    FILTER_STARTS_WITH_NAME("filter_starts_with_name"),
    PRINT_DESCENDING("print_descending");

    private final String command;
    private static final Map<String, CommandType> LOOK_MAP = new HashMap<>();

    static {
        for (CommandType type : values()) {
            LOOK_MAP.put(type.getCommand(), type);
        }
    }

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType getTypeByString(String command) {
        return LOOK_MAP.get(command);
    }
}
