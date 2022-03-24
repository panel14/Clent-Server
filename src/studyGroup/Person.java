package studyGroup;
/**
 * Класс Person для админа StudyGroup
 */
public class Person {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(""))
            this.name = name;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        if (!passportID.equals(""))
            this.passportID = passportID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private String name;                                     //Поле не может быть null, Строка не может быть пустой
    private String passportID;                               //Строка не может быть пустой, Поле может быть null
    private Location location;                               //Поле может быть null

    /**
     * Конструктор без параметров. Поля, которые не могут быть null, генерируются автоматически
     */
    public Person(){
        this.name = "Clark";
        this.passportID = null;
        this.location = Location.getDefaultLocation();
    }

    /**
     * Конструктор с параметрами
     * @param name - String, имя
     * @param passportID - String, идентификатор паспорта
     * @param location - Location, класс для координат админа
     */
    public Person(String name, String passportID, Location location){
        this.name = name;
        this.passportID = passportID;
        this.location = location;
    }

    /**
     * Перегрузка метода toString()
     * @return - возврат - String, строковое представление экземпляра класса
     */
    @Override
    public String toString() {
        return name + ";" + passportID + ";" + location.toString();
    }

}
