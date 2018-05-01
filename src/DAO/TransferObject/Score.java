package DAO.TransferObject;

public class Score {
    private long id;
    private long idScore;
    private Subject subject;
    private Schedule schedule;
    private String score;

    public Score(Subject subject, Schedule schedule, String score) {
        this.subject = subject;
        this.schedule = schedule;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdScore(long idScore) {
        this.idScore = idScore;
    }

    public long getIdScore() {
        return idScore;
    }

    public Subject getSubject() {
        return subject;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getScore() {
        return score;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        StringBuilder score = new StringBuilder()
                .append(id).append(". ")
                .append(subject).append(" ").append(schedule).append(" ").append("ОЦЕНКА - ").append(this.score)
                .append("\n");
        return score.toString();
    }
}
