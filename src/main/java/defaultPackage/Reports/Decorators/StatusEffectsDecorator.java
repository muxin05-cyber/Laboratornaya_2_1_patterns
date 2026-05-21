package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;
import java.util.List;

public class StatusEffectsDecorator extends ReportDecorator {

    private final MissionEntity mission;

    public StatusEffectsDecorator(ReportComponent wrapped, MissionEntity mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        List<String> effects = mission.getStatusEffects();
        if (effects != null && !effects.isEmpty()) {
            sb.append("Эффекты и состояния:\n");
            for (String effect : effects) {
                sb.append("  • ").append(safe(effect)).append("\n");
            }
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}