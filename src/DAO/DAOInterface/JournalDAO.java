package DAO.DAOInterface;

import DAO.TransferObject.ClassJournal;

import java.util.List;

public interface JournalDAO {
    boolean addClassJournal (ClassJournal classJournal);
    boolean addLesson(long idClass, long idSubject, long idSchedule);
    boolean addLessonByStudent(long idClass,long idStudent, long idSubject, long idSchedule, long idScore);
    boolean addStudentInClass(long idClass, long idStudent) throws Exception;
    boolean addSubjectInClass(long idClass, long idSubject) throws Exception;
    boolean deleteClassJournal (long id);
    boolean deleteLesson(long idClass, long idSubject, long idSchedule);
    boolean deleteLessonByStudent(long idClass, long idStudent, long idSubject, long idDate);
    boolean deleteStudentInClass(long idClass, long idStudent);
    boolean deleteSubjectInClass(long idClass, long idSubject) throws Exception;
    ClassJournal findClassJournal (long id) throws Exception;
    boolean updateClassJournal (long id, ClassJournal classJournal);
    List<ClassJournal> getAllClassJournal () throws Exception;
    long currentIDInsertJournal();
}
