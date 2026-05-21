package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;
import java.util.List;

public class EvacuationZonesDecorator extends ReportDecorator {

    private final MissionEntity mission;

    public EvacuationZonesDecorator(ReportComponent wrapped, MissionEntity mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        List<String> zones = mission.getEvacuationZones();
        if (zones != null && !zones.isEmpty()) {
            sb.append("Зоны эвакуации\n");
            for (String zone : zones) {
                sb.append("  • ").append(safe(zone)).append("\n");
            }
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}