package defaultPackage.Missions_data;

import defaultPackage.Missions_data.Enums.*;
import java.util.ArrayList;

public class BaseMissionFactory implements MissionFactory {
    @Override
    public MissionEntity createMission() {
        MissionEntity mission = new MissionEntity();
        mission.setSorcerers(new ArrayList<>());
        mission.setTechniques(new ArrayList<>());
        mission.setOperationTimeline(new ArrayList<>());
        mission.setOperationTags(new ArrayList<>());
        mission.setSupportUnits(new ArrayList<>());
        mission.setRecommendations(new ArrayList<>());
        mission.setArtifactsRecovered(new ArrayList<>());
        mission.setEvacuationZones(new ArrayList<>());
        mission.setStatusEffects(new ArrayList<>());
        return mission;
    }

    @Override
    public CurseEntity createCurse(String name, ThreatLevel threatLevel) {
        CurseEntity curse = new CurseEntity();
        curse.setName(name != null ? name : "Неизвестное проклятие");
        curse.setThreatLevel(threatLevel != null ? threatLevel : ThreatLevel.UNKNOWN);
        return curse;
    }

    @Override
    public SorcererEntity createSorcerer(String name, Rank rank, MissionEntity mission) {
        SorcererEntity s = new SorcererEntity();
        s.setName(name != null ? name : "Неизвестный маг");
        s.setRank(rank != null ? rank : Rank.UNKNOWN);
        s.setMission(mission);
        return s;
    }

    @Override
    public TechniqueEntity createTechnique(String name, TechniqueType type, String owner, int damage, MissionEntity mission) {
        TechniqueEntity t = new TechniqueEntity();
        t.setName(name != null ? name : "Неизвестная техника");
        t.setType(type != null ? type : TechniqueType.UNKNOWN);
        t.setOwner(owner != null ? owner : "Неизвестный владелец");
        t.setDamage(damage);
        t.setMission(mission);
        return t;
    }

    @Override
    public OperationEventEntity createOperationEvent(String timestamp, String type, String description, MissionEntity mission) {
        OperationEventEntity e = new OperationEventEntity();
        e.setTimestamp(timestamp);
        e.setType(type);
        e.setDescription(description);
        e.setMission(mission);
        return e;
    }

    @Override
    public EconomicAssessmentEntity createEconomicAssessment() {
        return new EconomicAssessmentEntity();
    }

    @Override
    public CivilianImpactEntity createCivilianImpact() {
        return new CivilianImpactEntity();
    }

    @Override
    public EnvironmentEntity createEnvironment() {
        return new EnvironmentEntity();
    }

    @Override
    public EnemyActivityEntity createEnemyActivity() {
        EnemyActivityEntity enemy = new EnemyActivityEntity();
        enemy.setAttackPatterns(new ArrayList<>());
        enemy.setCountermeasuresUsed(new ArrayList<>());
        return enemy;
    }
}