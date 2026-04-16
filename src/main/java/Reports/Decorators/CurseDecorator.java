package Reports.Decorators;

import Missions_data.Mission;
import Missions_data.Curse;
import Reports.Builders.ReportComponent;

public class CurseDecorator extends ReportDecorator {

    private final Mission mission;

    public CurseDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("Проклятие: \n");
        Curse curse = mission.getCurse();
        if (curse != null) {
            sb.append("  Название:      ").append(safe(curse.getName())).append("\n");
            if (curse.getThreatLevel() != null){
                sb.append("  Уровень угрозы: ").append(safe(curse.getThreatLevel().getValue())).append("\n");
            }else{
                sb.append("  Уровень угрозы: ").append(safe("UNKNOWN")).append("\n");
            }
        } else {
            sb.append("  Проклятие не указано\n");
        }
        sb.append("\n");
        sb.append(super.build());
        return sb.toString();
    }
}