package studyGroup;

public class Coordinates {
    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = (y > 693) ? 693 : y;
    }

    private Integer x; //Поле не может быть null
    private Float y; //Максимальное значение поля: 693, Поле не может быть null

    /**
     * Конструктор класса
     * @param x - int, x - координата
     * @param y - float, y - координата
     */
    public Coordinates(Integer x, Float y){
        this.x = x;
        this.y = (y > 693) ? 693 : y;
    }

    /**
     * Конструктор класса без аргументов. Устанавливает значения полей класса по умолчанию
     */
    public Coordinates(){
        this.x = 5;
        this.y = 5f;
    }

    /**
     * Перегрузка метода toString()
     * @return возврат - String, строковое представление класса
     */
    @Override
    public String toString() {
        return x.toString() + ";" + y.toString();
    }
}
