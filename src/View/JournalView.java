package View;

import DAO.TransferObject.*;

import java.util.List;

public class JournalView {
    public void printJournals(List<ClassJournal> journals)
    {
        System.out.println("Введите номер журнала:");
        for (ClassJournal journal: journals) {
            System.out.println(journal.getId()+". Журнал "+journal.getName()+" класса");
        }
        System.out.println();
    }

    public void printStudents(List<SchoolStudent> students)
    {
        System.out.println("Введите номер студента:");
        for (SchoolStudent student: students) {
            System.out.println(student.getId()+". "+student.getFirstName() + " " + student.getSecondName());
        }
        System.out.println();
    }

    public void printSubjects(List<Subject> subjects)
    {
        System.out.println("Введите номер предмета:");
        for (Subject subject: subjects) {
            System.out.println(subject.getId()+". "+ subject.getName());
        }
        System.out.println();
    }

    public void printSchedules(List<Score> data)
    {
        System.out.println("Введите номер расписания:");
        for (Score score : data) {
            System.out.println(score.getSchedule().getId() + ". " + score.getSchedule().getDate());
        }
        System.out.println();
    }

    public void printScore(List<Score> data)
    {
        System.out.println("Введите номер оценки:");
        for (Score score : data) {
            System.out.println(score.getId() + ". " + score.getSubject().getName() +
            " " + score.getSchedule().getDate() + " Оценка - " + score.getScore());
        }
        System.out.println();
    }

    public void printScoreByIdSubject(SchoolStudent student, long idSubject)
    {
        if (student.getScoresByIdSubject(idSubject).size()>0) {
            System.out.println(student.getId() + ". " + student.getFirstName() + " " + student.getSecondName());
            for (Score score : student.getScoresByIdSubject(idSubject)) {
                System.out.print(score);
            }
        }
    }

    public void printInfoAddJournal(boolean isAdd)
    {
        if (isAdd) System.out.println("Успешно добавлено");
        else System.out.println("Не поучилось добавить");
    }

    public void printInfoDeleteJournal(boolean isDelete)
    {
        if (isDelete) System.out.println("Успешно удалено");
        else System.out.println("Не получилось удалить");
    }

    public void printStudentsInJournal(ClassJournal journal)
    {
        System.out.println(journal);
    }
}
