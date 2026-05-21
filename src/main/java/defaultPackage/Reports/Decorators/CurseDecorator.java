package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Missions_data.CurseEntity;
import defaultPackage.Reports.Builders.ReportComponent;

public class CurseDecorator extends ReportDecorator {

    private final MissionEntity mission;

    public CurseDecorator(ReportComponent wrapped, MissionEntity mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("Проклятие: \n");
        CurseEntity curse = mission.getCurse();
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