package Reports.Decorators;

import Missions_data.Mission;
import Reports.Builders.ReportComponent;
import java.util.List;

public class RecommendationsDecorator extends ReportDecorator {

    private final Mission mission;

    public RecommendationsDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        List<String> recommendations = mission.getRecommendations();
        if (recommendations != null && !recommendations.isEmpty()) {
            sb.append("Рекомендации:\n");
            for (String rec : recommendations) {
                sb.append("  • ").append(safe(rec)).append("\n");
            }
            sb.append("\n");
        }
        String notes = mission.getNotes();
        if (notes != null && !notes.isEmpty()) {
            sb.append("Примечания\n");
            sb.append("  ").append(notes).append("\n");
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}