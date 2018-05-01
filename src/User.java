import Controller.*;
import DAO.DAOFactory;
import DAO.Factory;
import DAO.TransferObject.*;
import View.*;

import java.time.LocalDate;
import java.util.Scanner;

public class User {
    public static void main(String[] args) throws Exception {
        JournalView journalView = new JournalView();
        JournalController journalController = new JournalController(DAOFactory.getDaoFactory(Factory.XML).getJournalDAO(),
                journalView);
        StudentView studentView = new StudentView();
        StudentController studentController = new StudentController(DAOFactory.getDaoFactory(Factory.XML).getSchoolStudentDAO(),
                studentView);
        SubjectView subjectView = new SubjectView();
        SubjectController subjectController = new SubjectController(DAOFactory.getDaoFactory(Factory.XML).getSubjectDAO(),
                subjectView);
        ScheduleView scheduleView = new ScheduleView();
        ScheduleController scheduleController = new ScheduleController(DAOFactory.getDaoFactory(Factory.XML).getScheduleDAO(),
                scheduleView);
        ScoreView scoreView = new ScoreView();
        ScoreController scoreController = new ScoreController(DAOFactory.getDaoFactory(Factory.XML).getScoreDAO(),
                scoreView);
        Scanner in = new Scanner(System.in);
        StringBuilder requests = new StringBuilder();
        requests.append("Выберите функцию:\n")
                .append("1. Посмотреть журнал\n")
                .append("2. Добавить журнал\n")
                .append("3. Удалить журнал\n")
                .append("4. Изменить журнал\n")
                .append("5. Сервис\n")
                .append("0. Выйти\n");
        StringBuilder requestUpdateJournal = new StringBuilder("Выберите функцию:\n")
                .append("1. Заполнить журнал\n")
                .append("2. Добавить ученика в журнал\n")
                .append("3. Удалить ученика из журнала\n")
                .append("4. Добавить предмет для обучения\n")
                .append("5. Удалить предмет из обучения\n")
                .append("0. Выйти в меню\n");
        StringBuilder requestFillJournal = new StringBuilder("Выберите функцию\n")
                .append("1. Добавить дату занятия\n")
                .append("2. Добавить отсутствующего/оценку ученику\n")
                .append("3. Удалить дату занятия\n")
                .append("4. Удалить оценку ученика\n")
                .append("0. Выйти в меню\n");
        StringBuilder requestService = new StringBuilder("Выбеите функцию\n")
                .append("1. Добавить дополнительное расписание\n")
                .append("2. Добавить нового ученика в школу\n")
                .append("3. Добавить новый критерий оценивания в школу\n")
                .append("4. Добавить новый предмет в школу\n")
                .append("5. Удалить расписание\n")
                .append("6. Удалить ученика из школы\n")
                .append("7. Удалить критерий оценивания в школе\n")
                .append("8. Удалить предмет из школы\n")
                .append("0. Выйти в меню\n");
        int id = -1;
        while (id != 0) {
            System.out.println(requests.toString());
            id = in.nextInt();
            switch (id) {
                case 1:
                    journalController.lookJournals();
                    long idClass = in.nextLong();
                    journalController.lookSubjectsByIdJournal(idClass);
                    long idSubject = in.nextLong();
                    journalController.lookScoreById(idClass,idSubject);
                    break;
                case 2:
                    System.out.println("Введите название класса:");
                    ClassJournal classJournal = new ClassJournal(in.next(),null,null);
                    System.out.println("Добавьте учеников в класс:");
                    while (id!=0) {
                        System.out.println("Для выхода в главное меню введите 0!");
                        studentController.lookStudents();
                        id = in.nextInt();
                        classJournal.addStudent(studentController.getStudentById(Long.valueOf(id)));
                    }
                    System.out.println("Добавьте предметы по которым у них будут идти занятия:");
                    id = -1;
                    while (id!=0)
                    {
                        System.out.println("Для выхода в главное меню введите 0!");
                        subjectController.lookSubjects();
                        id = in.nextInt();
                        classJournal.addSubject(subjectController.getSubjectById(Long.valueOf(id)));
                    }
                    journalController.addJournal(classJournal);
                    id = -1;
                    break;
                case 3:
                    journalController.lookJournals();
                    journalController.deleteJournal(in.nextLong());
                    id = -1;
                    break;
                case 4:
                    System.out.println(requestUpdateJournal.toString());
                    id = in.nextInt();
                    switch (id)
                    {
                        case 1:
                            System.out.println(requestFillJournal);
                            id = in.nextInt();
                            if (id == 1) {
                                journalController.lookJournals();
                                idClass = in.nextLong();
                                journalController.lookSubjectsByIdJournal(idClass);
                                idSubject = in.nextLong();
                                scheduleController.lookSchedules();
                                long idSchedule = in.nextLong();
                                journalController.addLesson(idClass,idSubject,idSchedule);
                                id = -1;
                                break;
                            }
                            else if (id == 2)
                            {
                                journalController.lookJournals();
                                idClass = in.nextLong();
                                journalController.lookSubjectsByIdJournal(idClass);
                                idSubject = in.nextLong();
                                journalController.lookStudentsByIdJournal(idClass, idSubject);
                                long idStudent = in.nextLong();
                                journalController.lookScheduleById(idClass);
                                long idSchedule = in.nextLong();
                                scoreController.lookScores();
                                long idScore = in.nextLong();
                                journalController.addLessonByScore(idClass,idStudent,idSubject,idSchedule,idScore);
                                id = -1;
                                break;
                            }
                            else if (id == 3)
                            {
                                journalController.lookJournals();
                                idClass = in.nextLong();
                                journalController.lookSubjectsByIdJournal(idClass);
                                idSubject = in.nextLong();
                                journalController.lookScheduleById(idClass);
                                long idSchedule = in.nextLong();
                                journalController.deleteLesson(idClass,idSubject,idSchedule);
                                id = -1;
                                break;
                            }
                            else if (id == 4)
                            {
                                journalController.lookJournals();
                                idClass = in.nextLong();
                                journalController.lookSubjectsByIdJournal(idClass);
                                idSubject = in.nextLong();
                                journalController.lookStudentsByIdJournal(idClass, idSubject);
                                long idStudent = in.nextLong();
                                journalController.lookScoreById(idClass,idSubject,idStudent);
                                long idData = in.nextLong();
                                journalController.deleteLessonByStudent(idClass,idStudent,idSubject,idData);
                                id = -1;
                                break;
                            }
                            if (id == 0)
                            {
                                id = -1;
                                break;
                            }
                        case 2:
                            journalController.lookJournals();
                            idClass = in.nextLong();
                            studentController.lookStudents();
                            long idStudent = in.nextLong();
                            journalController.addStudentInClass(idClass,idStudent);
                            id = -1;
                            break;
                        case 3:
                            journalController.lookJournals();
                            idClass = in.nextLong();
                            journalController.lookSubjectsByIdJournal(idClass);
                            idSubject = in.nextLong();
                            journalController.lookStudentsByIdJournal(idClass,idSubject);
                            idStudent = in.nextLong();
                            journalController.deleteStudentInClass(idClass,idStudent);
                            id = -1;
                            break;
                        case 4:
                            journalController.lookJournals();
                            idClass = in.nextLong();
                            subjectController.lookSubjects();
                            idSubject = in.nextLong();
                            journalController.addSubjectInClass(idClass,idSubject);
                            id = -1;
                            break;
                        case 5:
                            journalController.lookJournals();
                            idClass = in.nextLong();
                            journalController.lookSubjectsByIdJournal(idClass);
                            idSubject = in.nextLong();
                            journalController.deleteSubjectInClass(idClass,idSubject);
                            id = -1;
                            break;
                    }
                    break;
                case 5:
                    System.out.println(requestService.toString());
                    id = in.nextInt();
                    switch (id)
                    {
                        case 1:
                            System.out.println("Введите год:");
                            int year = in.nextInt();
                            System.out.println("Введите месяц:");
                            int month = in.nextInt();
                            System.out.println("Введите день");
                            int day = in.nextInt();
                            Schedule schedule = new Schedule(LocalDate.of(year,month,day));
                            scheduleController.addSchedule(schedule);
                            break;
                        case 2:
                            System.out.println("Введите имя ученика:");
                            String firstName = in.next();
                            System.out.println("Введите фамилие ученика:");
                            String secondName = in.next();
                            SchoolStudent student = new SchoolStudent(firstName,secondName,null);
                            studentController.addStudent(student);
                            break;
                        case 3:
                            System.out.println("Введите название оценки:");
                            String nameScore = in.next();
                            Score score = new Score(null,null,nameScore);
                            scoreController.addScore(score);
                            break;
                        case 4:
                            System.out.println("Введите название предмета:");
                            String nameSubject = in.next();
                            Subject subject = new Subject(nameSubject);
                            subjectController.addSubject(subject);
                            break;
                        case 5:
                            scheduleController.lookSchedules();
                            long idSchedule = in.nextLong();
                            scheduleController.deleteSchedule(idSchedule);
                            break;
                        case 6:
                            studentController.lookStudents();
                            long idStudent = in.nextLong();
                            studentController.deleteStudent(idStudent);
                            break;
                        case 7:
                            scoreController.lookScores();
                            long idScore = in.nextLong();
                            scoreController.deleteScore(idScore);
                            break;
                        case 8:
                            subjectController.lookSubjects();
                            idSubject = in.nextLong();
                            subjectController.deleteSubject(idSubject);
                            break;
                        case 0:
                            id = -1;
                            break;
                    }
                    break;
                case 0:
                    id = 0;
                    break;
            }
        }
    }
}
