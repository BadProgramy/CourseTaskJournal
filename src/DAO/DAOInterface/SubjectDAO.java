package DAO.DAOInterface;

import DAO.TransferObject.Subject;

import java.util.List;

public interface SubjectDAO {
    boolean insertSubject (Subject subject);
    boolean deleteSubject (long id);
    Subject findSubject (long id);
    boolean updateSubject (long id, Subject subject);
    List<Subject> getAllSubject ();
    long currentIDInsertSubject();
}
