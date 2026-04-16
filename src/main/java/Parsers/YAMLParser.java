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

            parseBasicFields(mission, data);
            parseCurse(mission, data);
            parseSorcerers(mission, data);
            parseTechniques(mission, data);
            parseEnemyActivity(mission, data);
            parseEconomicAssessment(mission, data);
            parseCivilianImpact(mission, data);
            parseEnvironment(mission, data);
            parseOperationTimeline(mission, data);
            parseStringLists(mission, data);

            mission.setNotes(getString(data, "notes"));

            return new Pair<>(mission, "");

        } catch (FileNotFoundException e) {
            return new Pair<>(null, "Файл не найден: " + filepath);
        } catch (Exception e) {
            e.printStackTrace();
            return new Pair<>(null, "Структура файла не соответствует шаблону файлов миссий типа yaml");
        }
    }

    private void parseBasicFields(Mission mission, Map<String, Object> data) {
        mission.setMissionId(getString(data, "missionId"));
        mission.setLocation(getString(data, "location"));
        mission.setComment(getString(data, "comment"));

        Object dateObj = data.get("date");
        if (dateObj instanceof String) {
            mission.setDate((String) dateObj);
        } else if (dateObj instanceof Date) {
            mission.setDate(new SimpleDateFormat("yyyy-MM-dd").format((Date) dateObj));
        }

        String outcomeStr = getString(data, "outcome");
        if (outcomeStr != null) mission.setOutcome(Outcome.fromString(outcomeStr));

        Object damageObj = data.get("damageCost");
        if (damageObj instanceof Number) {
            mission.setDamageCost(((Number) damageObj).intValue());
        }
    }

    private void parseCurse(Mission mission, Map<String, Object> data) {
        Object obj = data.get("curse");
        if (!(obj instanceof Map)) return;

        Map<String, Object> map = (Map<String, Object>) obj;
        String name = getString(map, "name");
        String threatStr = getString(map, "threatLevel");

        Curse curse = factory.createCurse(name, ThreatLevel.fromString(threatStr));
        mission.setCurse(curse);
    }

    private void parseSorcerers(Mission mission, Map<String, Object> data) {
        Object obj = data.get("sorcerers");
        if (!(obj instanceof List)) return;

        for (Object item : (List<?>) obj) {
            if (!(item instanceof Map)) continue;
            Map<String, Object> map = (Map<String, Object>) item;

            String name = getString(map, "name");
            String rankStr = getString(map, "rank");

            factory.addSorcerer(mission, name, Rank.fromString(rankStr));
        }
    }

    private void parseTechniques(Mission mission, Map<String, Object> data) {
        Object obj = data.get("techniques");
        if (!(obj instanceof List)) return;

        for (Object item : (List<?>) obj) {
            if (!(item instanceof Map)) continue;
            Map<String, Object> map = (Map<String, Object>) item;

            String name = getString(map, "name");
            String typeStr = getString(map, "type");
            String owner = getString(map, "owner");
            int damage = 0;

            Object dmg = map.get("damage");
            if (dmg instanceof Number) damage = ((Number) dmg).intValue();

            factory.addTechnique(mission, name, TechniqueType.fromString(typeStr), owner, damage);
        }
    }

    private void parseEnemyActivity(Mission mission, Map<String, Object> data) {
        Object obj = data.get("enemyActivity");
        if (!(obj instanceof Map)) return;

        Map<String, Object> map = (Map<String, Object>) obj;
        EnemyActivity enemy = factory.createEnemyActivity();

        enemy.setBehaviorType(getString(map, "behaviorType"));
        enemy.setTargetPriority(getString(map, "targetPriority"));
        enemy.setRetreatStrategy(getString(map, "retreatStrategy"));
        enemy.setCoordinationLevel(getString(map, "coordinationLevel"));

        String mobStr = getString(map, "mobility");
        if (mobStr != null) enemy.setMobility(Mobility.fromString(mobStr));

        String riskStr = getString(map, "escalationRisk");
        if (riskStr != null) enemy.setEscalationRisk(EscalationRisk.fromString(riskStr));

        // Добавляем паттерны атак
        Object patternsObj = map.get("attackPatterns");
        if (patternsObj instanceof List) {
            for (Object p : (List<?>) patternsObj) {
                if (p != null) factory.addAttackPattern(enemy, p.toString());
            }
        }

        Object measuresObj = map.get("countermeasuresUsed");
        if (measuresObj instanceof List) {
            for (Object m : (List<?>) measuresObj) {
                if (m != null) factory.addCountermeasure(enemy, m.toString());
            }
        }

        enemy.setAttackTypes(getStringList(map, "attackTypes"));
        enemy.setVulnerabilities(getStringList(map, "vulnerabilities"));

        mission.setEnemyActivity(enemy);
    }

    private void parseEconomicAssessment(Mission mission, Map<String, Object> data) {
        Object obj = data.get("economicAssessment");
        if (!(obj instanceof Map)) return;

        Map<String, Object> map = (Map<String, Object>) obj;
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
        Object obj = data.get("civilianImpact");
        if (!(obj instanceof Map)) return;

        Map<String, Object> map = (Map<String, Object>) obj;
        CivilianImpact ci = factory.createCivilianImpact();

        ci.setEvacuated(getInt(map, "evacuated"));
        ci.setInjured(getInt(map, "injured"));
        ci.setMissing(getInt(map, "missing"));

        String riskStr = getString(map, "publicExposureRisk");
        if (riskStr != null) ci.setPublicExposureRisk(PublicExposureRisk.fromString(riskStr));

        mission.setCivilianImpact(ci);
    }

    private void parseEnvironment(Mission mission, Map<String, Object> data) {
        Object obj = data.get("environment");
        if (!(obj instanceof Map)) return;

        Map<String, Object> map = (Map<String, Object>) obj;
        Environment env = factory.createEnvironment();

        env.setWeather(getString(map, "weather"));
        env.setTimeOfDay(getString(map, "timeOfDay"));

        String visStr = getString(map, "visibility");
        if (visStr != null) env.setVisibility(Visibility.fromString(visStr));

        Object densityObj = map.get("cursedEnergyDensity");
        if (densityObj instanceof Number) {
            env.setCursedEnergyDensity(((Number) densityObj).doubleValue());
        } else if (densityObj instanceof String) {
            try {
                env.setCursedEnergyDensity(Double.parseDouble((String) densityObj));
            } catch (NumberFormatException ignored) {}
        }

        mission.setEnvironment(env);
    }

    private void parseOperationTimeline(Mission mission, Map<String, Object> data) {
        Object obj = data.get("operationTimeline");
        if (!(obj instanceof List)) return;

        for (Object item : (List<?>) obj) {
            if (!(item instanceof Map)) continue;
            Map<String, Object> map = (Map<String, Object>) item;

            String timestamp = getString(map, "timestamp");
            String type = getString(map, "type");
            String description = getString(map, "description");

            OperationEvent event = factory.createOperationEvent(timestamp, type, description);
            factory.addOperationEvent(mission, event);
        }
    }

    private void parseStringLists(Mission mission, Map<String, Object> data) {
        for (String tag : getStringList(data, "operationTags")) {
            factory.addOperationTag(mission, tag);
        }
        for (String unit : getStringList(data, "supportUnits")) {
            factory.addSupportUnit(mission, unit);
        }
        for (String rec : getStringList(data, "recommendations")) {
            factory.addRecommendation(mission, rec);
        }
        for (String artifact : getStringList(data, "artifactsRecovered")) {
            factory.addArtifact(mission, artifact);
        }
        for (String zone : getStringList(data, "evacuationZones")) {
            factory.addEvacuationZone(mission, zone);
        }
        for (String effect : getStringList(data, "statusEffects")) {
            factory.addStatusEffect(mission, effect);
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

    private boolean getBoolean(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val instanceof Boolean && (Boolean) val;
    }

    private List<String> getStringList(Map<String, Object> map, String key) {
        List<String> result = new ArrayList<>();
        Object obj = map.get(key);
        if (obj instanceof List) {
            for (Object item : (List<?>) obj) {
                if (item != null) result.add(item.toString());
            }
        }
        return result;
    }
}