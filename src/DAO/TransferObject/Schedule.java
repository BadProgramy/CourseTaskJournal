package DAO.TransferObject;

import java.time.LocalDate;

public class Schedule {
    private long id;
    private LocalDate date;

    public Schedule(LocalDate date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder schedule = new StringBuilder(date.toString());
        return schedule.toString();
    }
}
