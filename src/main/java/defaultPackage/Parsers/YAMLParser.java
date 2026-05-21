package defaultPackage.Parsers;

import defaultPackage.Missions_data.*;
import defaultPackage.Missions_data.Enums.*;
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
    public Pair<MissionEntity, String> parse(String filepath) {
        try (InputStream inputStream = new FileInputStream(filepath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            if (data == null) {
                return new Pair<>(null, "Файл пуст или имеет неверный формат");
            }

            MissionEntity mission = factory.createMission();
            parseAllFields(mission, data);
            return new Pair<>(mission, "");

        } catch (FileNotFoundException e) {
            return new Pair<>(null, "Файл не найден: " + filepath);
        } catch (Exception e) {
            return new Pair<>(null, "Структура файла не соответствует шаблону YAML");
        }
    }

    private void parseAllFields(MissionEntity mission, Map<String, Object> data) {
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
        mission.setCurse(parseCurse(data));
        parseSorcerers(mission, data);
        parseTechniques(mission, data);
        mission.setEnemyActivity(parseEnemyActivity(data));
        mission.setEconomicAssessment(parseEconomicAssessment(data));
        mission.setCivilianImpact(parseCivilianImpact(data));
        mission.setEnvironment(parseEnvironment(data));
        parseOperationTimeline(mission, data);
        mission.setOperationTags(parseStringList(data, "operationTags"));
        mission.setSupportUnits(parseStringList(data, "supportUnits"));
        mission.setRecommendations(parseStringList(data, "recommendations"));
        mission.setArtifactsRecovered(parseStringList(data, "artifactsRecovered"));
        mission.setEvacuationZones(parseStringList(data, "evacuationZones"));
        mission.setStatusEffects(parseStringList(data, "statusEffects"));
    }

    private CurseEntity parseCurse(Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "curse");
        if (map == null) return null;
        return factory.createCurse(
                getString(map, "name"),
                ThreatLevel.fromString(getString(map, "threatLevel"))
        );
    }

    private void parseSorcerers(MissionEntity mission, Map<String, Object> data) {
        List<Map<String, Object>> list = getListOfMaps(data, "sorcerers");
        for (Map<String, Object> map : list) {
            mission.getSorcerers().add(factory.createSorcerer(
                    getString(map, "name"),
                    Rank.fromString(getString(map, "rank")),
                    mission
            ));
        }
    }

    private void parseTechniques(MissionEntity mission, Map<String, Object> data) {
        List<Map<String, Object>> list = getListOfMaps(data, "techniques");
        for (Map<String, Object> map : list) {
            mission.getTechniques().add(factory.createTechnique(
                    getString(map, "name"),
                    TechniqueType.fromString(getString(map, "type")),
                    getString(map, "owner"),
                    getInt(map, "damage"),
                    mission
            ));
        }
    }

    private EnemyActivityEntity parseEnemyActivity(Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "enemyActivity");
        if (map == null) return null;
        EnemyActivityEntity enemy = factory.createEnemyActivity();
        enemy.setBehaviorType(getString(map, "behaviorType"));
        enemy.setTargetPriority(getString(map, "targetPriority"));
        enemy.setMobility(Mobility.fromString(getString(map, "mobility")));
        enemy.setEscalationRisk(EscalationRisk.fromString(getString(map, "escalationRisk")));
        enemy.setAttackPatterns(parseStringList(map, "attackPatterns"));
        enemy.setCountermeasuresUsed(parseStringList(map, "countermeasuresUsed"));
        return enemy;
    }

    private EconomicAssessmentEntity parseEconomicAssessment(Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "economicAssessment");
        if (map == null) return null;
        EconomicAssessmentEntity ea = factory.createEconomicAssessment();
        ea.setTotalDamageCost(getInt(map, "totalDamageCost"));
        ea.setInfrastructureDamage(getInt(map, "infrastructureDamage"));
        ea.setTransportDamage(getInt(map, "transportDamage"));
        ea.setCommercialDamage(getInt(map, "commercialDamage"));
        ea.setRecoveryEstimateDays(getInt(map, "recoveryEstimateDays"));
        ea.setInsuranceCovered(getBoolean(map, "insuranceCovered"));
        return ea;
    }

    private CivilianImpactEntity parseCivilianImpact(Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "civilianImpact");
        if (map == null) return null;
        CivilianImpactEntity ci = factory.createCivilianImpact();
        ci.setEvacuated(getInt(map, "evacuated"));
        ci.setInjured(getInt(map, "injured"));
        ci.setMissing(getInt(map, "missing"));
        ci.setPublicExposureRisk(PublicExposureRisk.fromString(getString(map, "publicExposureRisk")));
        return ci;
    }

    private EnvironmentEntity parseEnvironment(Map<String, Object> data) {
        Map<String, Object> map = getMap(data, "environment");
        if (map == null) return null;
        EnvironmentEntity env = factory.createEnvironment();
        env.setWeather(getString(map, "weather"));
        env.setTimeOfDay(getString(map, "timeOfDay"));
        env.setVisibility(Visibility.fromString(getString(map, "visibility")));
        env.setCursedEnergyDensity(getDouble(map, "cursedEnergyDensity"));
        return env;
    }

    private void parseOperationTimeline(MissionEntity mission, Map<String, Object> data) {
        List<Map<String, Object>> list = getListOfMaps(data, "operationTimeline");
        for (Map<String, Object> map : list) {
            mission.getOperationTimeline().add(factory.createOperationEvent(
                    getString(map, "timestamp"),
                    getString(map, "type"),
                    getString(map, "description"),
                    mission
            ));
        }
    }

    private List<String> parseStringList(Map<String, Object> data, String key) {
        List<String> result = new ArrayList<>();
        Object obj = data.get(key);
        if (obj instanceof List) {
            for (Object item : (List<?>) obj) {
                if (item != null) result.add(item.toString());
            }
        }
        return result;
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
            if (item instanceof Map) result.add((Map<String, Object>) item);
        }
        return result;
    }

    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }

    private int getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).intValue();
        if (val instanceof String) {
            try { return Integer.parseInt((String) val); }
            catch (NumberFormatException e) {
                try { return (int) Double.parseDouble((String) val); }
                catch (NumberFormatException ignored) {}
            }
        }
        return 0;
    }

    private double getDouble(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).doubleValue();
        if (val instanceof String) {
            try { return Double.parseDouble((String) val); }
            catch (NumberFormatException ignored) {}
        }
        return 0.0;
    }

    private boolean getBoolean(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val instanceof Boolean && (Boolean) val;
    }
}