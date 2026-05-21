package defaultPackage.Parsers;

import defaultPackage.Missions_data.*;
import defaultPackage.Missions_data.Enums.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TXTParser implements CommonParser {

    private final MissionFactory factory;

    public TXTParser() {
        this.factory = new BaseMissionFactory();
    }

    @Override
    public boolean supportType(String filepath) {
        if (filepath == null || !filepath.toLowerCase().endsWith(".txt")) return false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                return line.equals("[MISSION]") || line.startsWith("missionId:");
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    @Override
    public Pair<MissionEntity, String> parse(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String firstLine = findFirstNonEmptyLine(reader);
            if (firstLine == null) return new Pair<>(null, "Файл пуст");
            if (firstLine.startsWith("[MISSION]")) return parseSectionFormat(filepath);
            if (firstLine.startsWith("missionId:")) return parseFlatFormat(filepath);
            return new Pair<>(null, "Неизвестный формат TXT файла");
        } catch (IOException e) {
            return new Pair<>(null, "Ошибка чтения: " + e.getMessage());
        }
    }

    private String findFirstNonEmptyLine(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) return line;
        }
        return null;
    }

    private Pair<MissionEntity, String> parseFlatFormat(String filepath) {
        MissionEntity mission = factory.createMission();
        Map<Integer, SorcererEntity> sorcererMap = new TreeMap<>();
        Map<Integer, TechniqueEntity> techniqueMap = new TreeMap<>();
        Map<Integer, OperationEventEntity> timelineMap = new TreeMap<>();
        CurseEntity curse = null;
        EconomicAssessmentEntity economic = null;
        EnvironmentEntity environment = null;
        EnemyActivityEntity enemy = null;
        CivilianImpactEntity civilian = null;
        List<String> operationTags = new ArrayList<>();
        List<String> supportUnits = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        List<String> artifacts = new ArrayList<>();
        List<String> evacuationZones = new ArrayList<>();
        List<String> statusEffects = new ArrayList<>();
        List<String> attackPatterns = new ArrayList<>();
        List<String> countermeasures = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                int colonIdx = line.indexOf(':');
                if (colonIdx == -1) continue;
                String tag = line.substring(0, colonIdx).trim();
                String value = line.substring(colonIdx + 1).trim();
                if (value.endsWith(",")) value = value.substring(0, value.length() - 1).trim();

                if (parseBasicField(mission, tag, value)) continue;
                if (tag.startsWith("curse.")) {
                    if (curse == null) curse = factory.createCurse(null, null);
                    parseCurseField(curse, tag, value);
                    continue;
                }
                if (tag.startsWith("economicAssessment.")) {
                    if (economic == null) economic = factory.createEconomicAssessment();
                    parseEconomicField(economic, tag, value);
                    continue;
                }
                if (tag.startsWith("environment.")) {
                    if (environment == null) environment = factory.createEnvironment();
                    parseEnvironmentField(environment, tag, value);
                    continue;
                }
                if (tag.startsWith("enemyActivity.")) {
                    if (enemy == null) enemy = factory.createEnemyActivity();
                    parseEnemyField(enemy, tag, value);
                    continue;
                }
                if (tag.startsWith("civilianImpact.")) {
                    if (civilian == null) civilian = factory.createCivilianImpact();
                    parseCivilianField(civilian, tag, value);
                    continue;
                }
                if (parseIndexedSorcerer(tag, value, sorcererMap, mission)) continue;
                if (parseIndexedTechnique(tag, value, techniqueMap, mission)) continue;
                if (parseIndexedTimeline(tag, value, timelineMap, mission)) continue;
                if (parseIndexedList(tag, value, "operationTags", operationTags)) continue;
                if (parseIndexedList(tag, value, "supportUnits", supportUnits)) continue;
                if (parseIndexedList(tag, value, "recommendations", recommendations)) continue;
                if (parseIndexedList(tag, value, "artifactsRecovered", artifacts)) continue;
                if (parseIndexedList(tag, value, "evacuationZones", evacuationZones)) continue;
                if (parseIndexedList(tag, value, "statusEffects", statusEffects)) continue;
                if (parseIndexedList(tag, value, "attackPatterns", attackPatterns)) continue;
                if (parseIndexedList(tag, value, "enemyActivity.attackPattern", attackPatterns)) continue;
                if (parseIndexedList(tag, value, "countermeasuresUsed", countermeasures)) continue;
                if (parseIndexedList(tag, value, "enemyActivity.countermeasuresUsed", countermeasures)) continue;
                if (tag.equals("notes")) mission.setNotes(value);
            }
        } catch (IOException e) {
            return new Pair<>(null, "Ошибка чтения: " + e.getMessage());
        }
        if (curse != null) mission.setCurse(curse);
        if (economic != null) mission.setEconomicAssessment(economic);
        if (environment != null) mission.setEnvironment(environment);
        if (civilian != null) mission.setCivilianImpact(civilian);
        if (enemy != null) {
            if (!attackPatterns.isEmpty()) enemy.setAttackPatterns(attackPatterns);
            if (!countermeasures.isEmpty()) enemy.setCountermeasuresUsed(countermeasures);
            mission.setEnemyActivity(enemy);
        }
        mission.setSorcerers(new ArrayList<>(sorcererMap.values()));
        mission.setTechniques(new ArrayList<>(techniqueMap.values()));
        mission.setOperationTimeline(new ArrayList<>(timelineMap.values()));
        mission.setOperationTags(operationTags);
        mission.setSupportUnits(supportUnits);
        mission.setRecommendations(recommendations);
        mission.setArtifactsRecovered(artifacts);
        mission.setEvacuationZones(evacuationZones);
        mission.setStatusEffects(statusEffects);
        return new Pair<>(mission, "");
    }

    private Pair<MissionEntity, String> parseSectionFormat(String filepath) {
        MissionEntity mission = factory.createMission();
        CurseEntity curse = null;
        EconomicAssessmentEntity economic = null;
        EnvironmentEntity environment = null;
        EnemyActivityEntity enemy = null;
        CivilianImpactEntity civilian = null;
        List<SorcererEntity> sorcerers = new ArrayList<>();
        List<TechniqueEntity> techniques = new ArrayList<>();
        List<OperationEventEntity> timeline = new ArrayList<>();
        String currentSection = "";
        SorcererEntity currentSorcerer = null;
        TechniqueEntity currentTechnique = null;
        OperationEventEntity currentEvent = null;
        List<String> currentStringList = null;
        String currentListType = "";
        StringBuilder notes = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("[") && line.endsWith("]")) {
                    if (currentSorcerer != null) { currentSorcerer.setMission(mission); sorcerers.add(currentSorcerer); }
                    if (currentTechnique != null) { currentTechnique.setMission(mission); techniques.add(currentTechnique); }
                    if (currentEvent != null) { currentEvent.setMission(mission); timeline.add(currentEvent); }
                    if (currentStringList != null) saveStringList(mission, currentListType, currentStringList);
                    currentSection = line;
                    currentSorcerer = null;
                    currentTechnique = null;
                    currentEvent = null;
                    currentStringList = null;
                    switch (line) {
                        case "[SORCERER]": currentSorcerer = new SorcererEntity(); break;
                        case "[TECHNIQUE]": currentTechnique = new TechniqueEntity(); break;
                        case "[OPERATIONTIMELINE]": currentEvent = new OperationEventEntity(); break;
                        case "[OPERATIONTAGS]": currentStringList = new ArrayList<>(); currentListType = "operationTags"; break;
                        case "[SUPPORTUNITS]": currentStringList = new ArrayList<>(); currentListType = "supportUnits"; break;
                        case "[RECOMMENDATIONS]": currentStringList = new ArrayList<>(); currentListType = "recommendations"; break;
                        case "[ARTIFACTSRECOVERED]": currentStringList = new ArrayList<>(); currentListType = "artifactsRecovered"; break;
                        case "[EVACUATIONZONES]": currentStringList = new ArrayList<>(); currentListType = "evacuationZones"; break;
                        case "[STATUSEFFECTS]": currentStringList = new ArrayList<>(); currentListType = "statusEffects"; break;
                    }
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();
                if (currentSection.equals("") || currentSection.equals("[MISSION]")) {
                    parseBasicField(mission, key, value);
                } else if (currentSection.equals("[CURSE]")) {
                    if (curse == null) curse = factory.createCurse(null, null);
                    parseCurseField(curse, key, value);
                } else if (currentSection.equals("[ECONOMICASSESSMENT]")) {
                    if (economic == null) economic = factory.createEconomicAssessment();
                    parseEconomicField(economic, key, value);
                } else if (currentSection.equals("[ENVIRONMENT]")) {
                    if (environment == null) environment = factory.createEnvironment();
                    parseEnvironmentField(environment, key, value);
                } else if (currentSection.equals("[ENEMYACTIVITY]")) {
                    if (enemy == null) enemy = factory.createEnemyActivity();
                    if (!parseEnemyField(enemy, key, value)) {
                        if (key.equals("attackPatterns")) {
                            for (String p : parseCommaSeparated(value)) enemy.getAttackPatterns().add(p);
                        } else if (key.equals("countermeasuresUsed")) {
                            for (String m : parseCommaSeparated(value)) enemy.getCountermeasuresUsed().add(m);
                        }
                    }
                } else if (currentSection.equals("[CIVILIANIMPACT]")) {
                    if (civilian == null) civilian = factory.createCivilianImpact();
                    parseCivilianField(civilian, key, value);
                } else if (currentSection.equals("[SORCERER]") && currentSorcerer != null) {
                    parseSorcererField(currentSorcerer, key, value);
                } else if (currentSection.equals("[TECHNIQUE]") && currentTechnique != null) {
                    parseTechniqueField(currentTechnique, key, value);
                } else if (currentSection.equals("[OPERATIONTIMELINE]") && currentEvent != null) {
                    parseTimelineField(currentEvent, key, value);
                } else if (currentSection.equals("[NOTES]")) {
                    if (notes.length() > 0) notes.append("\n");
                    notes.append(value);
                } else if (currentStringList != null) {
                    if (key.equals("tag") || key.equals("unit") || key.equals("recommendation") ||
                            key.equals("artifact") || key.equals("zone") || key.equals("effect")) {
                        currentStringList.add(value);
                    }
                }
            }
            if (currentSorcerer != null) { currentSorcerer.setMission(mission); sorcerers.add(currentSorcerer); }
            if (currentTechnique != null) { currentTechnique.setMission(mission); techniques.add(currentTechnique); }
            if (currentEvent != null) { currentEvent.setMission(mission); timeline.add(currentEvent); }
            if (currentStringList != null) saveStringList(mission, currentListType, currentStringList);
            if (notes.length() > 0) mission.setNotes(notes.toString());
        } catch (IOException e) {
            return new Pair<>(null, "Ошибка чтения: " + e.getMessage());
        }

        if (curse != null) mission.setCurse(curse);
        if (economic != null) mission.setEconomicAssessment(economic);
        if (environment != null) mission.setEnvironment(environment);
        if (enemy != null) mission.setEnemyActivity(enemy);
        if (civilian != null) mission.setCivilianImpact(civilian);
        mission.setSorcerers(sorcerers);
        mission.setTechniques(techniques);
        mission.setOperationTimeline(timeline);
        return new Pair<>(mission, "");
    }

    private boolean parseBasicField(MissionEntity m, String tag, String value) {
        switch (tag) {
            case "missionId": m.setMissionId(value); return true;
            case "date": m.setDate(value); return true;
            case "location": m.setLocation(value); return true;
            case "outcome": m.setOutcome(Outcome.fromString(value)); return true;
            case "damageCost": m.setDamageCost(parseIntSafe(value)); return true;
            case "comment": m.setComment(value); return true;
            default: return false;
        }
    }

    private boolean parseCurseField(CurseEntity c, String tag, String value) {
        String field = tag.substring(tag.lastIndexOf('.') + 1);
        switch (field) {
            case "name": c.setName(value); return true;
            case "threatLevel": c.setThreatLevel(ThreatLevel.fromString(value)); return true;
            default: return false;
        }
    }

    private boolean parseEconomicField(EconomicAssessmentEntity e, String tag, String value) {
        String field = tag.substring(tag.lastIndexOf('.') + 1);
        switch (field) {
            case "totalDamageCost": e.setTotalDamageCost(parseIntSafe(value)); return true;
            case "infrastructureDamage": e.setInfrastructureDamage(parseIntSafe(value)); return true;
            case "transportDamage": e.setTransportDamage(parseIntSafe(value)); return true;
            case "commercialDamage": e.setCommercialDamage(parseIntSafe(value)); return true;
            case "recoveryEstimateDays": e.setRecoveryEstimateDays(parseIntSafe(value)); return true;
            case "insuranceCovered": e.setInsuranceCovered(Boolean.parseBoolean(value)); return true;
            default: return false;
        }
    }

    private boolean parseEnvironmentField(EnvironmentEntity e, String tag, String value) {
        String field = tag.substring(tag.lastIndexOf('.') + 1);
        switch (field) {
            case "weather": e.setWeather(value); return true;
            case "timeOfDay": e.setTimeOfDay(value); return true;
            case "visibility": e.setVisibility(Visibility.fromString(value)); return true;
            case "cursedEnergyDensity": e.setCursedEnergyDensity(parseDoubleSafe(value)); return true;
            default: return false;
        }
    }

    private boolean parseEnemyField(EnemyActivityEntity e, String tag, String value) {
        String field = tag.substring(tag.lastIndexOf('.') + 1);
        switch (field) {
            case "behaviorType": e.setBehaviorType(value); return true;
            case "targetPriority": e.setTargetPriority(value); return true;
            case "mobility": e.setMobility(Mobility.fromString(value)); return true;
            case "escalationRisk": e.setEscalationRisk(EscalationRisk.fromString(value)); return true;
            default: return false;
        }
    }

    private boolean parseCivilianField(CivilianImpactEntity c, String tag, String value) {
        String field = tag.substring(tag.lastIndexOf('.') + 1);
        switch (field) {
            case "evacuated": c.setEvacuated(parseIntSafe(value)); return true;
            case "injured": c.setInjured(parseIntSafe(value)); return true;
            case "missing": c.setMissing(parseIntSafe(value)); return true;
            case "publicExposureRisk": c.setPublicExposureRisk(PublicExposureRisk.fromString(value)); return true;
            default: return false;
        }
    }

    private void parseSorcererField(SorcererEntity s, String key, String value) {
        switch (key) {
            case "name": s.setName(value); break;
            case "rank": s.setRank(Rank.fromString(value)); break;
        }
    }

    private void parseTechniqueField(TechniqueEntity t, String key, String value) {
        switch (key) {
            case "name": t.setName(value); break;
            case "type": t.setType(TechniqueType.fromString(value)); break;
            case "owner": t.setOwner(value); break;
            case "damage": t.setDamage(parseIntSafe(value)); break;
        }
    }

    private void parseTimelineField(OperationEventEntity e, String key, String value) {
        switch (key) {
            case "timestamp": e.setTimestamp(value); break;
            case "type": e.setType(value); break;
            case "description": e.setDescription(value); break;
        }
    }

    private boolean parseIndexedSorcerer(String tag, String value, Map<Integer, SorcererEntity> map, MissionEntity mission) {
        int[] idx = new int[1];
        String field = extractIndexedField(tag, "sorcerer[", "]", idx);
        if (field == null) return false;
        SorcererEntity s = map.computeIfAbsent(idx[0], k -> factory.createSorcerer(null, null, mission));
        switch (field) {
            case "name": s.setName(value); return true;
            case "rank": s.setRank(Rank.fromString(value)); return true;
            default: return false;
        }
    }

    private boolean parseIndexedTechnique(String tag, String value, Map<Integer, TechniqueEntity> map, MissionEntity mission) {
        int[] idx = new int[1];
        String field = extractIndexedField(tag, "technique[", "]", idx);
        if (field == null) return false;
        TechniqueEntity t = map.computeIfAbsent(idx[0], k -> factory.createTechnique(null, null, null, 0, mission));
        switch (field) {
            case "name": t.setName(value); return true;
            case "type": t.setType(TechniqueType.fromString(value)); return true;
            case "owner": t.setOwner(value); return true;
            case "damage": t.setDamage(parseIntSafe(value)); return true;
            default: return false;
        }
    }

    private boolean parseIndexedTimeline(String tag, String value, Map<Integer, OperationEventEntity> map, MissionEntity mission) {
        int[] idx = new int[1];
        String field = extractIndexedField(tag, "operationTimeline[", "]", idx);
        if (field == null) return false;
        OperationEventEntity e = map.computeIfAbsent(idx[0], k -> factory.createOperationEvent(null, null, null, mission));
        switch (field) {
            case "timestamp": e.setTimestamp(value); return true;
            case "type": e.setType(value); return true;
            case "description": e.setDescription(value); return true;
            default: return false;
        }
    }

    private boolean parseIndexedList(String tag, String value, String prefix, List<String> list) {
        if (tag.startsWith(prefix + "[")) { list.add(value); return true; }
        return false;
    }

    private String extractIndexedField(String tag, String prefix, String suffix, int[] outIndex) {
        int start = tag.indexOf(prefix);
        if (start == -1) return null;
        int end = tag.indexOf(suffix, start + prefix.length());
        if (end == -1) return null;
        try { outIndex[0] = Integer.parseInt(tag.substring(start + prefix.length(), end)); }
        catch (NumberFormatException e) { return null; }
        if (end + 1 >= tag.length()) return null;
        String field = tag.substring(end + 1);
        if (field.startsWith(".")) field = field.substring(1);
        return field;
    }

    private void saveStringList(MissionEntity mission, String type, List<String> list) {
        switch (type) {
            case "operationTags": mission.setOperationTags(list); break;
            case "supportUnits": mission.setSupportUnits(list); break;
            case "recommendations": mission.setRecommendations(list); break;
            case "artifactsRecovered": mission.setArtifactsRecovered(list); break;
            case "evacuationZones": mission.setEvacuationZones(list); break;
            case "statusEffects": mission.setStatusEffects(list); break;
        }
    }

    private List<String> parseCommaSeparated(String value) {
        List<String> result = new ArrayList<>();
        for (String part : value.split(",")) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) result.add(trimmed);
        }
        return result;
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