package DAO;

import DAO.DAOInterface.*;

public abstract class DAOFactory {
    public static DAOFactory getDaoFactory(Factory nameFactory)
    {
        switch (nameFactory)
        {
            case XML: return new XmlDaoFactory();
        }
        return null;
    }
    public abstract JournalDAO getJournalDAO() throws Exception;
    public abstract ScheduleDAO getScheduleDAO() throws Exception;
    public abstract SchoolStudentDAO getSchoolStudentDAO() throws Exception;
    public abstract ScoreDAO getScoreDAO() throws Exception;
    public abstract SubjectDAO getSubjectDAO() throws Exception;
}
