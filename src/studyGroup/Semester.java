package studyGroup;

public enum Semester {

    FIRST("FIRST"),
    THIRD("THIRD"),
    FOURTH("FOURTH"),
    SIXTH("SIXTH");

    private final String value;

    Semester(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
