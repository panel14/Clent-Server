package studyGroup;

import java.util.HashMap;

public enum Semester {

    FIRST("FIRST"),
    THIRD("THIRD"),
    FOURTH("FOURTH"),
    SIXTH("SIXTH");

    private final String value;
    private static final HashMap<String, Semester> LOOK_MAP = new HashMap<>();

    static {
        for (Semester semester : values())
            LOOK_MAP.put(semester.getValue(), semester);
    }

    Semester(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Semester getSemester(String string) {
        return LOOK_MAP.get(string);
    }
}
