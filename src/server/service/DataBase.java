package server.service;

import utils.Pair;
import studyGroup.*;
import utils.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * Класс для работы с базой данных
 * */
public class DataBase {
    private static final String url = "jdbc:postgresql://localhost:9999/studs";
    private static final String user = "s312434";
    private static final String password = "***";
    private static Connection connection;

    private static boolean isConnected = false;

    private static final String IS_EXIST_REQUEST = "SELECT * FROM app_users WHERE login LIKE ?";
    private static final String IS_AUTH_REQUEST = "SELECT * FROM app_users WHERE login LIKE ? AND password LIKE ?";
    private static final String INSERT_USER_REQUEST = "INSERT INTO app_users (login, password) VALUES (?,?)";
    private static final String GET_COLLECTION = "SELECT * FROM app_groups";
    private static final String SET_COLLECTION = "INSERT INTO app_groups" +
            "(coord_x," +
            "coord_y,"  +
            "creation_date," +
            "student_count," +
            "average_mark," +
            "form_education," +
            "semester," +
            "person_name," +
            "person_passport," +
            "person_loc_x," +
            "person_loc_y," +
            "person_loc_z," +
            "owner," +
            "name," +
            "key)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Метод, устанавливает соединение с базой данных
     * @return true - соединение установлено
     *         false - не удалось установить соединение
     * */
    public static boolean connect(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Подключение успешно установлено.");
            return true;
        }
        catch (Exception e){
            System.out.println("Не удалось установить подключение: " + e.getMessage());
            return false;
        }
    }

    /**
     * Метод, проверяет, существует ли пользователь в базе данных
     * @param user - данные пользователя
     * @return true - пользователь существует
     *         false - пользователя нет в базе данных
     * */
    public static boolean isUserExist(User user){
        try {
            PreparedStatement checkStatement = connection.prepareStatement(IS_EXIST_REQUEST);
            checkStatement.setString(1, user.login);
            ResultSet resultSet = checkStatement.executeQuery();
            return (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод, добавляет нового пользователя в базу данных
     * @param user - данные пользователя
     * @return Возвращаемое значение - объект {@code Pair<Boolean, String>}. Значение bool - удалось
     * зарегистрировать пользователя или нет, String - комментарий результата выполнения
     * */
    public static Pair<Boolean, String> registerUser(User user) {
        isConnected = connect();
        if (!isConnected) return new Pair<>(false, "Не удалось подключиться к базе данных.");
        if (isUserExist(user)) return new Pair<>(false, "Пользователь с таким логином уже существует.");
        try {
            String hashPassword = Hasher.encodeStringSHA512(user.password);
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_USER_REQUEST);
            insertStatement.setString(1, user.login);
            insertStatement.setString(2, hashPassword);
            insertStatement.executeUpdate();
            insertStatement.close();
            return new Pair<>(true, "Пользователь успешно зарегистрирован.");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Pair<>(false, "Не удалось получить данные по пользователю: " + e.getMessage());
        }
    }

    /**
     * Метод, проверяет, зарегистрирован ли пользователь
     * @param user - данные пользователя
     * @return Возвращаемое значение - объект {@code Pair<Boolean, String>}. Значение bool - зарегистрирован
     * ли пользователь, String - комментарий результата выполнения
     * */
    public static Pair<Boolean, String> isAuthUser(User user) {
        isConnected = connect();
        if (!isConnected) return new Pair<>(false, "Не удалось подключиться к базе данных.");
        if (!isUserExist(user)) return new Pair<>(false, "Пользователя с таким логином не существует.");
        try {
            String hashPassword = Hasher.encodeStringSHA512(user.password);

            PreparedStatement checkPasswordState = connection.prepareStatement(IS_AUTH_REQUEST);
            checkPasswordState.setString(1, user.login);
            checkPasswordState.setString(2, hashPassword);

            ResultSet checkSet = checkPasswordState.executeQuery();
            if (checkSet.next())
                return new Pair<>(true, "Пользователь подтверждён.");
            else return new Pair<>(false, "Неверный пароль.");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Pair<>(false, "Не удалось получить данные по пользователю: " + e.getMessage());
        }
    }

    /**
     * Метод, получает всю коллекцию из базы данных
     * @return HashMap - все группы, содержащиеся в базе данных, обёрнутых в HashMap
     * */
    public static HashMap<String, StudyGroup> getCollection() {
        HashMap<String, StudyGroup> groups = new HashMap<>();
        try {
            ResultSet collection = connection.prepareStatement(GET_COLLECTION).executeQuery();

            while (collection.next()){
                StudyGroup group = new StudyGroup();
                group.setId(collection.getInt(1));
                group.setName(collection.getString(15));

                Coordinates coordinates = new Coordinates(collection.getInt(2),
                        collection.getFloat(3));
                group.setCoordinates(coordinates);

                LocalDateTime time = collection.getTimestamp(4).toLocalDateTime();
                group.setCreationDate(time);

                group.setStudentsCount(collection.getInt(5));
                group.setAverageMark(collection.getInt(6));

                FormOfEducation form = FormOfEducation.getFullTimeEducation(collection.getString(7));
                group.setFormOfEducation(form);

                Semester semester = (collection.getString(8) == null) ? null :
                        Semester.getSemester(collection.getString(8));
                group.setSemesterEnum(semester);

                Person admin = new Person();
                admin.setName((collection.getString(9) == null) ? null : collection.getString(9));
                admin.setPassportID((collection.getString(10) == null) ? "" : collection.getString(10));

                Location location = new Location(collection.getDouble(11),
                        collection.getInt(12), collection.getDouble(13));
                admin.setLocation(location);
                group.setOwner(collection.getString(14));

                groups.put(collection.getString(16), group);
            }

        } catch (Exception e) {
            System.out.println("Не удалось обратиться к коллекции: " + e.getMessage());
        }
        return groups;
    }

    /**
     * Метод, сохраняет коллекцию в базу данных
     * @param groups - коллекция объектов
     * */
    public static void saveCollection(HashMap<String, StudyGroup> groups){
        try {
            connection.createStatement().executeUpdate("TRUNCATE app_groups");
            for (Map.Entry<String, StudyGroup> entry : groups.entrySet()){
                String key = entry.getKey();
                StudyGroup group = entry.getValue();
                saveGroup(group, key);
            }
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить коллекцию в базу данных: " + e.getMessage());
        }
    }

    /**
     * Метод, сохраняет коллекцию в базу данных
     * @param group - объект StudyGroup
     * @param key - ключ, с которым объект group будет добавлен в коллекцию
     * */
    public static void saveGroup(StudyGroup group, String key) {
        try {
            PreparedStatement addState = connection.prepareStatement(SET_COLLECTION);
            addState.setInt(1, group.getCoordinates().getX());
            addState.setDouble(2, group.getCoordinates().getY());


            Timestamp date = Timestamp.valueOf(group.getCreationDate());
            addState.setTimestamp(3, date);

            addState.setInt(4, (int) group.getStudentsCount());
            addState.setInt(5, group.getAverageMark());
            addState.setString(6, group.getFormOfEducation() == null
                    ? null : group.getFormOfEducation().getValue());
            addState.setString(7, group.getSemesterEnum().getValue());
            Person admin = group.getGroupAdmin();

            addState.setString(8, (admin == null) ? null : admin.getName());
            addState.setString(9, (admin == null) ? null : admin.getPassportID());

            Location location = (admin == null) ? null : admin.getLocation();
            addState.setDouble(10, (location == null) ? 0 : location.getX());
            addState.setDouble(11, (location == null) ? 0 : location.getY());
            addState.setDouble(12, (location == null) ? 0 : location.getZ());
            addState.setString(13, group.getOwner());
            addState.setString(14, group.getName());
            addState.setString(15, key);
            addState.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Не удалось сохранить объект в базу данных: " + e.getMessage());
        }
    }
}
