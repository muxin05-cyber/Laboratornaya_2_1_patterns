package defaultPackage.Parsers;

import defaultPackage.Missions_data.*;
import defaultPackage.Missions_data.Enums.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BinaryParser implements CommonParser {

    private final MissionFactory factory;

    public BinaryParser() {
        this.factory = new BaseMissionFactory();
    }

    @Override
    public boolean supportType(String filepath) {
        if (filepath == null) return false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                return line.startsWith("MISSION_CREATED|");
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    @Override
    public Pair<MissionEntity, String> parse(String filepath) {
        MissionEntity mission = factory.createMission();
        CurseEntity curse = null;
        EconomicAssessmentEntity economic = null;
        EnvironmentEntity environment = null;
        EnemyActivityEntity enemy = null;
        CivilianImpactEntity civilian = null;
        boolean hasEnemyData = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 0) continue;

                switch (parts[0]) {
                    case "MISSION_CREATED":
                        if (parts.length >= 4) {
                            mission.setMissionId(parts[1]);
                            mission.setDate(parts[2]);
                            mission.setLocation(parts[3]);
                        }
                        break;

                    case "MISSION_COMMENT":
                        if (parts.length >= 2) mission.setComment(parts[1]);
                        break;

                    case "MISSION_NOTE":
                        if (parts.length >= 2) mission.setNotes(parts[1]);
                        break;

                    case "MISSION_RESULT":
                        if (parts.length >= 2) mission.setOutcome(Outcome.fromString(parts[1]));
                        if (parts.length >= 3 && parts[2].startsWith("damageCost="))
                            mission.setDamageCost(parseIntAfterEquals(parts[2]));
                        break;

                    case "CURSE_DETECTED":
                        if (curse == null) curse = factory.createCurse(null, null);
                        if (parts.length >= 2) curse.setName(parts[1]);
                        if (parts.length >= 3) curse.setThreatLevel(ThreatLevel.fromString(parts[2]));
                        break;

                    case "SORCERER_ASSIGNED":
                        if (parts.length >= 3) {
                            mission.getSorcerers().add(
                                    factory.createSorcerer(parts[1], Rank.fromString(parts[2]), mission)
                            );
                        }
                        break;

                    case "TECHNIQUE_USED":
                        if (parts.length >= 5) {
                            mission.getTechniques().add(
                                    factory.createTechnique(parts[1], TechniqueType.fromString(parts[2]),
                                            parts[3], parseIntSafe(parts[4]), mission)
                            );
                        }
                        break;

                    case "ECONOMIC_ASSESSMENT":
                        if (economic == null) economic = factory.createEconomicAssessment();
                        parseKeyValuePairs(economic, parts);
                        break;

                    case "ENVIRONMENT_DATA":
                        if (environment == null) environment = factory.createEnvironment();
                        parseKeyValuePairs(environment, parts);
                        break;

                    case "ENEMY_DATA":
                        if (enemy == null) enemy = factory.createEnemyActivity();
                        hasEnemyData = true;
                        parseKeyValuePairs(enemy, parts);
                        break;

                    case "ENEMY_ACTION":
                        if (enemy == null) enemy = factory.createEnemyActivity();
                        if (parts.length >= 3) enemy.getAttackPatterns().add(parts[2]);
                        break;

                    case "ENEMY_COUNTER":
                        if (enemy == null) enemy = factory.createEnemyActivity();
                        if (parts.length >= 2) enemy.getCountermeasuresUsed().add(parts[1]);
                        break;

                    case "TIMELINE_EVENT":
                        if (parts.length >= 4) {
                            mission.getOperationTimeline().add(
                                    factory.createOperationEvent(parts[1], parts[2], parts[3], mission)
                            );
                        }
                        break;

                    case "CIVILIAN_IMPACT":
                        if (civilian == null) civilian = factory.createCivilianImpact();
                        parseKeyValuePairs(civilian, parts);
                        break;

                    case "OPERATION_TAG":
                        if (parts.length >= 2) mission.getOperationTags().add(parts[1]);
                        break;

                    case "SUPPORT_UNIT":
                        if (parts.length >= 2) mission.getSupportUnits().add(parts[1]);
                        break;

                    case "RECOMMENDATION":
                        if (parts.length >= 2) mission.getRecommendations().add(parts[1]);
                        break;

                    case "ARTIFACT_RECOVERED":
                        if (parts.length >= 2) mission.getArtifactsRecovered().add(parts[1]);
                        break;

                    case "EVACUATION_ZONE":
                        if (parts.length >= 2) mission.getEvacuationZones().add(parts[1]);
                        break;

                    case "STATUS_EFFECT":
                        if (parts.length >= 2) mission.getStatusEffects().add(parts[1]);
                        break;
                }
            }
        } catch (IOException e) {
            return new Pair<>(null, "Ошибка чтения файла: " + e.getMessage());
        }

        if (curse != null) mission.setCurse(curse);
        if (economic != null) mission.setEconomicAssessment(economic);
        if (environment != null) mission.setEnvironment(environment);
        if (hasEnemyData && enemy != null) mission.setEnemyActivity(enemy);
        if (civilian != null) mission.setCivilianImpact(civilian);

        return new Pair<>(mission, "");
    }

    private void parseKeyValuePairs(Object obj, String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            String[] kv = parts[i].split("=", 2);
            if (kv.length != 2) continue;
            String key = kv[0];
            String value = kv[1];

            if (obj instanceof EconomicAssessmentEntity e) {
                switch (key) {
                    case "total": e.setTotalDamageCost(parseIntSafe(value)); break;
                    case "infra": e.setInfrastructureDamage(parseIntSafe(value)); break;
                    case "transport": e.setTransportDamage(parseIntSafe(value)); break;
                    case "commercial": e.setCommercialDamage(parseIntSafe(value)); break;
                    case "recovery": e.setRecoveryEstimateDays(parseIntSafe(value)); break;
                    case "insured": e.setInsuranceCovered(Boolean.parseBoolean(value)); break;
                }
            } else if (obj instanceof EnvironmentEntity e) {
                switch (key) {
                    case "weather": e.setWeather(value); break;
                    case "time": e.setTimeOfDay(value); break;
                    case "visibility": e.setVisibility(Visibility.fromString(value)); break;
                    case "density": e.setCursedEnergyDensity(parseDoubleSafe(value)); break;
                }
            } else if (obj instanceof EnemyActivityEntity e) {
                switch (key) {
                    case "behavior": e.setBehaviorType(value); break;
                    case "target": e.setTargetPriority(value); break;
                    case "mobility": e.setMobility(Mobility.fromString(value)); break;
                    case "risk": e.setEscalationRisk(EscalationRisk.fromString(value)); break;
                }
            } else if (obj instanceof CivilianImpactEntity c) {
                switch (key) {
                    case "evacuated": c.setEvacuated(parseIntSafe(value)); break;
                    case "injured": c.setInjured(parseIntSafe(value)); break;
                    case "missing": c.setMissing(parseIntSafe(value)); break;
                    case "risk": c.setPublicExposureRisk(PublicExposureRisk.fromString(value)); break;
                }
            }
        }
    }

    private int parseIntAfterEquals(String text) {
        String[] parts = text.split("=", 2);
        return parts.length == 2 ? parseIntSafe(parts[1]) : 0;
    }

    private int parseIntSafe(String value) {
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) {
            try { return (int) Double.parseDouble(value); }
            catch (NumberFormatException ex) { return 0; }
        }
    }

    private double parseDoubleSafe(String value) {
        try { return Double.parseDouble(value); }
        catch (NumberFormatException e) { return 0.0; }
    }
}