package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.CivilianImpactEntity;
import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;

public class CivilianImpactDecorator extends ReportDecorator {

    private final MissionEntity mission;

    public CivilianImpactDecorator(ReportComponent wrapped, MissionEntity mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        CivilianImpactEntity civilianImpact = mission.getCivilianImpact();
        if (civilianImpact != null) {
            sb.append("Урон гражданским: \n");
            sb.append("  Количество эвакуированных:      ").append(safe(String.valueOf(civilianImpact.getEvacuated()))).append("\n");
            sb.append("  Количество пострадавших:      ").append(safe(String.valueOf(civilianImpact.getInjured()))).append("\n");
            sb.append("  Количество пропавших:      ").append(safe(String.valueOf(civilianImpact.getMissing()))).append("\n");
            if(civilianImpact.getPublicExposureRisk() != null){
                sb.append("  Риск разоблачения: ").append(safe(civilianImpact.getPublicExposureRisk().getValue())).append("\n");
            }else{
                sb.append("  Риск разоблачения: ").append("UNKNOWN").append("\n");
            }
        }
        sb.append("\n");

        sb.append(super.build());
        return sb.toString();
    }
}