package Controller;

import DAO.DAOInterface.SchoolStudentDAO;
import DAO.TransferObject.SchoolStudent;
import View.StudentView;

import javax.xml.crypto.dsig.TransformException;

public class StudentController {
    private SchoolStudentDAO model;
    private StudentView view;

    public StudentController(SchoolStudentDAO model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void lookStudents()
    {
        view.printStudents(model.getAllSchoolStudent());
    }

    public void addStudent(SchoolStudent student) throws TransformException {
        view.printInfoAddStudent(model.insertSchoolStudent(student));
    }

    public void deleteStudent(long idStudent) throws TransformException {
        view.printInfoDeleteStudent(model.deleteSchoolStudent(idStudent));
    }

    public SchoolStudent getStudentById(long id)
    {
        return model.findSchoolStudent(id);
    }
}
