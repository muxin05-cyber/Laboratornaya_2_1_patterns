package Reports.Builders;

import Missions_data.Mission;
import Reports.Decorators.*;


public abstract class BaseReportBuilder implements ReportBuilder {

    protected ReportComponent component;
    protected final Mission mission;

    public BaseReportBuilder(Mission mission) {
        this.mission = mission;
        this.component = new EmptyReport();
    }

    @Override
    public void buildMissionInfo() {
        component = new MissionInfoDecorator(component, mission, "");
    }

    @Override
    public void buildCurse() {
        component = new CurseDecorator(component, mission);
    }

    @Override
    public void buildSorcerers() {
        component = new SorcerersDecorator(component, mission);
    }

    @Override
    public void buildTechniques() {
        component = new TechniquesDecorator(component, mission);
    }

    @Override
    public void buildEnemyActivity() {
        component = new EnemyActivityDecorator(component, mission);
    }

    @Override
    public void buildEconomicAssessment() {
        component = new EfficiencyDecorator(component, mission);
    }

    @Override
    public void buildCivilianImpact() {
        component = new CivilianImpactDecorator(component, mission);
    }

    @Override
    public void buildEnvironmentConditions() {
        component = new EnvironmentDecorator(component, mission);
    }

    @Override
    public void buildTimeLine() {
        component = new TimeLineDecorator(component, mission);
    }

    @Override
    public void buildOperationTags() {
        component = new OperationTagsDecorator(component, mission);
    }

    @Override
    public void buildSupportUnits() {
        component = new SupportUnitsDecorator(component, mission);
    }

    @Override
    public void buildRecommendations() {
        component = new RecommendationsDecorator(component, mission);
    }


    @Override
    public void buildArtifactsRecovered() {
        component = new ArtifactsRecoveredDecorator(component, mission);
    }

    @Override
    public void buildEvacuationZones() {
        component = new EvacuationZonesDecorator(component, mission);
    }

    @Override
    public void buildStatusEffects() {
        component = new StatusEffectsDecorator(component, mission);
    }

    @Override
    public ReportComponent getResult() {
        return component;
    }
}