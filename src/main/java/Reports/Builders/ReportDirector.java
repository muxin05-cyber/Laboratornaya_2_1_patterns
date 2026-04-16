package Reports.Builders;


public class ReportDirector {

    private final ReportBuilder builder;

    public ReportDirector(ReportBuilder builder) {
        this.builder = builder;
    }

    public ReportComponent constructBriefReport() {
        builder.buildCurse();
        builder.buildMissionInfo();
        return builder.getResult();
    }

    public ReportComponent constructDetailedReport() {
        builder.buildRecommendations();
        builder.buildArtifactsRecovered();
        builder.buildEvacuationZones();
        builder.buildStatusEffects();
        builder.buildSupportUnits();
        builder.buildEnemyActivity();
        builder.buildCivilianImpact();
        builder.buildOperationTags();
        builder.buildTimeLine();
        builder.buildEnvironmentConditions();
        builder.buildEconomicAssessment();
        builder.buildSorcerers();
        builder.buildTechniques();
        builder.buildCurse();
        builder.buildMissionInfo();
        return builder.getResult();
    }

    public ReportComponent constructRiskReport() {
        builder.buildRecommendations();
        builder.buildEnemyActivity();
        builder.buildCivilianImpact();
        builder.buildCurse();
        builder.buildMissionInfo();
        return builder.getResult();
    }

    public ReportComponent constructStatisticsReport() {
        builder.buildRecommendations();
        builder.buildSupportUnits();
        builder.buildEconomicAssessment();
        builder.buildCivilianImpact();
        builder.buildCurse();
        builder.buildMissionInfo();
        return builder.getResult();
    }
}