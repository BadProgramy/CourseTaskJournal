package Controller;

import DAO.DAOInterface.ScoreDAO;
import DAO.TransferObject.Score;
import View.ScoreView;

public class ScoreController {
    private ScoreDAO model;
    private ScoreView view;

    public ScoreController(ScoreDAO model, ScoreView view) {
        this.model = model;
        this.view = view;
    }

    public void lookScores()
    {
        view.printScores(model.getAllScore());
    }

    public void addScore(Score score)
    {
        view.printInfoAddScore(model.insertScore(score));
    }

    public void deleteScore(long idScore)
    {
        view.printInfoDeleteScore(model.deleteScore(idScore));
    }

    public ScoreDAO getModel() {
        return model;
    }

    public void setModel(ScoreDAO model) {
        this.model = model;
    }

    public ScoreView getView() {
        return view;
    }

    public void setView(ScoreView view) {
        this.view = view;
    }
}
