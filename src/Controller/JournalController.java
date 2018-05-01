package Controller;

import DAO.DAOInterface.JournalDAO;
import DAO.TransferObject.ClassJournal;
import DAO.TransferObject.SchoolStudent;
import View.JournalView;

public class JournalController {
    private JournalDAO model;
    private JournalView view;

    public JournalController(JournalDAO model, JournalView view) {
        this.model = model;
        this.view = view;
    }

    public void lookJournals() throws Exception {
        view.printJournals(model.getAllClassJournal());
    }

    public void lookScoreById(long idClass, long idSubject) throws Exception {
        for (SchoolStudent student: model.findClassJournal(idClass).getSchoolStudents()) {
            view.printScoreByIdSubject(student, idSubject);
        }
    }

    public void lookJournalById(long id) throws Exception {
        view.printStudentsInJournal(model.findClassJournal(id));
    }

    public void lookStudentsByIdJournal(long id, long idSubject) throws Exception {
        view.printStudents(model.findClassJournal(id).getSchoolStudentsByIdSubject(idSubject));
    }

    public void lookSubjectsByIdJournal(long id) throws Exception {
        view.printSubjects(model.findClassJournal(id).getSubjectDomains());
    }


    public void lookScheduleById(long idClass) throws Exception {
        view.printSchedules(model.findClassJournal(idClass).getStudent().getScores());
    }

    public void lookScoreById(long idClass,long idSubject, long idStudent) throws Exception {
        view.printScore(model.findClassJournal(idClass).getStudent(idStudent).getScoresByIdSubject(idSubject));
    }

    public void deleteJournal(long id) throws Exception {
        view.printInfoDeleteJournal(model.deleteClassJournal(id));
    }

    public void addJournal(ClassJournal journal) throws Exception {
        if (journal!=null)
            view.printInfoAddJournal(model.addClassJournal(journal));
    }

    public void addLesson(long idClass, long idSubject, long idSchedule)//Добавить занятие (Оценка по умолчанию)
    {
        view.printInfoAddJournal(model.addLesson(idClass,idSubject,idSchedule));
    }

    public void addLessonByScore(long idClass,long idStudent, long idSubject, long idSchedule, long idScore)
    {
        view.printInfoAddJournal(model.addLessonByStudent(idClass,idStudent,idSubject,idSchedule,idScore));
    }

    public void addStudentInClass(long idClass, long idStudent) throws Exception {
        view.printInfoAddJournal(model.addStudentInClass(idClass,idStudent));
    }

    public void addSubjectInClass(long idClass, long idSubject) throws Exception {
        view.printInfoAddJournal(model.addSubjectInClass(idClass,idSubject));
    }

    public void deleteSubjectInClass(long idClass, long idSubject) throws Exception {
        view.printInfoDeleteJournal(model.deleteSubjectInClass(idClass,idSubject));
    }

    public void deleteStudentInClass(long idClass, long idStudent)
    {
        view.printInfoDeleteJournal(model.deleteStudentInClass(idClass,idStudent));
    }

    public void deleteLesson(long idClass, long idSubject, long idSchedule)
    {
        view.printInfoDeleteJournal(model.deleteLesson(idClass,idSubject,idSchedule));
    }

    public void deleteLessonByStudent(long idClass,long idStudent, long idSubject, long idDate)
    {
        view.printInfoDeleteJournal(model.deleteLessonByStudent(idClass,idStudent,idSubject, idDate));
    }

    public JournalDAO getModel() {
        return model;
    }

    public void setModel(JournalDAO model) {
        this.model = model;
    }

    public JournalView getView() {
        return view;
    }

    public void setView(JournalView view) {
        this.view = view;
    }
}
