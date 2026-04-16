package Reports.Decorators;

import Missions_data.Mission;
import Reports.Builders.ReportComponent;
import java.util.List;

public class StatusEffectsDecorator extends ReportDecorator {

    private final Mission mission;

    public StatusEffectsDecorator(ReportComponent wrapped, Mission mission) {
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
        }
        sb.append("\n");

        sb.append(super.build());
        return sb.toString();
    }
}