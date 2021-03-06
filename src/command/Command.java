package command;

import state.CollectionStorage;
import studyGroup.*;
import utils.User;
/**
 * Общий класс команд приложения
 * */
public class Command {

    protected String[] args;
    protected User user;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected Person getAdmin(String admin){
        if (admin.equals("null"))
            return null;

        String[] adminParams = admin.split(";");

        Person person = new Person();

        person.setName(adminParams[0]);
        person.setPassportID(adminParams[1]);

        Location location = new Location();

        if (adminParams.length == 2)
            return person;

        String[] locationParams = adminParams[2].split(" ");
        boolean isXParsed = false;
        for (String loc : locationParams){
            if (loc.contains(".") && !isXParsed){
                location.setX(Double.parseDouble(loc));
                isXParsed = true;
            }
            else if (loc.contains(".") && isXParsed)
                location.setZ(Double.parseDouble(loc));
            else
                location.setY(Integer.parseInt(loc));
        }
        person.setLocation(location);

        return person;
    }

    protected StudyGroup parseStudyGroup(String[] props){
        StudyGroup group = new StudyGroup();
        group.setName(args[0]);

        String[] coords = args[1].split(" ");
        group.setCoordinates(new Coordinates(Integer.parseInt(coords[0]), Float.parseFloat(coords[1])));

        group.setStudentsCount(Long.parseLong(args[2]));
        group.setAverageMark(Integer.parseInt(args[3]));

        FormOfEducation form = (args[4].equals("") ? null : FormOfEducation.valueOf(args[4]));
        group.setFormOfEducation(form);
        group.setSemesterEnum(Semester.valueOf(args[5]));

        group.setGroupAdmin(getAdmin(args[6]));

        return group;
    }

    protected boolean isNameExist(String name) {
        return CollectionStorage.storage.getUserCollection(user).values()
                .stream().noneMatch(s -> s.getName().equals(name));
    }

    /**
     * Основной метод команд приложения. Запускает обработку команды
     * @return string - результат выполнения команды.
     * */
    public String execute(){
        return "Неизвестная команда";
    }
}
