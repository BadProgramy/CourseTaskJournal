package DAO.CloudscapeDAO.XML;

import DAO.DAOInterface.SchoolStudentDAO;
import Storage.DataSourceFactory;
import DAO.TransferObject.SchoolStudent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

public class CloudscapeSchoolStudentXML implements SchoolStudentDAO {
    private final File xmlFile;
    private Document document;
    private static final int COUNT_NUMBER_TAG_SCHOOLSTUDENTS = 0;//позиция начинается с 0
    private static final long INCREMENT_ID = 1;

    public CloudscapeSchoolStudentXML() throws Exception {
        xmlFile = DataSourceFactory.createDataSourceSchoolStudentsXML();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    @Override
    public boolean insertSchoolStudent(SchoolStudent schoolStudent){
        if (existStudent(schoolStudent)) {
            schoolStudent.setId(currentIDInsertSchoolStudent() + INCREMENT_ID);
            NodeList schoolStudents = document.getElementsByTagName("SchoolStudents");
            Element lastStudents = (Element) schoolStudents.item(COUNT_NUMBER_TAG_SCHOOLSTUDENTS);

            Element student = document.createElement("SchoolStudent");
            student.setAttribute("id", String.valueOf(schoolStudent.getId()));
            student.setAttribute("firstName", schoolStudent.getFirstName());
            student.setAttribute("secondName", schoolStudent.getSecondName());
            lastStudents.appendChild(student);
            return saveTransformXML();
        }
        else return false;
    }

    private boolean existStudent(SchoolStudent student)
    {
        NodeList students = document.getElementsByTagName("SchoolStudent");
        Element schoolStudent;
        for (int i = 0; i < students.getLength(); i++) {
            schoolStudent = (Element) students.item(i);
            if (schoolStudent.getAttribute("firstName").equals(String.valueOf(student.getFirstName())) &&
                    schoolStudent.getAttribute("secondName").equals(String.valueOf(student.getSecondName())))
                return false;
        }
        return true;
    }

    @Override
    public boolean deleteSchoolStudent(long id){
        NodeList schoolStudents = document.getElementsByTagName("SchoolStudents");
        NodeList students = document.getElementsByTagName("SchoolStudent");
        Element student;
        for (int i = 0; i < students.getLength(); i++) {
            student = (Element) students.item(i);
            if (student.getAttribute("id").equals(String.valueOf(id))) {
                schoolStudents.item(COUNT_NUMBER_TAG_SCHOOLSTUDENTS).removeChild(student);
                return saveTransformXML();
            }
        }
        return false;
    }

    @Override
    public SchoolStudent findSchoolStudent(long id) {
        NodeList students = document.getElementsByTagName("SchoolStudent");
        Element student;
        SchoolStudent schoolStudent;
        for (int i = 0; i < students.getLength(); i++) {
            student = (Element) students.item(i);
            if (student.getAttribute("id").equals(String.valueOf(id)))
            {
                schoolStudent = new SchoolStudent(student.getAttribute("firstName"),
                        student.getAttribute("secondName"),null);
                schoolStudent.setId(id);
                return schoolStudent;
            }
        }
        return null;
    }

    @Override
    public boolean updateSchoolStudent(long id,SchoolStudent schoolStudent) {
        deleteSchoolStudent(id);
        return insertSchoolStudent(schoolStudent);
    }

    @Override
    public List<SchoolStudent> getAllSchoolStudent() {
        List<SchoolStudent> schoolStudents = new ArrayList<>();
        NodeList students = document.getElementsByTagName("SchoolStudent");
        for (int i = 0; i < students.getLength(); i++) {
            schoolStudents.add(findSchoolStudent(Long.valueOf(((Element) students.item(i)).getAttribute("id"))));
        }
        return schoolStudents;
    }

    @Override
    public long currentIDInsertSchoolStudent() {
        NodeList scores = document.getElementsByTagName("SchoolStudent");
        Element lastScore = (Element) scores.item(scores.getLength()-1);
        return Long.valueOf(lastScore.getAttribute("id"));
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
