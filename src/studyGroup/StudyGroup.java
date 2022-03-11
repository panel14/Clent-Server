package studyGroup;

import java.time.LocalDateTime;

public class StudyGroup implements Comparable<StudyGroup> {

    private Integer id;                                      //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;                                     //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates;                         //Поле не может быть null
    private java.time.LocalDateTime creationDate;            //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount;                              //Значение поля должно быть больше 0
    private int averageMark;                                 //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation;                 //Поле может быть null
    private Semester semesterEnum;                           //Поле не может быть null
    private Person groupAdmin;                               //Поле может быть null

    public static void setCount(int count) {
        StudyGroup.count = count;
    }

    private static int count;

    public StudyGroup(){
        this.id = ++count;
        this.name = "Bob";
        this.creationDate = LocalDateTime.now().withNano(0);
        this.coordinates = new Coordinates(404, 666.66f);
        this.semesterEnum = Semester.FIRST;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = (id > 0) ? id : ++count;
    }

    public long getStudentsCount(){
        return studentsCount;
    }

    public void setStudentsCount(long studentsCount){
        this.studentsCount = (studentsCount > 0) ? studentsCount : 15;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = (name.equals("")) ? "Bob" : name;
    }

    public Coordinates getCoordinates() { return coordinates; }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = (coordinates == null) ? new Coordinates() : coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = (creationDate == null) ?  LocalDateTime.now().withNano(0) : creationDate;
    }

    public int getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(int averageMark) {
        this.averageMark = (averageMark > 0) ? averageMark : 4;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = (semesterEnum != null) ? semesterEnum : Semester.FIRST;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    @Override
    public String toString() {
        return "StudentGroup: " + name;
    }

    @Override
    public int compareTo(StudyGroup o) {
        if (this.studentsCount < o.studentsCount)
            return -1;
        else if (this.studentsCount > o.studentsCount)
            return 1;
        return 0;
    }
}
