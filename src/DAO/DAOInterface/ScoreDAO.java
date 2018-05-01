package DAO.DAOInterface;

import DAO.TransferObject.Score;

import java.util.List;

public interface ScoreDAO {
    boolean insertScore (Score score);
    boolean deleteScore (long id);
    Score findScore (long id);
    boolean updateScore (long id, Score score);
    List<Score> getAllScore ();
    long currentIDInsertScore();
}
