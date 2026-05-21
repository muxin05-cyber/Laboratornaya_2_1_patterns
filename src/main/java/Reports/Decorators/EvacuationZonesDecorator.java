package Reports.Decorators;

import Missions_data.Mission;
import Reports.Builders.ReportComponent;
import java.util.List;

public class EvacuationZonesDecorator extends ReportDecorator {

    private final Mission mission;

    public EvacuationZonesDecorator(ReportComponent wrapped, Mission mission) {
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