package studyGroup;

public class Location {

    public void setX(double x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    private double x;
    private Integer y; //Поле не может быть null
    private double z;

    /**
     * Конструктор класса
     * @param x - double, х - координата
     * @param y - int, y - координата
     * @param z - double, z - координата
     */
    public Location(double x, Integer y, double z){
        this.x = x;
        this.y = (y != null) ? y : 25;
        this.z = z;
    }

    /**
     * Конструктор класса без аргументов. Нужен, чтобы инициализировать класс с пустыми полями.
     */
    public Location(){}

    /**
     * Статический метод, нужен, чтобы заполнить поля класса значениями по умолчанию
     * @return возврат - Location, новый экземпляр класса Location
     */
    public static Location getDefaultLocation(){
        return new Location(99.9d, 8, 7.77d);
    }

    /**
     * Перегрузка метода toString()
     * @return - возврат - String, строковое представление экземпляра класса
     */
    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

}
