package studyGroup;

public enum FormOfEducation {

    DISTANCE_EDUCATION("DISTANCE_EDUCATION"),
    FULL_TIME_EDUCATION("FULL_TIME_EDUCATION"),
    EVENING_CLASSES("EVENING_CLASSES");

    private final String value;

    FormOfEducation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
