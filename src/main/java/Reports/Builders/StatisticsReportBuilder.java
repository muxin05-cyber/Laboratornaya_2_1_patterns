package Reports.Builders;


import Missions_data.Mission;
import Reports.Decorators.*;

public class StatisticsReportBuilder extends BaseReportBuilder {


    public StatisticsReportBuilder(Mission mission) {
        super(mission);
    }
    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Статистический отчёт:");
    }
}

