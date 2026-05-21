package Reports.Decorators;

import Missions_data.Mission;
import Reports.Builders.ReportComponent;

public class MissionInfoDecorator extends ReportDecorator {

    private final Mission mission;
    String title;
    public MissionInfoDecorator(ReportComponent wrapped, Mission mission, String title) {
        super(wrapped);
        this.mission = mission;
        this.title = title;
    }
    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ").append(title).append("\n\n");
        sb.append("Общая информация: \n");
        sb.append("  ID миссии:     ").append(safe(mission.getMissionId())).append("\n");
        sb.append("  Дата:          ").append(safe(mission.getDate())).append("\n");
        sb.append("  Локация:       ").append(safe(mission.getLocation())).append("\n");
        if(mission.getOutcome() != null){
            sb.append("  Результат:     ").append(safe(mission.getOutcome().getValue())).append("\n");
        }else{
            sb.append("  Результат:     ").append("UNKNOWN").append("\n");
        }

        if (mission.getDamageCost() > 0) {
            sb.append("  Ущерб:         ").append(mission.getDamageCost()).append("\n");
        }
        if (mission.getComment() != null){
            sb.append("  Комментарий:     ").append(safe(mission.getComment())).append("\n");
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}