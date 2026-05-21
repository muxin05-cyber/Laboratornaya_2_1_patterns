package defaultPackage.Reports.Builders;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Decorators.MissionInfoDecorator;

public class DetailedReportBuilder extends BaseReportBuilder {
    public DetailedReportBuilder(MissionEntity mission) {
        super(mission);
    }

    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Полный отчёт о миссии:");
    }

}