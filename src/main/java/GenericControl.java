import Missions_data.Mission;
import Parsers.*;

public class GenericControl {
    private Basic_window window;
    private ParsersManagement parsersManagement;
    private ReportsManager reportsManager;


    public GenericControl(Basic_window window) {
        this.window = window;
        this.parsersManagement = new ParsersManagement();
        this.reportsManager = new ReportsManager();
    }

    public String getView(String filepath) {
        Mission mission = assemblingMission(filepath);
        if (mission == null) {
            return null;
        }
        Pair<String, String> pair = reportsManager.getReport(mission);
        String report = pair.getFirst();
        String message = pair.getSecond();
        if (report == null) {
            window.show_error_message(message);
            return null;
        }
        return report;
    }

    public void setReportBuilder(String name) {
        String message = reportsManager.setReportBuilderByName(name);
        if (message != null) {
            window.show_error_message(message);
        }
    }

    private Mission assemblingMission(String filepath) {
        Pair<Mission, String> pair = parsersManagement.FileProcessing(filepath);
        Mission mission = pair.getFirst();
        String message = pair.getSecond();
        if (mission == null) {
            window.show_error_message(message);
        }
        return mission;
    }



    public String[] getReportTypes() {
        return reportsManager.getReportTypes();
    }
}
