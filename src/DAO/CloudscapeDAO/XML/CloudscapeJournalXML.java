package DAO.CloudscapeDAO.XML;

import DAO.DAOInterface.JournalDAO;
import Storage.DataSourceFactory;
import DAO.TransferObject.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CloudscapeJournalXML implements JournalDAO {

    private final File xmlFile;
    private Document document;
    private static final int COUNT_NUMBER_TAG_JOURNAL = 0;//позиция начинается с 0
    private static final long INCREMENT_ID = 1;
    private static final int DEFAULT_ID_SCORE = 6;

    public CloudscapeJournalXML() throws Exception {
        xmlFile = DataSourceFactory.createDataSourceJournalXML();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    @Override
    public boolean addLesson(long idClass, long idSubject, long idSchedule) {//Добавляем занятие
        NodeList journals = document.getElementsByTagName("ClassJournal");
        Element journal;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass)))
                addLessonByIdSubject(idClass, idSubject, idSchedule, DEFAULT_ID_SCORE, journal);
        }
        return saveTransformXML();
    }

    @Override
    public boolean deleteLesson(long idClass, long idSubject, long idSchedule) {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        Element journal;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass)))
                deleteLessonByIdSchedule(idSubject, idSchedule, journal);
        }
        return saveTransformXML();
    }

    @Override
    public boolean addLessonByStudent(long idClass, long idStudent, long idSubject, long idSchedule, long idScore) {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        Element journal;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass)))
                addLessonByIdStudent(idClass, idStudent, idSubject, idSchedule, idScore, journal);
        }
        return saveTransformXML();
    }

    @Override
    public boolean deleteLessonByStudent(long idClass, long idStudent, long idSubject, long idDate) {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        Element journal;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass)))
                deleteLessonByIdStudent(idStudent, idSubject, idDate, journal);
        }
        return saveTransformXML();
    }

    private void addLessonByIdSubject(long idClass, long idSubject, long idSchedule, long idScore, Element journal) {
        NodeList subjects;
        NodeList schoolStudents;
        Element subject;
        Element student;
        subjects = journal.getElementsByTagName("Subject");
        for (int j = 0; j < subjects.getLength(); j++) {
            subject = (Element) subjects.item(j);
            if (subject.getAttribute("id").equals(String.valueOf(idSubject))) {
                schoolStudents = subject.getElementsByTagName("SchoolStudent");
                for (int i = 0; i < schoolStudents.getLength(); i++) {
                    student = (Element) schoolStudents.item(i);
                    student.appendChild(
                            getScore(idClass, idSubject, Long.valueOf(student.getAttribute("id")), idSchedule, idScore));
                }
            }
        }
    }

    private void deleteLessonByIdSchedule(long idSubject, long idSchedule, Element journal) {
        NodeList subjects, schoolStudents, dates;
        Element subject, student, date;
        subjects = journal.getElementsByTagName("Subject");
        for (int j = 0; j < subjects.getLength(); j++) {
            subject = (Element) subjects.item(j);
            if (subject.getAttribute("id").equals(String.valueOf(idSubject))) {
                schoolStudents = subject.getElementsByTagName("SchoolStudent");
                for (int i = 0; i < schoolStudents.getLength(); i++) {
                    student = (Element) schoolStudents.item(i);
                    dates = student.getElementsByTagName("Date");
                    for (int k = 0; k < dates.getLength(); k++) {
                        date = (Element) dates.item(k);
                        if (date.getAttribute("idSchedule").equals(String.valueOf(idSchedule)))
                            student.removeChild(date);
                    }
                }
            }
        }
    }

    private void deleteLessonByIdStudent(long idStudent, long idSubject, long idDate, Element journal) {
        NodeList subjects, schoolStudents, dates;
        Element subject, student, date;
        subjects = journal.getElementsByTagName("Subject");
        for (int j = 0; j < subjects.getLength(); j++) {
            subject = (Element) subjects.item(j);
            if (subject.getAttribute("id").equals(String.valueOf(idSubject))) {
                schoolStudents = subject.getElementsByTagName("SchoolStudent");
                for (int i = 0; i < schoolStudents.getLength(); i++) {
                    student = (Element) schoolStudents.item(i);
                    if (student.getAttribute("id").equals(String.valueOf(idStudent))) {
                        dates = student.getElementsByTagName("Date");
                        for (int k = 0; k < dates.getLength(); k++) {
                            date = (Element) dates.item(k);
                            if (date.getAttribute("id").equals(String.valueOf(idDate)))
                                student.removeChild(date);
                        }
                    }
                }
            }
        }
    }
//гавно метод с кучой параметров, стыдно даже показывать(
    private void addLessonByIdStudent(long idClass, long idStudent, long idSubject, long idSchedule, long idScore, Element journal) {
        NodeList subjects;
        NodeList schoolStudents;
        Element subject;
        Element student;
        subjects = journal.getElementsByTagName("Subject");
        for (int j = 0; j < subjects.getLength(); j++) {
            subject = (Element) subjects.item(j);
            if (subject.getAttribute("id").equals(String.valueOf(idSubject))) {
                schoolStudents = subject.getElementsByTagName("SchoolStudent");
                for (int i = 0; i < schoolStudents.getLength(); i++) {
                    student = (Element) schoolStudents.item(i);
                    if (student.getAttribute("id").equals(String.valueOf(idStudent)))
                        student.appendChild(getScore(idClass, idSubject, idStudent, idSchedule, idScore));
                }
            }
        }
    }

    private Element getScore(long idClass, long idSubject, long idStudent, long idSchedule, long idScore) {
        Element scoreXML = document.createElement("Date");
        scoreXML.setAttribute("id", String.valueOf(currentIDInsertDate(idClass, idSubject, idStudent) + INCREMENT_ID));
        scoreXML.setAttribute("idSchedule", String.valueOf(idSchedule));
        scoreXML.setAttribute("idScore", String.valueOf(idScore));
        scoreXML.setAttribute("idSubject", String.valueOf(idSubject));
        return scoreXML;
    }

    @Override
    public boolean addStudentInClass(long idClass, long idStudent) throws Exception {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        NodeList subjects;
        Element journal;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass))) {
                subjects = journal.getElementsByTagName("Subject");
                for (int j = 0; j < subjects.getLength(); j++) {
                    addStudentInSubject(idStudent, (Element) subjects.item(j));
                }
            }
        }
        return saveTransformXML();
    }

    @Override
    public boolean deleteStudentInClass(long idClass, long idStudent) {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        NodeList subjects;
        Element journal;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass))) {
                subjects = journal.getElementsByTagName("Subject");
                for (int j = 0; j < subjects.getLength(); j++) {
                    if (existStudentInSubject((Element) subjects.item(j),idStudent))
                        deleteStudentInSubject(idStudent, (Element) subjects.item(j));
                }
            }
        }
        return saveTransformXML();
    }

    private void deleteStudentInSubject(long idStudent, Element subject) {
        NodeList students = subject.getElementsByTagName("SchoolStudent");
        Element student;
        for (int i = 0; i < students.getLength(); i++) {
            student = (Element) students.item(i);
            if (student.getAttribute("id").equals(String.valueOf(idStudent)))
                subject.removeChild(student);
        }
    }

    private void addStudentInSubject(long idStudent, Element subject) throws Exception {
        CloudscapeSchoolStudentXML cloudscapeSchoolStudentXML = new CloudscapeSchoolStudentXML();
        if (existStudentInSubject(subject, cloudscapeSchoolStudentXML.findSchoolStudent(idStudent).getId())) {
            Element student = document.createElement("SchoolStudent");
            student.setAttribute("id", String.valueOf(cloudscapeSchoolStudentXML.findSchoolStudent(idStudent).getId()));
            subject.appendChild(student);
        }
    }

    private boolean existStudentInSubject(Element subject, long idStudent) {
        NodeList students = subject.getElementsByTagName("SchoolStudent");
        Element student;
        for (int i = 0; i < students.getLength(); i++) {
            student = (Element) students.item(i);
            if (student.getAttribute("id").equals(String.valueOf(idStudent)))
                return false;
        }
        return true;
    }

    @Override
    public boolean addClassJournal(ClassJournal classJournal) {
        if (existClassJournal(classJournal.getName())) {
            classJournal.setId(currentIDInsertJournal() + INCREMENT_ID);
            NodeList journals = document.getElementsByTagName("SchoolJournals");
            Element lastSchedule = (Element) journals.item(COUNT_NUMBER_TAG_JOURNAL);

            Element journal = document.createElement("ClassJournal");
            journal.setAttribute("id", String.valueOf(classJournal.getId()));
            journal.setAttribute("name", classJournal.getName());
            journal.appendChild(addSubject(classJournal, journal));
            lastSchedule.appendChild(journal);
            return saveTransformXML();
        } else return false;
    }

    private boolean existClassJournal(String name) {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        Element journal;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("name").equals(name)) return false;
        }
        return true;
    }

    @Override
    public boolean addSubjectInClass(long idClass, long idSubject) throws Exception {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        CloudscapeSubjectXML cloudscapeSubjectXML = new CloudscapeSubjectXML();
        Element journal;
        Element subject = document.createElement("Subject");
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass))) {
                if (existSubjectInClass(journal, idSubject)) {
                    subject.setAttribute("id", String.valueOf(cloudscapeSubjectXML.findSubject(idSubject).getId()));
                    journal.appendChild(subject);
                }
                else return false;
            }
        }
        return saveTransformXML();
    }

    private boolean existSubjectInClass(Element journal, long idSubject)
    {
        NodeList subjects = journal.getElementsByTagName("Subject");
        Element subject;
        for (int i = 0; i < subjects.getLength(); i++) {
            subject = (Element) subjects.item(i);
            if (subject.getAttribute("id").equals(String.valueOf(idSubject)))
                return false;
        }
        return true;
    }

    @Override
    public boolean deleteSubjectInClass(long idClass, long idSubject) throws Exception {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        NodeList subjects;
        Element journal, subject;
        for (int i = 0; i < journals.getLength(); i++) {
            journal = (Element) journals.item(i);
            if (journal.getAttribute("id").equals(String.valueOf(idClass)))
            {
                if (existSubjectInClass(journal,idSubject)) {
                    subjects = journal.getElementsByTagName("Subject");
                    for (int j = 0; j < subjects.getLength(); j++) {
                        subject = (Element) subjects.item(j);
                        if (subject.getAttribute("id").equals(String.valueOf(idSubject)))
                            journal.removeChild(subject);
                    }
                }
            }
        }
        return saveTransformXML();
    }

    private boolean addClassJournal(long id, ClassJournal classJournal)
    {
        classJournal.setId(id);
        NodeList journals = document.getElementsByTagName("SchoolJournals");
        Element lastSchedule = (Element) journals.item(COUNT_NUMBER_TAG_JOURNAL);

        Element journal = document.createElement("ClassJournal");
        journal.setAttribute("id",String.valueOf(classJournal.getId()));
        journal.setAttribute("name",classJournal.getName());
        journal.appendChild(addSubject(classJournal,journal));
        lastSchedule.appendChild(journal);
        return saveTransformXML();
    }

    private Element addSubject(ClassJournal classJournal,Element journal)
    {
        Element subjectXML = null;
        if (classJournal.getSubjectDomains() != null)
        for (Subject subject: classJournal.getSubjectDomains()) {
            subjectXML =  document.createElement("Subject");
            subjectXML.setAttribute("id",String.valueOf(subject.getId()));
            subjectXML.setAttribute("name",subject.getName());
            addSchoolStudent(classJournal.getSchoolStudents(),subjectXML);
            journal.appendChild(subjectXML);
        }
        return subjectXML;
    }

    private Element addSchoolStudent(List<SchoolStudent> schoolStudents,Element subject)
    {
        Element schoolStudentXML = null;
        if (schoolStudents != null)
        for (SchoolStudent schoolStudent: schoolStudents) {
            schoolStudentXML = document.createElement("SchoolStudent");
            schoolStudentXML.setAttribute("id",String.valueOf(schoolStudent.getId()));
            addDate(schoolStudent.getScores(),schoolStudentXML);
            subject.appendChild(schoolStudentXML);
        }
        return schoolStudentXML;
    }

    private Element addDate(List<Score> data, Element schoolStudent)
    {
        Element dateXML = null;
        if (data != null)
        for (Score score : data) {
            dateXML = document.createElement("Date");
            dateXML.setAttribute("id",String.valueOf(score.getId()));
            dateXML.setAttribute("idScore",String.valueOf(score.getIdScore()));
            dateXML.setAttribute("idSchedule",String.valueOf(score.getSchedule().getId()));
            dateXML.setAttribute("idSubject",String.valueOf(score.getSubject().getId()));
            schoolStudent.appendChild(dateXML);
        }
        return dateXML;
    }

    @Override
    public boolean deleteClassJournal(long id) {
        NodeList journals = document.getElementsByTagName("SchoolJournals");
        NodeList journal = document.getElementsByTagName("ClassJournal");
        Element element;
        for (int i = 0; i < journal.getLength(); i++) {
            element = (Element) journal.item(i);
            if (element.getAttribute("id").equals(String.valueOf(id)))
                if (existClassJournal(element.getAttribute("name")))
                journals.item(COUNT_NUMBER_TAG_JOURNAL).removeChild(element);
        }
        return saveTransformXML();
    }

    @Override
    public ClassJournal findClassJournal(long id) throws Exception {
        NodeList journals = document.getElementsByTagName("ClassJournal");
        Element element;
        ClassJournal classJournal;
        for (int i = 0; i < journals.getLength(); i++) {
            element = (Element) journals.item(i);
            if (element.getAttribute("id").equals(String.valueOf(id)))
            {
                classJournal = new ClassJournal(element.getAttribute("name"),
                        getStudentsByClass(element),getSubjectsByClass(element));
                classJournal.setId(id);
                return classJournal;
            }
        }
        return null;
    }

    private List<Subject> getSubjectsByClass(Element studentClass) throws Exception {
        List<Subject> subjectList = new ArrayList<>();
        CloudscapeSubjectXML cloudscapeSubjectXML = new CloudscapeSubjectXML();
        Subject subject;
        NodeList subjects = studentClass.getElementsByTagName("Subject");
        for (int i = 0; i < subjects.getLength(); i++) {
            subject = cloudscapeSubjectXML.findSubject(
                    Long.valueOf(((Element)subjects.item(i)).getAttribute("id")));
            subjectList.add(subject);
        }
        return subjectList;
    }

    private List<SchoolStudent> getStudentsByClass(Element studentsClass) throws Exception {
        List<SchoolStudent> schoolStudentList = new ArrayList<>();
        SchoolStudent student;
        CloudscapeSchoolStudentXML cloudscapeSchoolStudentXML = new CloudscapeSchoolStudentXML();
        NodeList subjects = studentsClass.getElementsByTagName("Subject");
        NodeList schoolStudents;
        for (int k = 0; k < subjects.getLength(); k++) {
            schoolStudents = ((Element)subjects.item(k)).getElementsByTagName("SchoolStudent");
            for (int i = 0; i < schoolStudents.getLength(); i++) {
                student = cloudscapeSchoolStudentXML.findSchoolStudent(
                        Long.valueOf(((Element) schoolStudents.item(i)).getAttribute("id")));
                student.setScores(getScoreStudent((Element) schoolStudents.item(i)));
                schoolStudentList.add(student);
            }
        }
        return schoolStudentList;
    }

    private List<Score> getScoreStudent(Element student) throws Exception {
        List<Score> scoresList = new ArrayList<>();
        try {
            Score score;
            CloudscapeScoreXML cloudscapeScoreXML = new CloudscapeScoreXML();
            CloudscapeScheduleXML cloudscapeScheduleXML = new CloudscapeScheduleXML();
            CloudscapeSubjectXML cloudscapeSubjectXML = new CloudscapeSubjectXML();
            NodeList scores = student.getElementsByTagName("Date");
            for (int i = 0; i < scores.getLength(); i++) {
                score = cloudscapeScoreXML.findScore(Long.valueOf(((Element) scores.item(i)).getAttribute("idScore")));
                score.setId(Long.valueOf(((Element) scores.item(i)).getAttribute("id")));
                score.setSchedule(cloudscapeScheduleXML.findSchedule(
                        Long.valueOf(((Element) scores.item(i)).getAttribute("idSchedule"))));
                score.setSubject(cloudscapeSubjectXML.findSubject(
                        Long.valueOf(((Element) scores.item(i)).getAttribute("idSubject"))));
                scoresList.add(score);
            }
            return scoresList;
        }
        catch (NullPointerException ex)
        {
            return scoresList;
        }
    }

    @Override
    public boolean updateClassJournal(long id, ClassJournal classJournal) {
        deleteClassJournal(id);
        return addClassJournal(id, classJournal);
    }

    @Override
    public List<ClassJournal> getAllClassJournal() throws Exception {
        List<ClassJournal> classJournals = new ArrayList<>();
        NodeList journals = document.getElementsByTagName("ClassJournal");
        for (int i = 0; i < journals.getLength(); i++) {
            classJournals.add(findClassJournal(Long.valueOf(((Element)journals.item(i)).getAttribute("id"))));
        }
        return classJournals;
    }

    @Override
    public long currentIDInsertJournal() {
        try {
            NodeList journals = document.getElementsByTagName("ClassJournal");
            Element lastJournal = (Element) journals.item(journals.getLength() - 1);
            return Long.valueOf(lastJournal.getAttribute("id"));
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return 0;
        }
        catch (IndexOutOfBoundsException ex)
        {
            return 0;
        }
    }

    public long currentIDInsertDate(long idClass, long idSubject, long idStudent)//куча гавна, не могу смотреть на него
    {
        try {
            NodeList journals = document.getElementsByTagName("ClassJournal");
            NodeList subjects, students, dates;
            Element journal, subject, student, date;
            for (int i = 0; i < journals.getLength(); i++) {
                journal = (Element) journals.item(i);
                if (journal.getAttribute("id").equals(String.valueOf(idClass))) {
                    subjects = journal.getElementsByTagName("Subject");
                    for (int j = 0; j < subjects.getLength(); j++) {
                        subject = (Element) subjects.item(j);
                        if (subject.getAttribute("id").equals(String.valueOf(idSubject))) {
                            students = subject.getElementsByTagName("SchoolStudent");
                            for (int k = 0; k < students.getLength(); k++) {
                                student = (Element) students.item(k);
                                if (student.getAttribute("id").equals(String.valueOf(idStudent))) {
                                    dates = student.getElementsByTagName("Date");
                                    date = (Element) dates.item(dates.getLength() - 1);
                                    return Long.valueOf(date.getAttribute("id"));
                                }
                            }
                        }
                    }
                }
            }
            return -1;
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            return 0;
        }
        catch (IndexOutOfBoundsException ex)
        {
            return 0;
        }
    }

    private boolean saveTransformXML(){
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile.getPath());
            transformer.transform(source, result);
            return true;
        } catch (TransformerConfigurationException ex) {
            return false;
        } catch (TransformerException ex) {
            return false;
        }
    }
}