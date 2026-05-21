package Reports.Builders;

public interface ReportBuilder {
    void buildMissionInfo();
    void buildCurse();
    void buildSorcerers();
    void buildTechniques();
    void buildRecommendations();
    void buildEnemyActivity();
    void buildEconomicAssessment();
    void buildCivilianImpact();
    void buildEnvironmentConditions();
    void buildTimeLine();
    void buildOperationTags();
    void buildSupportUnits();
    void buildArtifactsRecovered();
    void buildEvacuationZones();
    void buildStatusEffects();

    ReportComponent getResult();
}