package DAO.DAOInterface;

import DAO.TransferObject.SchoolStudent;

import javax.xml.crypto.dsig.TransformException;
import java.util.List;

public interface SchoolStudentDAO {
    boolean insertSchoolStudent (SchoolStudent schoolStudent) throws TransformException;
    boolean deleteSchoolStudent (long id) throws TransformException;
    SchoolStudent findSchoolStudent (long id);
    boolean updateSchoolStudent (long id,SchoolStudent schoolStudent);
    List<SchoolStudent> getAllSchoolStudent ();
    long currentIDInsertSchoolStudent();
}
