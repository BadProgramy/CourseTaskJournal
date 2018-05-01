package View;

import DAO.TransferObject.Subject;

import java.util.List;

public class SubjectView {
    public void printSubjects(List<Subject> subjects)
    {
        System.out.println("Введите номер предмета");
        for (Subject subject: subjects) {
            System.out.println(subject.getId()+ ". "+subject);
        }
    }

    public void printInfoAddSubject(boolean isAdd)
    {
        if(isAdd) System.out.println("Успешно добавлено");
        else System.out.println("Не удалось добавить");
    }

    public void printInfoDeleteSubject(boolean isDelete)
    {
        if (isDelete) System.out.println("Успешно удалено");
        else System.out.println("Не удалось удалить");
    }
}
