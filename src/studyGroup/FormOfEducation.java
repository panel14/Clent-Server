package studyGroup;

import java.util.HashMap;

public enum FormOfEducation {

    DISTANCE_EDUCATION("DISTANCE_EDUCATION"),
    FULL_TIME_EDUCATION("FULL_TIME_EDUCATION"),
    EVENING_CLASSES("EVENING_CLASSES");

    private final String value;
    private static final HashMap<String, FormOfEducation> LOOK_MAP = new HashMap<>();

    static {
        for(FormOfEducation form : values())
            LOOK_MAP.put(form.getValue(), form);
    }

    FormOfEducation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FormOfEducation getFullTimeEducation(String string) {
        return LOOK_MAP.get(string);
    }

}
