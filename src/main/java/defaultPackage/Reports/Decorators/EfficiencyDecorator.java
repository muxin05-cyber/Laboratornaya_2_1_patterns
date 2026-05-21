package defaultPackage.Reports.Decorators;
import defaultPackage.Missions_data.EconomicAssessmentEntity;
import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Reports.Builders.ReportComponent;


public class EfficiencyDecorator extends ReportDecorator{
    private final MissionEntity mission;

    public EfficiencyDecorator(ReportComponent wrapped, MissionEntity mission) {
        super(wrapped);
        this.mission = mission;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        EconomicAssessmentEntity economicAssessment = mission.getEconomicAssessment();
        if (economicAssessment != null) {
            sb.append("Экономическая оценка: \n");
            sb.append("  Полный ущерб:      ").append(safe(Integer.toString(economicAssessment.getTotalDamageCost()))).append("\n");
            sb.append("  Ущерб инфраструктуры:      ").append(safe(Integer.toString(economicAssessment.getInfrastructureDamage()))).append("\n");
            sb.append("  Ущерб транспорту:      ").append(safe(Integer.toString(economicAssessment.getTransportDamage()))).append("\n");
            sb.append("  Коммерческий ущерб:      ").append(safe(Integer.toString(economicAssessment.getCommercialDamage()))).append("\n");
            sb.append("  Количество дней на восстановление:      ").append(safe(Integer.toString(economicAssessment.getRecoveryEstimateDays()))).append("\n");
            sb.append("  Покрытие страховкой:      ").append(safe(Boolean.toString(economicAssessment.isInsuranceCovered()))).append("\n");
            sb.append("\n");
        }
        sb.append(super.build());
        return sb.toString();
    }

}
