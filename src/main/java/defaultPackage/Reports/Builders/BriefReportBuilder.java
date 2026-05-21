package defaultPackage.Reports.Builders;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Decorators.MissionInfoDecorator;

public class BriefReportBuilder extends BaseReportBuilder {
    public BriefReportBuilder(MissionEntity mission) {
        super(mission);
    }

    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Краткий отчёт о миссии:");
    }

}