package Reports.Builders;

import Missions_data.Mission;
import Reports.Decorators.MissionInfoDecorator;

public class BriefReportBuilder extends BaseReportBuilder {
    public BriefReportBuilder(Mission mission) {
        super(mission);
    }

    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Краткий отчёт о миссии:");
    }

}