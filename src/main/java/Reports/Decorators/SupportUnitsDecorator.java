package Reports.Decorators;

import Missions_data.Mission;
import Reports.Builders.ReportComponent;
import java.util.List;

public class SupportUnitsDecorator extends ReportDecorator {

    private final Mission mission;

    public SupportUnitsDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();

        List<String> units = mission.getSupportUnits();

        if (units != null && !units.isEmpty()) {
            sb.append("Вспомогательные подразделения\n");
            for (String unit : units) {
                sb.append("  • ").append(safe(unit)).append("\n");
            }
        }
        sb.append("\n");

        sb.append(super.build());
        return sb.toString();
    }
}