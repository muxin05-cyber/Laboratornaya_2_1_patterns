package defaultPackage.Reports.Builders;


import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Decorators.*;

public class StatisticsReportBuilder extends BaseReportBuilder {


    public StatisticsReportBuilder(MissionEntity mission) {
        super(mission);
    }
    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Статистический отчёт:");
    }
}

