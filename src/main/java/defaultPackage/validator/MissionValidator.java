package defaultPackage.validator;

import defaultPackage.Missions_data.*;
import defaultPackage.Missions_data.Enums.Outcome;

import java.util.ArrayList;
import java.util.List;

public class MissionValidator {

    public List<String> validateWithWarnings(MissionEntity mission) {
        List<String> warnings = new ArrayList<>();
        if (isEmpty(mission.getMissionId())) {
            warnings.add("! missionId не заполнен");
        }
        if (isEmpty(mission.getDate())) {
            warnings.add("! date не заполнен");
        }
        if (isEmpty(mission.getLocation())) {
            warnings.add("! location не заполнен");
        }
        if (mission.getOutcome() == null || mission.getOutcome() == Outcome.UNKNOWN) {
            warnings.add("! outcome не указан или UNKNOWN");
        }
        if (mission.getDamageCost() < 0) {
            warnings.add("! damageCost отрицательный");
        }
        if (mission.getCurse() == null) {
            warnings.add("! curse отсутствует");
        } else {
            if (isEmpty(mission.getCurse().getName())) {
                warnings.add("! curse.name не заполнен");
            }
            if (mission.getCurse().getThreatLevel() == null) {
                warnings.add("! curse.threatLevel не указан");
            }
        }
        if (mission.getSorcerers() != null) {
            for (int i = 0; i < mission.getSorcerers().size(); i++) {
                SorcererEntity s = mission.getSorcerers().get(i);
                if (isEmpty(s.getName())) {
                    warnings.add("! sorcerers[" + i + "].name не заполнен");
                }
                if (s.getRank() == null) {
                    warnings.add("! sorcerers[" + i + "].rank не указан");
                }
            }
        }
        if (mission.getTechniques() != null) {
            for (int i = 0; i < mission.getTechniques().size(); i++) {
                TechniqueEntity t = mission.getTechniques().get(i);
                if (isEmpty(t.getName())) {
                    warnings.add("! techniques[" + i + "].name не заполнен");
                }
                if (t.getType() == null) {
                    warnings.add("! techniques[" + i + "].type не указан");
                }
                if (isEmpty(t.getOwner())) {
                    warnings.add("! techniques[" + i + "].owner не заполнен");
                }
                if (t.getDamage() < 0) {
                    warnings.add("! techniques[" + i + "].damage отрицательный");
                }
            }
        }
        if (hasTechniques(mission) && !hasSorcerers(mission)) {
            warnings.add("! Есть техники, но нет магов-владельцев");
        }
        if (mission.getEconomicAssessment() != null) {
            EconomicAssessmentEntity ea = mission.getEconomicAssessment();
            if (ea.getTotalDamageCost() < 0) {
                warnings.add("! economicAssessment.totalDamageCost отрицательный");
            }
            if (ea.getRecoveryEstimateDays() < 0) {
                warnings.add("! economicAssessment.recoveryEstimateDays отрицательный");
            }
        }
        if (mission.getEnemyActivity() != null) {
            EnemyActivityEntity ea = mission.getEnemyActivity();
            if (isEmpty(ea.getBehaviorType())) {
                warnings.add("! enemyActivity.behaviorType не заполнен");
            }
            if (ea.getMobility() == null) {
                warnings.add("! enemyActivity.mobility не указан");
            }
            if (ea.getEscalationRisk() == null) {
                warnings.add("! enemyActivity.escalationRisk не указан");
            }
        }
        if (mission.getEnvironment() != null) {
            EnvironmentEntity env = mission.getEnvironment();
            if (isEmpty(env.getWeather())) {
                warnings.add("! environment.weather не заполнен");
            }
            if (env.getVisibility() == null) {
                warnings.add("! environment.visibility не указан");
            }
            if (env.getCursedEnergyDensity() < 0) {
                warnings.add("! environment.cursedEnergyDensity отрицательный");
            }
        }
        if (mission.getCivilianImpact() != null) {
            CivilianImpactEntity ci = mission.getCivilianImpact();
            if (ci.getEvacuated() < 0) {
                warnings.add("! civilianImpact.evacuated отрицательный");
            }
            if (ci.getInjured() < 0) {
                warnings.add("! civilianImpact.injured отрицательный");
            }
            if (ci.getMissing() < 0) {
                warnings.add("! civilianImpact.missing отрицательный");
            }
            if (ci.getPublicExposureRisk() == null) {
                warnings.add("! civilianImpact.publicExposureRisk не указан");
            }
        }
        if (mission.getOperationTimeline() != null) {
            for (int i = 0; i < mission.getOperationTimeline().size(); i++) {
                OperationEventEntity e = mission.getOperationTimeline().get(i);
                if (isEmpty(e.getTimestamp())) {
                    warnings.add("! operationTimeline[" + i + "].timestamp не заполнен");
                }
                if (isEmpty(e.getType())) {
                    warnings.add("! operationTimeline[" + i + "].type не заполнен");
                }
                if (isEmpty(e.getDescription())) {
                    warnings.add("! operationTimeline[" + i + "].description не заполнен");
                }
            }
        }

        return warnings;
    }

    private boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    private boolean hasTechniques(MissionEntity mission) {
        return mission.getTechniques() != null && !mission.getTechniques().isEmpty();
    }

    private boolean hasSorcerers(MissionEntity mission) {
        return mission.getSorcerers() != null && !mission.getSorcerers().isEmpty();
    }
}