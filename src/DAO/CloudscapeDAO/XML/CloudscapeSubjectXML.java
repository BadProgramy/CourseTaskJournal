package DAO.CloudscapeDAO.XML;

import DAO.DAOInterface.SubjectDAO;
import Storage.DataSourceFactory;
import DAO.TransferObject.Subject;
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

public class CloudscapeSubjectXML implements SubjectDAO {
    private final File xmlFile;
    private Document document;
    private static final int COUNT_NUMBER_TAG_SCHOOLSTUDENTS = 0;//позиция начинается с 0
    private static final long INCREMENT_ID = 1;

    public CloudscapeSubjectXML() throws Exception {
        xmlFile = DataSourceFactory.createDataSourceSubjectsXML();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    @Override
    public boolean insertSubject(Subject subject) {
        if (existSubject(subject)) {
            subject.setId(currentIDInsertSubject() + INCREMENT_ID);
            NodeList subjects = document.getElementsByTagName("Subjects");
            Element lastSubject = (Element) subjects.item(COUNT_NUMBER_TAG_SCHOOLSTUDENTS);

            Element element = document.createElement("Subject");
            element.setAttribute("id", String.valueOf(subject.getId()));
            element.setTextContent(subject.getName());

            lastSubject.appendChild(element);
            return saveTransformXML();
        }
        else return false;
    }

    private boolean existSubject(Subject subject)
    {
        NodeList subjects = document.getElementsByTagName("Subject");
        Element tempSubject;
        for (int i = 0; i < subjects.getLength(); i++) {
            tempSubject = (Element) subjects.item(i);
            if (tempSubject.getTextContent().equals(String.valueOf(subject.getName())))
                return false;
        }
        return true;
    }

    @Override
    public boolean deleteSubject(long id) {
        NodeList subjects = document.getElementsByTagName("Subjects");
        NodeList subject = document.getElementsByTagName("Subject");
        Element element;
        for (int i = 0; i < subject.getLength(); i++) {
            element = (Element) subject.item(i);
            if (element.getAttribute("id").equals(String.valueOf(id))) {
                subjects.item(COUNT_NUMBER_TAG_SCHOOLSTUDENTS).removeChild(element);
                return saveTransformXML();
            }
        }
        return false;
    }

    @Override
    public Subject findSubject(long id) {
        NodeList subjects = document.getElementsByTagName("Subject");
        Element element;
        Subject subject;
        for (int i = 0; i < subjects.getLength(); i++) {
            element = (Element) subjects.item(i);
            if (element.getAttribute("id").equals(String.valueOf(id)))
            {
                subject = new Subject(element.getTextContent());
                subject.setId(id);
                return subject;
            }
        }
        return null;
    }

    @Override
    public boolean updateSubject(long id, Subject subject) {
        deleteSubject(id);
        return insertSubject(subject);
    }

    @Override
    public List<Subject> getAllSubject() {
        List<Subject> subjects = new ArrayList<>();
        NodeList subject = document.getElementsByTagName("Subject");
        for (int i = 0; i < subject.getLength(); i++) {
            subjects.add(findSubject(Long.valueOf(((Element) subject.item(i)).getAttribute("id"))));
        }
        return subjects;
    }

    @Override
    public long currentIDInsertSubject() {
        NodeList subject = document.getElementsByTagName("Subject");
        Element lastSubject = (Element) subject.item(subject.getLength()-1);
        return Long.valueOf(lastSubject.getAttribute("id"));
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
