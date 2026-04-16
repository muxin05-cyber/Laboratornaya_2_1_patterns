package Missions_data;

import Missions_data.Enums.*;

public interface MissionFactory {

    Mission createMission();
    Curse createCurse(String name, ThreatLevel threatLevel);
    Sorcerer createSorcerer(String name, Rank rank);
    Technique createTechnique(String name, TechniqueType type, String owner, int damage);
    EconomicAssessment createEconomicAssessment();
    CivilianImpact createCivilianImpact();
    Environment createEnvironment();
    EnemyActivity createEnemyActivity();
    OperationEvent createOperationEvent(String timestamp, String type, String description);

    void addSorcerer(Mission mission, String name, Rank rank);
    void addTechnique(Mission mission, String name, TechniqueType type, String owner, int damage);
    void addOperationEvent(Mission mission, OperationEvent event);
    void addOperationTag(Mission mission, String tag);
    void addSupportUnit(Mission mission, String unit);
    void addRecommendation(Mission mission, String recommendation);
    void addArtifact(Mission mission, String artifact);
    void addEvacuationZone(Mission mission, String zone);
    void addStatusEffect(Mission mission, String effect);
    void addAttackPattern(EnemyActivity enemy, String pattern);
    void addCountermeasure(EnemyActivity enemy, String measure);
}