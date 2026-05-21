package defaultPackage.validator;

import defaultPackage.Missions_data.MissionEntity;
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
                if (isEmpty(mission.getSorcerers().get(i).getName())) {
                    warnings.add("! sorcerers[" + i + "].name не заполнен");
                }
                if (mission.getSorcerers().get(i).getRank() == null) {
                    warnings.add("! sorcerers[" + i + "].rank не указан");
                }
            }
        }

        if (mission.getTechniques() != null) {
            for (int i = 0; i < mission.getTechniques().size(); i++) {
                if (isEmpty(mission.getTechniques().get(i).getName())) {
                    warnings.add("! techniques[" + i + "].name не заполнен");
                }
                if (mission.getTechniques().get(i).getType() == null) {
                    warnings.add("! techniques[" + i + "].type не указан");
                }
                if (isEmpty(mission.getTechniques().get(i).getOwner())) {
                    warnings.add("! techniques[" + i + "].owner не заполнен");
                }
            }
        }

        if (hasTechniques(mission) && !hasSorcerers(mission)) {
            warnings.add("! Есть техники, но нет магов-владельцев");
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