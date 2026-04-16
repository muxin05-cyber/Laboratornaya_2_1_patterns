package Missions_data;

import Missions_data.Enums.*;
import java.util.ArrayList;

public class BaseMissionFactory implements MissionFactory {

    private static BaseMissionFactory instance;

    public BaseMissionFactory() {}


    @Override
    public Mission createMission() {
        Mission mission = new Mission();
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
    public Curse createCurse(String name, ThreatLevel threatLevel) {
        Curse curse = new Curse();
        curse.setName(name != null ? name : "Неизвестное проклятие");
        curse.setThreatLevel(threatLevel != null ? threatLevel : ThreatLevel.UNKNOWN);
        return curse;
    }

    @Override
    public Sorcerer createSorcerer(String name, Rank rank) {
        Sorcerer sorcerer = new Sorcerer();
        sorcerer.setName(name != null ? name : "Неизвестный маг");
        sorcerer.setRank(rank != null ? rank : Rank.UNKNOWN);
        return sorcerer;
    }

    @Override
    public Technique createTechnique(String name, TechniqueType type, String owner, int damage) {
        Technique technique = new Technique();
        technique.setName(name != null ? name : "Неизвестная техника");
        technique.setType(type != null ? type : TechniqueType.UNKNOWN);
        technique.setOwner(owner != null ? owner : "Неизвестный владелец");
        technique.setDamage(damage);
        return technique;
    }


    @Override
    public EconomicAssessment createEconomicAssessment() {
        return new EconomicAssessment();
    }

    @Override
    public CivilianImpact createCivilianImpact() {
        return new CivilianImpact();
    }

    @Override
    public Environment createEnvironment() {
        return new Environment();
    }

    @Override
    public EnemyActivity createEnemyActivity() {
        EnemyActivity enemy = new EnemyActivity();
        enemy.setAttackPatterns(new ArrayList<>());
        enemy.setCountermeasuresUsed(new ArrayList<>());
        return enemy;
    }

    @Override
    public OperationEvent createOperationEvent(String timestamp, String type, String description) {
        return new OperationEvent(timestamp, type, description);
    }


    @Override
    public void addSorcerer(Mission mission, String name, Rank rank) {
        if (mission.getSorcerers() == null) {
            mission.setSorcerers(new ArrayList<>());
        }
        mission.getSorcerers().add(createSorcerer(name, rank));
    }

    @Override
    public void addTechnique(Mission mission, String name, TechniqueType type, String owner, int damage) {
        if (mission.getTechniques() == null) {
            mission.setTechniques(new ArrayList<>());
        }
        mission.getTechniques().add(createTechnique(name, type, owner, damage));
    }

    @Override
    public void addOperationEvent(Mission mission, OperationEvent event) {
        if (mission.getOperationTimeline() == null) {
            mission.setOperationTimeline(new ArrayList<>());
        }
        mission.getOperationTimeline().add(event);
    }

    @Override
    public void addOperationTag(Mission mission, String tag) {
        if (mission.getOperationTags() == null) {
            mission.setOperationTags(new ArrayList<>());
        }
        mission.getOperationTags().add(tag);
    }

    @Override
    public void addSupportUnit(Mission mission, String unit) {
        if (mission.getSupportUnits() == null) {
            mission.setSupportUnits(new ArrayList<>());
        }
        mission.getSupportUnits().add(unit);
    }

    @Override
    public void addRecommendation(Mission mission, String recommendation) {
        if (mission.getRecommendations() == null) {
            mission.setRecommendations(new ArrayList<>());
        }
        mission.getRecommendations().add(recommendation);
    }

    @Override
    public void addArtifact(Mission mission, String artifact) {
        if (mission.getArtifactsRecovered() == null) {
            mission.setArtifactsRecovered(new ArrayList<>());
        }
        mission.getArtifactsRecovered().add(artifact);
    }

    @Override
    public void addEvacuationZone(Mission mission, String zone) {
        if (mission.getEvacuationZones() == null) {
            mission.setEvacuationZones(new ArrayList<>());
        }
        mission.getEvacuationZones().add(zone);
    }

    @Override
    public void addStatusEffect(Mission mission, String effect) {
        if (mission.getStatusEffects() == null) {
            mission.setStatusEffects(new ArrayList<>());
        }
        mission.getStatusEffects().add(effect);
    }



    @Override
    public void addAttackPattern(EnemyActivity enemy, String pattern) {
        if (enemy.getAttackPatterns() == null) {
            enemy.setAttackPatterns(new ArrayList<>());
        }
        enemy.getAttackPatterns().add(pattern);
    }

    @Override
    public void addCountermeasure(EnemyActivity enemy, String measure) {
        if (enemy.getCountermeasuresUsed() == null) {
            enemy.setCountermeasuresUsed(new ArrayList<>());
        }
        enemy.getCountermeasuresUsed().add(measure);
    }
}