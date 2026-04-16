package Reports.Decorators;


import Missions_data.Mission;
import Missions_data.Sorcerer;
import Reports.Builders.ReportComponent;

public class SorcerersDecorator extends ReportDecorator {

    private final Mission mission;

    public SorcerersDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();



        if (mission.getSorcerers() != null && !mission.getSorcerers().isEmpty()) {
            sb.append("Участники миссии:\n");
            for (int i = 0; i < mission.getSorcerers().size(); i++) {
                Sorcerer s = mission.getSorcerers().get(i);
                sb.append("  ").append(i + 1).append(". ");
                sb.append(safe(s.getName()));
                if(s.getRank() != null){
                    sb.append(" — ").append(safe(s.getRank().getValue())).append("\n");
                }else{
                    sb.append(" — ").append("UNKNOWN").append("\n");
                }
            }
        }
        sb.append("\n");

        sb.append(super.build());
        return sb.toString();
    }
}