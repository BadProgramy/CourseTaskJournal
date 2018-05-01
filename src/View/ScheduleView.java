package View;


import DAO.TransferObject.Schedule;

import java.util.List;

public class ScheduleView {
    public void printSchedules(List<Schedule> schedules)
    {
        System.out.println("Введите номер расписания\n");
        for (Schedule schedule: schedules) {
            System.out.println(schedule.getId() + ". "+ schedule);
        }
        System.out.println();
    }

    public void printInfoAddSchedule(boolean isAdd)
    {
        if (isAdd) System.out.println("Успешно добавлено");
        else System.out.println("Не добавлено");
    }

    public void printInfoDeleteSchedule(boolean isDelete)
    {
        if (isDelete) System.out.println("Успешно удалено");
        else System.out.println("Не удалено");
    }
}
