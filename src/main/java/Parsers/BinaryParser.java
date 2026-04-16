package Parsers;

import Missions_data.*;
import Missions_data.Enums.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BinaryParser implements CommonParser {

    private final MissionFactory factory;

    public BinaryParser() {
        this.factory = new BaseMissionFactory();
    }


    @Override
    public boolean supportType(String filepath) {
        if (filepath == null) return false;
        String lower = filepath.toLowerCase();

        if (lower.endsWith(".xml") || lower.endsWith(".json") ||
                lower.endsWith(".txt") || lower.endsWith(".yaml") || lower.endsWith(".yml")) {
            return false;
        }

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
    public Pair<Mission, String> parse(String filepath) {
        Mission mission = factory.createMission();

        List<Sorcerer> sorcerers = new ArrayList<>();
        List<Technique> techniques = new ArrayList<>();
        List<OperationEvent> timeline = new ArrayList<>();

        Curse curse = null;
        EconomicAssessment economic = null;
        Environment environment = null;
        EnemyActivity enemy = null;
        CivilianImpact civilian = null;

        boolean hasEnemyData = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 0) continue;

                String command = parts[0];

                switch (command) {
                    case "MISSION_CREATED":
                        if (parts.length >= 4) {
                            mission.setMissionId(parts[1]);
                            mission.setDate(parts[2]);
                            mission.setLocation(parts[3]);
                        }
                        break;

                    case "CURSE_DETECTED":
                        if (curse == null) curse = factory.createCurse(null, null);
                        if (parts.length >= 3) {
                            curse.setName(parts[1]);
                            curse.setThreatLevel(ThreatLevel.fromString(parts[2]));
                        }
                        break;

                    case "SORCERER_ASSIGNED":
                        if (parts.length >= 3) {
                            Sorcerer s = factory.createSorcerer(parts[1], Rank.fromString(parts[2]));
                            sorcerers.add(s);
                        }
                        break;

                    case "TECHNIQUE_USED":
                        if (parts.length >= 5) {
                            Technique t = factory.createTechnique(
                                    parts[1],
                                    TechniqueType.fromString(parts[2]),
                                    parts[3],
                                    parseIntSafe(parts[4])
                            );
                            techniques.add(t);
                        }
                        break;

                    case "ECONOMIC_ASSESSMENT":
                        if (economic == null) economic = factory.createEconomicAssessment();
                        parseEconomicPipeFormat(economic, parts);
                        break;

                    case "ENVIRONMENT_DATA":
                        if (environment == null) environment = factory.createEnvironment();
                        parseEnvironmentPipeFormat(environment, parts);
                        break;

                    case "ENEMY_DATA":
                        if (enemy == null) enemy = factory.createEnemyActivity();
                        hasEnemyData = true;
                        parseEnemyPipeFormat(enemy, parts);
                        break;

                    case "ENEMY_ATTACK":
                        if (parts.length >= 2 && enemy != null) {
                            factory.addAttackPattern(enemy, parts[1]);
                        }
                        break;

                    case "ENEMY_ACTION":
                        if (parts.length >= 2 && enemy != null) {
                            // Сохраняем тип атаки (DIRECT_ASSAULT, TRAP_USAGE, SHADOW_ATTACK)
                            factory.addAttackPattern(enemy, parts[2]);
                        }
                        break;

                    case "ENEMY_COUNTER":
                        if (parts.length >= 2 && enemy != null) {
                            factory.addCountermeasure(enemy, parts[1]);
                        }
                        break;

                    case "TIMELINE_EVENT":
                        if (parts.length >= 4) {
                            OperationEvent e = factory.createOperationEvent(parts[1], parts[2], parts[3]);
                            timeline.add(e);
                        }
                        break;

                    case "CIVILIAN_IMPACT":
                        if (civilian == null) civilian = factory.createCivilianImpact();
                        parseCivilianPipeFormat(civilian, parts);
                        break;

                    case "OPERATION_TAG":
                        if (parts.length >= 2) {
                            factory.addOperationTag(mission, parts[1]);
                        }
                        break;

                    case "SUPPORT_UNIT":
                        if (parts.length >= 2) {
                            factory.addSupportUnit(mission, parts[1]);
                        }
                        break;

                    case "RECOMMENDATION":
                        if (parts.length >= 2) {
                            factory.addRecommendation(mission, parts[1]);
                        }
                        break;

                    case "ARTIFACT_RECOVERED":
                        if (parts.length >= 2) {
                            factory.addArtifact(mission, parts[1]);
                        }
                        break;

                    case "EVACUATION_ZONE":
                        if (parts.length >= 2) {
                            factory.addEvacuationZone(mission, parts[1]);
                        }
                        break;

                    case "STATUS_EFFECT":
                        if (parts.length >= 2) {
                            factory.addStatusEffect(mission, parts[1]);
                        }
                        break;

                    case "MISSION_NOTE":
                        if (parts.length >= 2) {
                            mission.setNotes(parts[1]);
                        }
                        break;

                    case "MISSION_COMMENT":
                        if (parts.length >= 2) {
                            mission.setComment(parts[1]);
                        }
                        break;

                    case "MISSION_RESULT":
                        if (parts.length >= 2) {
                            mission.setOutcome(Outcome.fromString(parts[1]));
                        }
                        if (parts.length >= 3) {
                            String[] kv = parts[2].split("=");
                            if (kv.length == 2 && kv[0].equals("damageCost")) {
                                mission.setDamageCost(parseIntSafe(kv[1]));
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            return new Pair<>(null, "Структура файла не соответствует шаблону миссий для бинарных файлов");
        }

        if (curse != null) mission.setCurse(curse);
        if (!sorcerers.isEmpty()) mission.setSorcerers(sorcerers);
        if (!techniques.isEmpty()) mission.setTechniques(techniques);
        if (!timeline.isEmpty()) mission.setOperationTimeline(timeline);
        if (economic != null) mission.setEconomicAssessment(economic);
        if (environment != null) mission.setEnvironment(environment);

        if (hasEnemyData && enemy != null) {
            mission.setEnemyActivity(enemy);
        }

        if (civilian != null) mission.setCivilianImpact(civilian);

        return new Pair<>(mission, "");
    }

    private void parseEconomicPipeFormat(EconomicAssessment e, String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            String[] kv = parts[i].split("=");
            if (kv.length != 2) continue;
            switch (kv[0]) {
                case "total": e.setTotalDamageCost(parseIntSafe(kv[1])); break;
                case "infra": e.setInfrastructureDamage(parseIntSafe(kv[1])); break;
                case "transport": e.setTransportDamage(parseIntSafe(kv[1])); break;
                case "commercial": e.setCommercialDamage(parseIntSafe(kv[1])); break;
                case "recovery": e.setRecoveryEstimateDays(parseIntSafe(kv[1])); break;
                case "insured": e.setInsuranceCovered(Boolean.parseBoolean(kv[1])); break;
            }
        }
    }

    private void parseEnvironmentPipeFormat(Environment e, String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            String[] kv = parts[i].split("=");
            if (kv.length != 2) continue;
            switch (kv[0]) {
                case "weather": e.setWeather(kv[1]); break;
                case "time": e.setTimeOfDay(kv[1]); break;
                case "visibility": e.setVisibility(Visibility.fromString(kv[1])); break;
                case "density": e.setCursedEnergyDensity(parseDoubleSafe(kv[1])); break;
            }
        }
    }

    private void parseEnemyPipeFormat(EnemyActivity e, String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            String[] kv = parts[i].split("=");
            if (kv.length != 2) continue;
            switch (kv[0]) {
                case "behavior": e.setBehaviorType(kv[1]); break;
                case "target": e.setTargetPriority(kv[1]); break;
                case "mobility": e.setMobility(Mobility.fromString(kv[1])); break;
                case "risk": e.setEscalationRisk(EscalationRisk.fromString(kv[1])); break;
                case "retreat": e.setRetreatStrategy(kv[1]); break;
                case "coord": e.setCoordinationLevel(kv[1]); break;
            }
        }
    }

    private void parseCivilianPipeFormat(CivilianImpact c, String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            String[] kv = parts[i].split("=");
            if (kv.length != 2) continue;
            switch (kv[0]) {
                case "evacuated": c.setEvacuated(parseIntSafe(kv[1])); break;
                case "injured": c.setInjured(parseIntSafe(kv[1])); break;
                case "missing": c.setMissing(parseIntSafe(kv[1])); break;
                case "risk": c.setPublicExposureRisk(PublicExposureRisk.fromString(kv[1])); break;
            }
        }
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                return (int) Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }

    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}