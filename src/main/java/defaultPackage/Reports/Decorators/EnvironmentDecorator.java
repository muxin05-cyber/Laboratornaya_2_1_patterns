package defaultPackage.Reports.Decorators;

import defaultPackage.Missions_data.EnvironmentEntity;
import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;

public class EnvironmentDecorator extends ReportDecorator{
    private final MissionEntity mission;

    public EnvironmentDecorator(ReportComponent wrapped, MissionEntity mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        EnvironmentEntity environment = mission.getEnvironment();
        if (environment != null) {
            sb.append("\nУсловия окружающей среды: \n");
            sb.append("  Погода:      ").append(safe(environment.getWeather())).append("\n");
            sb.append("  Время дня: ").append(safe(environment.getTimeOfDay())).append("\n");
            if(environment.getVisibility() !=null){
                sb.append("  Видимость: ").append(safe(environment.getVisibility().getValue())).append("\n");
            }else{
                sb.append("  Видимость: ").append("UNKNOWN").append("\n");
            }
            sb.append("  Плотность энергии проклятия: ").append(safe(Double.toString(environment.getCursedEnergyDensity()))).append("\n");
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }
}
