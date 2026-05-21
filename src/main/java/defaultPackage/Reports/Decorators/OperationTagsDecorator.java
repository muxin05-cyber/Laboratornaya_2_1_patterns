package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;
import java.util.List;

public class OperationTagsDecorator extends ReportDecorator {

    private final MissionEntity mission;

    public OperationTagsDecorator(ReportComponent wrapped, MissionEntity mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        List<String> tags = mission.getOperationTags();
        if (tags != null && !tags.isEmpty()) {
            sb.append("Теги миссии: \n");
            for (String tag : tags) {
                sb.append("  • ").append(safe(tag)).append("\n");
            }
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}