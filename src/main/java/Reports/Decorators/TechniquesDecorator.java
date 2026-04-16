package Reports.Decorators;


import Missions_data.Mission;
import Missions_data.Technique;
import Reports.Builders.ReportComponent;

public class TechniquesDecorator extends ReportDecorator {

    private final Mission mission;

    public TechniquesDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();



        if (mission.getTechniques() != null && !mission.getTechniques().isEmpty()) {
            sb.append("Применённые техники\n");
            for (int i = 0; i < mission.getTechniques().size(); i++) {
                Technique t = mission.getTechniques().get(i);
                sb.append("  ").append(i + 1).append(". ").append(safe(t.getName())).append("\n");
                if(t.getType() != null){
                    sb.append("      Тип:     ").append(safe(t.getType().getValue())).append("\n");
                }else{
                    sb.append("      Тип:     ").append("UNKNOWN").append("\n");
                }

                sb.append("      Владелец: ").append(safe(t.getOwner())).append("\n");
                sb.append("      Урон:    ").append(safeNumber(t.getDamage())).append("\n");
                if (i < mission.getTechniques().size() - 1) sb.append("\n");
            }
        }
        sb.append("\n");

        sb.append(super.build());
        return sb.toString();
    }
}
