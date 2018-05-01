package View;

import DAO.TransferObject.SchoolStudent;

import java.util.List;

public class StudentView {
    public void printStudents(List<SchoolStudent> students)
    {
        System.out.println("Введите номер студента\n");
        for (SchoolStudent student: students) {
            System.out.println(student);
        }
    }

    public void printInfoAddStudent(boolean isAdd)
    {
        if (isAdd) System.out.println("Успешно добавлено в базу");
        else System.out.println("Не удалось добавить в базу");
    }

    public void printInfoDeleteStudent(boolean isDelete)
    {
        if (isDelete) System.out.println("Успешно удалено из базы");
        else System.out.println("Не удалось удалить");
    }
}
