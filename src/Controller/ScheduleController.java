package Controller;

import DAO.DAOInterface.ScheduleDAO;
import DAO.TransferObject.Schedule;
import View.ScheduleView;

public class ScheduleController {
    private ScheduleDAO model;
    private ScheduleView view;

    public ScheduleController(ScheduleDAO model, ScheduleView view) {
        this.model = model;
        this.view = view;
    }

    public void lookSchedules()
    {
        view.printSchedules(model.getAllSchedule());
    }

    public void addSchedule(Schedule schedule)
    {
        view.printInfoAddSchedule(model.insertSchedule(schedule));
    }

    public void deleteSchedule(long idSchedule)
    {
        view.printInfoDeleteSchedule(model.deleteSchedule(idSchedule));
    }


    public ScheduleDAO getModel() {
        return model;
    }

    public void setModel(ScheduleDAO model) {
        this.model = model;
    }

    public ScheduleView getView() {
        return view;
    }

    public void setView(ScheduleView view) {
        this.view = view;
    }
}
