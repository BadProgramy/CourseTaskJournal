package Controller;

import DAO.DAOInterface.SubjectDAO;
import DAO.TransferObject.Subject;
import View.SubjectView;

public class SubjectController {
    private SubjectDAO model;
    private SubjectView view;

    public SubjectController(SubjectDAO model, SubjectView view) {
        this.model = model;
        this.view = view;
    }

    public void lookSubjects()
    {
        view.printSubjects(model.getAllSubject());
    }

    public void addSubject(Subject subject)
    {
        view.printInfoAddSubject(model.insertSubject(subject));
    }

    public void deleteSubject(long idSubject)
    {
        view.printInfoDeleteSubject(model.deleteSubject(idSubject));
    }

    public Subject getSubjectById(long id)
    {
        return model.findSubject(id);
    }

    public SubjectDAO getModel() {
        return model;
    }

    public void setModel(SubjectDAO model) {
        this.model = model;
    }

    public SubjectView getView() {
        return view;
    }

    public void setView(SubjectView view) {
        this.view = view;
    }
}
