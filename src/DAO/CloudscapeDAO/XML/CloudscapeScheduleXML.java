package DAO.CloudscapeDAO.XML;

import DAO.DAOInterface.ScheduleDAO;
import Storage.DataSourceFactory;
import DAO.TransferObject.Schedule;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CloudscapeScheduleXML implements ScheduleDAO {
    private final File xmlFile;
    private Document document;
    private static final int COUNT_NUMBER_TAG_SCHEDULE = 0;//позиция начинается с 0
    private static final long INCREMENT_ID = 1;

    public CloudscapeScheduleXML() throws Exception {
        xmlFile = DataSourceFactory.createDataSourceSchedulesXML();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    @Override
    public boolean insertSchedule(Schedule schedule) {
        if (existSchedule(schedule)) {
            schedule.setId(currentIDInsertSchedule() + INCREMENT_ID);
            NodeList schedules = document.getElementsByTagName("Schedules");
            Element lastSchedule = (Element) schedules.item(COUNT_NUMBER_TAG_SCHEDULE);

            Element element = document.createElement("Time");
            element.setAttribute("id", String.valueOf(schedule.getId()));
            element.setAttribute("day", String.valueOf(schedule.getDate().getDayOfMonth()));
            element.setAttribute("month", String.valueOf(schedule.getDate().getMonthValue()));
            element.setAttribute("year", String.valueOf(schedule.getDate().getYear()));

            lastSchedule.appendChild(element);
            return saveTransformXML();
        }
        else return false;
    }

    private boolean existSchedule(Schedule schedule)
    {
        NodeList times = document.getElementsByTagName("Time");
        Element time;
        for (int i = 0; i < times.getLength(); i++) {
            time = (Element) times.item(i);
            if (time.getAttribute("day").equals(String.valueOf(schedule.getDate().getDayOfMonth())) &&
                    time.getAttribute("month").equals(String.valueOf(schedule.getDate().getMonthValue())) &&
                    time.getAttribute("year").equals(String.valueOf(schedule.getDate().getYear())))
                return false;
        }
        return true;
    }

    @Override
    public boolean deleteSchedule(long id) {
        NodeList schedules = document.getElementsByTagName("Schedules");
        NodeList times = document.getElementsByTagName("Time");
        Element element;
        for (int i = 0; i < times.getLength(); i++) {
            element = (Element) times.item(i);
            if (element.getAttribute("id").equals(String.valueOf(id))) {
                schedules.item(COUNT_NUMBER_TAG_SCHEDULE).removeChild(element);
                return saveTransformXML();
            }
        }
        return false;
    }

    @Override
    public Schedule findSchedule(long id) {
        NodeList times = document.getElementsByTagName("Time");
        Element element;
        Schedule schedule;
        for (int i = 0; i < times.getLength(); i++) {
            element = (Element) times.item(i);
            if (element.getAttribute("id").equals(String.valueOf(id)))
            {
                schedule = new Schedule(LocalDate.of(Integer.parseInt(element.getAttribute("year")),
                        Integer.parseInt(element.getAttribute("month")),
                        Integer.parseInt(element.getAttribute("day"))));
                schedule.setId(id);
                return schedule;
            }
        }
        return null;
    }

    @Override
    public boolean updateSchedule(long id,Schedule schedule) {
        deleteSchedule(id);
        return insertSchedule(schedule);
    }

    @Override
    public List<Schedule> getAllSchedule() {
        List<Schedule> schedules = new ArrayList<>();
        NodeList times = document.getElementsByTagName("Time");
        for (int i = 0; i < times.getLength(); i++) {
            schedules.add(findSchedule(Long.valueOf(((Element) times.item(i)).getAttribute("id"))));
        }
        return schedules;
    }

    @Override
    public long currentIDInsertSchedule() {
        NodeList schedules = document.getElementsByTagName("Time");
        Element lastSchedule = (Element) schedules.item(schedules.getLength()-1);
        return Long.valueOf(lastSchedule.getAttribute("id"));
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
