package View;

import DAO.TransferObject.Score;

import java.util.List;

public class ScoreView {
    public void printScores(List<Score> data)
    {
        System.out.println("Введите номер оценки\n");
        for (Score score : data) {
            System.out.println(score.getIdScore() + ". " + score.getScore());
        }
    }

    public void printInfoAddScore(boolean isAdd)
    {
        if (isAdd) System.out.println("Успешно добавлено");
        else System.out.println("Не удалось добавить");
    }

    public void printInfoDeleteScore(boolean isDelete)
    {
        if (isDelete) System.out.println("Успешно добавлено");
        else System.out.println("Не удалось добавить");
    }
}
