package defaultPackage.Missions_data;

import defaultPackage.Missions_data.Enums.*;

public interface MissionFactory {
    MissionEntity createMission();
    CurseEntity createCurse(String name, ThreatLevel threatLevel);
    SorcererEntity createSorcerer(String name, Rank rank, MissionEntity mission);
    TechniqueEntity createTechnique(String name, TechniqueType type, String owner, int damage, MissionEntity mission);
    OperationEventEntity createOperationEvent(String timestamp, String type, String description, MissionEntity mission);
    EconomicAssessmentEntity createEconomicAssessment();
    CivilianImpactEntity createCivilianImpact();
    EnvironmentEntity createEnvironment();
    EnemyActivityEntity createEnemyActivity();
}