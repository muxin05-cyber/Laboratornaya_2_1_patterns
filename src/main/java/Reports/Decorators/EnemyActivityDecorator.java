package Reports.Decorators;


import Missions_data.Mission;
import Missions_data.EnemyActivity;
import Reports.Builders.ReportComponent;

import java.util.List;

public class EnemyActivityDecorator extends ReportDecorator {

    private final Mission mission;

    public EnemyActivityDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();

        if (mission.getEnemyActivity() != null) {
            EnemyActivity ea = mission.getEnemyActivity();
            sb.append("Активность противника: \n");
            sb.append("  Тип поведения:   ").append(safe(ea.getBehaviorType())).append("\n");
            sb.append("  Приоритет целей: ").append(safe(ea.getTargetPriority())).append("\n");
            if(ea.getMobility()!=null){
                sb.append("  Мобильность:     ").append(safe(ea.getMobility().getValue())).append("\n");
            }else{
                sb.append("  Мобильность:     ").append("UNKNOWN").append("\n");
            }
            if(ea.getEscalationRisk()!= null){
                sb.append("  Риск эскалации:  ").append(safe(ea.getEscalationRisk().getValue())).append("\n");
            }else{
                sb.append("  Риск эскалации:  ").append("UNKNOWN").append("\n");
            }
            sb.append("\n");
            if(ea.getAttackPatterns() != null){
                sb.append("Приёмы атак: \n");
                int i = 0;
                for(String s: ea.getAttackPatterns()){
                    i++;
                    sb.append(i).append(" - ").append(s).append("\n");
                }
            }

            if(ea.getCountermeasuresUsed() != null){
                sb.append("\nИспользуемые контрмеры: \n");
                int i = 0;
                for(String s: ea.getCountermeasuresUsed()){
                    i++;
                    sb.append(i).append(" - ").append(s).append("\n");
                }
                sb.append("\n");
            }
        }

        sb.append(super.build());
        return sb.toString();
    }
}