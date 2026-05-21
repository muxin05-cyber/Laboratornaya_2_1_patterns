package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;
import java.util.List;

public class SupportUnitsDecorator extends ReportDecorator {

    private final MissionEntity mission;

    public SupportUnitsDecorator(ReportComponent wrapped, MissionEntity mission) {
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
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}