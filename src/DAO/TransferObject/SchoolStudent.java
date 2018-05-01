package DAO.TransferObject;

import java.util.ArrayList;
import java.util.List;

public class SchoolStudent {
    private long id;
    private String firstName;
    private String secondName;
    private List<Score> scores;

    public SchoolStudent( String firstName, String secondName, List<Score> scores) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.scores = scores;
    }

    public List<Score> getScoresByIdSubject(long idSubject)
    {
        List<Score> data = new ArrayList<>();
        for (Score score : scores) {
            if (score.getSubject().getId() == idSubject)
                data.add(score);
        }
        return data;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        StringBuilder student = new StringBuilder("Ученик:\n")
                .append(id).append(". ")
                .append(firstName).append(" ")
                .append(secondName).append("\n");
        student.append("Оценки\n");
        if (scores != null)
        for (Score score : this.scores) {
            student.append(score);
        }
        return student.toString();
    }
}
