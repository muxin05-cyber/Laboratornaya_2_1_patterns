package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;
import java.util.List;

public class ArtifactsRecoveredDecorator extends ReportDecorator {

    private final MissionEntity mission;

    public ArtifactsRecoveredDecorator(ReportComponent wrapped, MissionEntity mission) {
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
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}