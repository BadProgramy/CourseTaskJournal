package DAO;

import DAO.DAOInterface.*;
import DAO.CloudscapeDAO.XML.*;

public class XmlDaoFactory extends DAOFactory{
    @Override
    public JournalDAO getJournalDAO() throws Exception {
        return new CloudscapeJournalXML();
    }

    @Override
    public ScheduleDAO getScheduleDAO() throws Exception {
        return new CloudscapeScheduleXML();
    }

    @Override
    public SchoolStudentDAO getSchoolStudentDAO() throws Exception {
        return new CloudscapeSchoolStudentXML();
    }

    @Override
    public ScoreDAO getScoreDAO() throws Exception {
        return new CloudscapeScoreXML();
    }

    @Override
    public SubjectDAO getSubjectDAO() throws Exception {
        return new CloudscapeSubjectXML();
    }
}
