package Storage;

import Manager.PreferencesManager;

import java.io.File;

public class DataSourceFactory {
    public static File createDataSourceJournalXML() throws Exception {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        return new File(preferencesManager.getPathJournalXMLFile());
    }

    public static File createDataSourceSchoolStudentsXML() throws Exception {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        return new File(preferencesManager.getPathStudentsXmlFile());
    }

    public static File createDataSourceScoresXML() throws Exception {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        return new File(preferencesManager.getPathScoresXmlFile());
    }

    public static File createDataSourceSchedulesXML() throws Exception {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        return new File(preferencesManager.getPathSchedulesXmlFile());
    }

    public static File createDataSourceSubjectsXML() throws Exception {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        return new File(preferencesManager.getPathSubjectsXmlFile());
    }
}
