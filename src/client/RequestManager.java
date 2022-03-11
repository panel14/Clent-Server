package client;

import command.CommandType;
import org.apache.commons.lang3.math.NumberUtils;
import studyGroup.FormOfEducation;
import studyGroup.Semester;
import utils.Request;

import java.util.Scanner;

public class RequestManager {

    private final static Scanner in = new Scanner(System.in);

    public static Request makeRequest(String[] command){
        if (command.length == 1){

            CommandType type = CommandType.getTypeByString(command[0]);
            if (type != null)
                return new Request(type);
            return null;

        } else {
            if (command[1].equals(""))
                return null;
            switch (command[0]){
                case "insert": {
                    if (command.length != 3)
                        return null;
                    String[] params = getParams(command[2]);
                    params[7] = command[1];
                    return new Request(CommandType.INSERT, params);
                }
                case "update": {
                    if (command.length != 3 || !command[1].equals("id"))
                        return null;
                    String[] params = getParams(command[2]);
                    params[7] = command[2];
                    return new Request(CommandType.UPDATE, params);
                }
                default:
                    CommandType type = CommandType.getTypeByString(command[0]);
                    if (type != null)
                        return new Request(type, new String[]{command[1]});
                    return null;
            }
        }
    }

    private static String[] getParams(String groupName){
        String incorrect = "Неверный формат ввода. Повторите ввод: ";

        String[] params = new String[8];
        params[0] = groupName;

        System.out.println("Введите координаты объекта. Формат ввода \"x y\"");
        while (!checkCoords(params[1]  = in.nextLine()))
            System.out.println(incorrect);

        System.out.println("Введите количество студентов в группе:");
        while (!NumberUtils.isParsable(params[2] = in.nextLine()))
            System.out.println(incorrect);

        System.out.println("Введите среднюю оценку группы:");
        while (!NumberUtils.isParsable(params[3] = in.nextLine()))
            System.out.println(incorrect);

        System.out.println("Введите форму обучения. Возможные варианты:");
        for (FormOfEducation form : FormOfEducation.values())
            System.out.println(form);
        System.out.println("Для ввода null нажмите ENTER.");

        while (!checkFE(params[4] = in.nextLine()))
            System.out.println(incorrect);

        System.out.println("Введите семестр. Возможные варианты:");
        for (Semester semester : Semester.values())
            System.out.println(semester);

        while (true) {
            boolean b = false;
            params[5] = in.nextLine();
            for (Semester s : Semester.values()) {
                if (s.toString().equals(params[5])) {
                    b = true;
                    break;
                }
            }
            if (b) break;
            System.out.println(incorrect);
        }

        System.out.println("Введите имя админа группы (нажмите ENTER, чтобы установить admin - null):");
        params[6] = getAdmin();

        return params;

    }

    private static boolean checkCoords(String coordinates){
        String[] splinted = coordinates.split(" ");
        return NumberUtils.isParsable(splinted[0]) && NumberUtils.isParsable(splinted[1]);
    }

    private static boolean checkFE(String formEdu){
        if (formEdu.equals(""))
            return true;
        for (FormOfEducation s : FormOfEducation.values()) {
            if (s.toString().equals(formEdu)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkLocation(String location){
        if (location.equals(""))
            return true;
        String[] locParam = location.split(" ");
        if (locParam.length < 1)
            return false;
        for (String param : locParam)
            if (!param.equals(" ") && NumberUtils.isParsable(param)){
                try {
                    Integer.parseInt(param);
                } catch (NumberFormatException e) {
                    continue;
                }
                return true;
            }
        return false;
    }

    private static String getAdmin(){
        StringBuilder result = new StringBuilder("");

        String[] admin = new String[3];

        admin[0] = in.nextLine();
        if (admin[0].equals(""))
            return "null";

        result.append(admin[0]);
        result.append(";");

        System.out.println("Введите идентификатор паспорта (не свой). Формат: число. Для ввода null нажмите ENTER:");
        String passport;
        while((passport = in.nextLine()).equals(" "))
            System.out.println("Поле не может быть пустым");
        result.append(passport);
        result.append(";");

        System.out.println("Введите локацию. Формат ввода: \"1.0 1 1.5\". Вторая координата (int) не может быть null:");

        while (!checkLocation(admin[1] = in.nextLine()))
            System.out.println("Неверный формат ввода");
        result.append(admin[1]);
        result.append(";");


        return result.toString();
    }
}
