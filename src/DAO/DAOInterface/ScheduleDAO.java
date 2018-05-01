package DAO.DAOInterface;

import DAO.TransferObject.Schedule;

import java.util.List;

public interface ScheduleDAO {
    boolean insertSchedule (Schedule schedule);
    boolean deleteSchedule (long id);
    Schedule findSchedule (long id);
    boolean updateSchedule (long id, Schedule schedule);
    List<Schedule> getAllSchedule ();
    long currentIDInsertSchedule();
}
