package DAO.TransferObject;

import java.util.ArrayList;
import java.util.List;

public class ClassJournal {
    private long id;
    private String name;
    private List<SchoolStudent> schoolStudents;
    private List<Subject> subjectDomains;
    private static final int DEFAULT_EXIST_ID_STUDENT = 0;

    public ClassJournal(String name, List<SchoolStudent> schoolStudents, List<Subject> subjectDomains) {
        this.name = name;
        if (schoolStudents!=null)
            this.schoolStudents = schoolStudents;
        else  this.schoolStudents = new ArrayList<>();
        if (subjectDomains!=null)
            this.subjectDomains = subjectDomains;
        else this.subjectDomains = new ArrayList<>();
    }


    public SchoolStudent getStudent()
    {
        return schoolStudents.get(DEFAULT_EXIST_ID_STUDENT);
    }

    public SchoolStudent getStudent(long idStudent)
    {
        for (SchoolStudent student: schoolStudents) {
            if (student.getId() == idStudent) return student;
        }
        return null;
    }

    public List<SchoolStudent> getSchoolStudentsByIdSubject(long idSubject)
    {
        List<SchoolStudent> students = new ArrayList<>();
        for (SchoolStudent student: schoolStudents) {
            if (student.getScoresByIdSubject(idSubject).size()>0)
                students.add(student);
        }
        return students;
    }

    public void addStudent(SchoolStudent student)
    {
        if (student!=null)
        schoolStudents.add(student);
    }

    public void addSubject(Subject subject)
    {
        if (subject!=null)
        subjectDomains.add(subject);
    }

    public List<Subject> getSubjectDomains() {
        return subjectDomains;
    }

    public void setSubjectDomains(List<Subject> subjectDomains) {
        this.subjectDomains = subjectDomains;
    }

    public List<SchoolStudent> getSchoolStudents() {
        return schoolStudents;
    }

    public void setSchoolStudents(List<SchoolStudent> schoolStudents) {
        this.schoolStudents = schoolStudents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder journal = new StringBuilder(String.valueOf(id)).append(". Журнал ")
                .append(name).append(" класса\n");
        journal.append("У них преподаются предметы:\n");
        for (Subject subject: subjectDomains) {
            journal.append(subject).append("\n");
        }
        for (SchoolStudent student: schoolStudents) {
            journal.append(student);
        }
        return journal.toString();
    }
}
