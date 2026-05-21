package defaultPackage.Reports.Builders;


import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Decorators.*;

public class RiskReportBuilder extends BaseReportBuilder {


    public RiskReportBuilder(MissionEntity mission) {
        super(mission);
    }
    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Отчёт о рисках миссии:");
    }
}

