package Reports.Builders;


import Missions_data.Mission;
import Reports.Decorators.*;

public class RiskReportBuilder extends BaseReportBuilder {


    public RiskReportBuilder(Mission mission) {
        super(mission);
    }
    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "Отчёт о рисках миссии:");
    }
}

