package Reports.Builders;

import Missions_data.Mission;
import Reports.Decorators.MissionInfoDecorator;

public class DetailedReportBuilder extends BaseReportBuilder {
    public DetailedReportBuilder(Mission mission) {
        super(mission);
    }

    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Полный отчёт о миссии:");
    }

}