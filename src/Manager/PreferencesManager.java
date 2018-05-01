package Manager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Properties;

public class PreferencesManager {
    private static PreferencesManager instance;
    private Document document;
    private String FILE_PATH = "src\\Resources\\Configuration\\appconfig.xml";

    private PreferencesManager() throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        if (new File(FILE_PATH).exists()==false) FILE_PATH="Resources\\Configuration\\appconfig.xml";
        document = builder.parse(new File(FILE_PATH));
    }

    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            instance = new PreferencesManager();
        return instance;
    }

    public String getPathJournalXMLFile()
    {
        return getElement("pathJournal").getTextContent();
    }

    public void setPathJournalXMLFile(String value)
    {
        getElement("pathJournal").setTextContent(value);
    }

    public String getPathStudentsXmlFile() {return getElement("pathStudents").getTextContent();}

    public void setPathStudentsXmlFile(String value) {getElement("pathStudents").setTextContent(value);}

    public String getPathScoresXmlFile() {return getElement("pathScores").getTextContent();}

    public void  setPathScoresXmlFile(String value) {getElement("pathScores").setTextContent(value);}

    public String getPathSchedulesXmlFile() {return getElement("pathSchedules").getTextContent();}

    public void setPathSchedulesXmlFile(String value) {getElement("pathSchedules").setTextContent(value);}

    public String getPathSubjectsXmlFile() {return getElement("pathSubjects").getTextContent();}

    public void setPathSubjectsXmlFile(String value) {getElement("pathSubjects").setTextContent(value);}

    private Element getElement(String nameField) {
        String[] field = nameField.split("\\.");
        NodeList nodeList = document.getElementsByTagName(field[field.length - 1]);
        Node node = nodeList.item(0);
        return (Element) node;
    }

    public void setProperty(String key, String value) {
        getElement(key).setTextContent(value);
    }

    public String getProperty(String key) {
        return getElement(key).getTextContent();
    }

    public void setProperties(Properties prop) {
        while (prop.elements().hasMoreElements()) {
            getElement((String) prop.elements().nextElement()).setTextContent((String) prop.keys().nextElement());
        }
    }

    public Properties getProperties()
    {
        Properties prop = new Properties();
        String[] keys = new String[]{
                PreferencesConstantManager.XMLPATH
        };

        for (String key : keys) {
            setProperty(key, getElement(key).getTextContent());
        }
        return prop;
    }
}
