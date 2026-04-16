package Reports.Decorators;

import Missions_data.Mission;
import Reports.Builders.ReportComponent;
import java.util.List;

public class ArtifactsRecoveredDecorator extends ReportDecorator {

    private final Mission mission;

    public ArtifactsRecoveredDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();



        List<String> artifacts = mission.getArtifactsRecovered();

        if (artifacts != null && !artifacts.isEmpty()) {
            sb.append("Найденные артефакты:\n");
            for (String artifact : artifacts) {
                sb.append("  • ").append(safe(artifact)).append("\n");
            }
        }
        sb.append("\n");

        sb.append(super.build());
        return sb.toString();
    }
}