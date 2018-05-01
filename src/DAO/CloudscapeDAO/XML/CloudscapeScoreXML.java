package DAO.CloudscapeDAO.XML;

import DAO.DAOInterface.ScoreDAO;
import DAO.TransferObject.Score;
import Storage.DataSourceFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CloudscapeScoreXML implements ScoreDAO {
    private final File xmlFile;
    private Document document;
    private static final int COUNT_NUMBER_TAG_SCORES = 0;//позиция начинается с 0
    private static final long INCREMENT_ID = 1;

    public CloudscapeScoreXML() throws Exception {
        xmlFile = DataSourceFactory.createDataSourceScoresXML();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    @Override
    public boolean insertScore(Score score) {
        if (existScore(score)) {
            score.setIdScore(currentIDInsertScore() + INCREMENT_ID);
            NodeList scores = document.getElementsByTagName("Scores");
            Element lastScores = (Element) scores.item(COUNT_NUMBER_TAG_SCORES);

            Element scoreLocalVar = document.createElement("Score");
            scoreLocalVar.setAttribute("id", String.valueOf(score.getIdScore()));
            scoreLocalVar.setTextContent(score.getScore());
            lastScores.appendChild(scoreLocalVar);
            return saveTransformXML();
        }
        else return false;
    }

    private boolean existScore(Score score)
    {
        NodeList scores = document.getElementsByTagName("Score");
        Element tempScore;
        for (int i = 0; i < scores.getLength(); i++) {
            tempScore = (Element) scores.item(i);
            if (tempScore.getTextContent().equals(String.valueOf(score.getScore())))
                return false;
        }
        return true;
    }

    @Override
    public boolean deleteScore(long id) {
        NodeList scoresMain = document.getElementsByTagName("Scores");
        NodeList scores = document.getElementsByTagName("Score");
        Element score;
        for (int i = 0; i < scores.getLength(); i++) {
            score = (Element) scores.item(i);
            if (score.getAttribute("id").equals(String.valueOf(id))) {
                scoresMain.item(COUNT_NUMBER_TAG_SCORES).removeChild(score);
                return saveTransformXML();
            }
        }
        return false;
    }

    @Override
    public Score findScore(long id) {
        NodeList scores = document.getElementsByTagName("Score");
        Element element;
        Score score;
        for (int i = 0; i < scores.getLength(); i++) {
            element = (Element) scores.item(i);
            if (element.getAttribute("id").equals(String.valueOf(id)))
            {
                score = new Score(null,null,element.getTextContent());
                score.setIdScore(id);
                return score;
            }
        }
        return null;
    }

    @Override
    public boolean updateScore(long id, Score score) {
        deleteScore(id);
        return insertScore(score);
    }

    @Override
    public List<Score> getAllScore() {
        List<Score> scoreList = new ArrayList<>();
        NodeList scores = document.getElementsByTagName("Score");
        for (int i = 0; i < scores.getLength(); i++) {
            scoreList.add(findScore(Long.valueOf(((Element) scores.item(i)).getAttribute("id"))));
        }
        return scoreList;
    }

    public long currentIDInsertScore()
    {
        NodeList scores = document.getElementsByTagName("Score");
        Element lastScore = (Element) scores.item(scores.getLength()-1);
        return Long.valueOf(lastScore.getAttribute("id"));
    }

    private boolean saveTransformXML(){
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile.getPath());
            transformer.transform(source, result);
            return true;
        } catch (TransformerConfigurationException ex) {
            return false;
        } catch (TransformerException ex) {
            return false;
        }
    }
}
