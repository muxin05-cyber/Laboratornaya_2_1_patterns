package Reports.Decorators;

import Missions_data.Curse;
import Missions_data.Environment;
import Missions_data.Mission;
import Reports.Builders.ReportComponent;

public class EnvironmentDecorator extends ReportDecorator{
    private final Mission mission;

    public EnvironmentDecorator(ReportComponent wrapped, Mission mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();

        Environment environment = mission.getEnvironment();
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
