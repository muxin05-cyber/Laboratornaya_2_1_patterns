package Parsers;

import Missions_data.*;
import Missions_data.Enums.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class YAMLParser implements CommonParser {

    private final MissionFactory factory;

    public YAMLParser() {
        this.factory = new BaseMissionFactory();
    }

    @Override
    public boolean supportType(String filepath) {
        return filepath != null && (filepath.endsWith(".yaml") || filepath.endsWith(".yml"));
    }

    @Override
    public Pair<Mission, String> parse(String filepath) {
        try (InputStream inputStream = new FileInputStream(filepath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            if (data == null) {
                return new Pair<>(null, "Файл пуст или имеет неверный формат");
            }

            Mission mission = factory.createMission();
            parseAllFields(mission, data);
            return new Pair<>(mission, "");

        } catch (FileNotFoundException e) {
            return new Pair<>(null, "Файл не найден: " + filepath);
        } catch (Exception e) {
            return new Pair<>(null, "Структура файла не соответствует шаблону YAML");
        }
    }

    private void parseAllFields(Mission mission, Map<String, Object> data) {
        mission.setMissionId(getString(data, "missionId"));
        mission.setLocation(getString(data, "location"));
        mission.setComment(getString(data, "comment"));
        mission.setNotes(getString(data, "notes"));
        Object dateObj = data.get("date");
        if (dateObj instanceof String) {
            mission.setDate((String) dateObj);
        } else if (dateObj instanceof Date) {
            mission.setDate(new SimpleDateFormat("yyyy-MM-dd").format((Date) dateObj));
        }
        mission.setOutcome(Outcome.fromString(getString(data, "outcome")));
        mission.setDamageCost(getInt(data, "damageCost"));
        parseCurse(mission, data);
        parseSorcerers(mission, data);
        parseTechniques(mission, data);
        parseEnemyActivity(mission, data);
        parseEconomicAssessment(mission, data);
        parseCivilianImpact(mission, data);
        parseEnvironment(mission, data);
        parseOperationTimeline(mission, data);
        parseStringList(data, "operationTags", mission::addOperationTag);
        parseStringList(data, "supportUnits", mission::addSupportUnit);
        parseStringList(data, "recommendations", mission::addRecommendation);
        parseStringList(data, "artifactsRecovered", mission::addArtifact);
        parseStringList(data, "evacuationZones", mission::addEvacuationZone);
        parseStringList(data, "statusEffects", mission::addStatusEffect);
    }

    private void parseCurse(Mission mission, Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "curse");
        if (map == null) return;

        Curse curse = factory.createCurse(
                getString(map, "name"),
                ThreatLevel.fromString(getString(map, "threatLevel"))
        );
        mission.setCurse(curse);
    }

    private void parseSorcerers(Mission mission, Map<String, Object> data) {
        List<Map<String, Object>> list = getListOfMaps(data, "sorcerers");
        for (Map<String, Object> map : list) {
            factory.addSorcerer(mission,
                    getString(map, "name"),
                    Rank.fromString(getString(map, "rank"))
            );
        }
    }

    private void parseTechniques(Mission mission, Map<String, Object> data) {
        List<Map<String, Object>> list = getListOfMaps(data, "techniques");
        for (Map<String, Object> map : list) {
            factory.addTechnique(mission,
                    getString(map, "name"),
                    TechniqueType.fromString(getString(map, "type")),
                    getString(map, "owner"),
                    getInt(map, "damage")
            );
        }
    }

    private void parseEnemyActivity(Mission mission, Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "enemyActivity");
        if (map == null) return;
        EnemyActivity enemy = factory.createEnemyActivity();
        enemy.setBehaviorType(getString(map, "behaviorType"));
        enemy.setTargetPriority(getString(map, "targetPriority"));
        enemy.setMobility(Mobility.fromString(getString(map, "mobility")));
        enemy.setEscalationRisk(EscalationRisk.fromString(getString(map, "escalationRisk")));
        parseStringList(map, "attackPatterns", enemy::addAttackPattern);
        parseStringList(map, "countermeasuresUsed", enemy::addCountermeasure);
        mission.setEnemyActivity(enemy);
    }

    private void parseEconomicAssessment(Mission mission, Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "economicAssessment");
        if (map == null) return;

        EconomicAssessment ea = factory.createEconomicAssessment();
        ea.setTotalDamageCost(getInt(map, "totalDamageCost"));
        ea.setInfrastructureDamage(getInt(map, "infrastructureDamage"));
        ea.setTransportDamage(getInt(map, "transportDamage"));
        ea.setCommercialDamage(getInt(map, "commercialDamage"));
        ea.setRecoveryEstimateDays(getInt(map, "recoveryEstimateDays"));
        ea.setInsuranceCovered(getBoolean(map, "insuranceCovered"));

        mission.setEconomicAssessment(ea);
    }

    private void parseCivilianImpact(Mission mission, Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "civilianImpact");
        if (map == null) return;

        CivilianImpact ci = factory.createCivilianImpact();
        ci.setEvacuated(getInt(map, "evacuated"));
        ci.setInjured(getInt(map, "injured"));
        ci.setMissing(getInt(map, "missing"));
        ci.setPublicExposureRisk(PublicExposureRisk.fromString(getString(map, "publicExposureRisk")));

        mission.setCivilianImpact(ci);
    }

    private void parseEnvironment(Mission mission, Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "environment");
        if (map == null) return;
        Environment env = factory.createEnvironment();
        env.setWeather(getString(map, "weather"));
        env.setTimeOfDay(getString(map, "timeOfDay"));
        env.setVisibility(Visibility.fromString(getString(map, "visibility")));
        env.setCursedEnergyDensity(getDouble(map, "cursedEnergyDensity"));

        mission.setEnvironment(env);
    }

    private void parseOperationTimeline(Mission mission, Map<String, Object> data) {
        List<Map<String, Object>> list = getListOfMaps(data, "operationTimeline");
        for (Map<String, Object> map : list) {
            factory.addOperationEvent(mission,
                    factory.createOperationEvent(
                            getString(map, "timestamp"),
                            getString(map, "type"),
                            getString(map, "description")
                    )
            );
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap(Map<String, Object> data, String key) {
        Object obj = data.get(key);
        return (obj instanceof Map) ? (Map<String, Object>) obj : null;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getListOfMaps(Map<String, Object> data, String key) {
        Object obj = data.get(key);
        if (!(obj instanceof List)) return Collections.emptyList();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object item : (List<?>) obj) {
            if (item instanceof Map) {
                result.add((Map<String, Object>) item);
            }
        }
        return result;
    }

    private void parseStringList(Map<String, Object> data, String key, java.util.function.Consumer<String> adder) {
        Object obj = data.get(key);
        if (obj instanceof List) {
            for (Object item : (List<?>) obj) {
                if (item != null) adder.accept(item.toString());
            }
        }
    }

    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }

    private int getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).intValue();
        if (val instanceof String) {
            try {
                return Integer.parseInt((String) val);
            } catch (NumberFormatException e) {
                try {
                    return (int) Double.parseDouble((String) val);
                } catch (NumberFormatException ignored) {}
            }
        }
        return 0;
    }

    private double getDouble(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).doubleValue();
        if (val instanceof String) {
            try {
                return Double.parseDouble((String) val);
            } catch (NumberFormatException ignored) {}
        }
        return 0.0;
    }

    private boolean getBoolean(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val instanceof Boolean && (Boolean) val;
    }
}