package Reports.Decorators;

import Missions_data.Mission;
import Missions_data.OperationEvent;
import Reports.Builders.ReportComponent;
import java.util.List;

public class TimeLineDecorator extends ReportDecorator {

    private final Mission mission;

    public TimeLineDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        List<OperationEvent> timeline = mission.getOperationTimeline();

        if (timeline != null && !timeline.isEmpty()) {
            sb.append("Таймлайн происходивших событий:").append("\n");
            for (int i = 0; i < timeline.size(); i++) {
                OperationEvent event = timeline.get(i);
                sb.append("  ").append(i + 1).append(". ");
                sb.append("[").append(safe(event.getTimestamp())).append("] ");
                sb.append(safe(event.getType())).append("\n");
                sb.append("     ").append(safe(event.getDescription())).append("\n");
                if (i < timeline.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        sb.append("\n");

        sb.append(super.build());
        return sb.toString();
    }
}