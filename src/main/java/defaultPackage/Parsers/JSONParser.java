package defaultPackage.Parsers;


import defaultPackage.Missions_data.MissionEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import defaultPackage.Missions_data.OperationEventEntity;
import defaultPackage.Missions_data.SorcererEntity;
import defaultPackage.Missions_data.TechniqueEntity;

import java.io.File;
import java.io.IOException;

public class JSONParser implements CommonParser {

    @Override
    public boolean supportType(String filepath) {
        return filepath != null && filepath.toLowerCase().endsWith(".json");
    }


    @Override
    public Pair<MissionEntity, String> parse(String filepath) {
        ObjectMapper mapper = new ObjectMapper();
        MissionEntity mission = null;
        try {
            mission = mapper.readValue(new File(filepath), MissionEntity.class);
            if (mission.getSorcerers() != null) {
                for (SorcererEntity s : mission.getSorcerers()) {
                    s.setMission(mission);
                }
            }
            if (mission.getTechniques() != null) {
                for (TechniqueEntity t : mission.getTechniques()) {
                    t.setMission(mission);
                }
            }
            if (mission.getOperationTimeline() != null) {
                for (OperationEventEntity e : mission.getOperationTimeline()) {
                    e.setMission(mission);
                }
            }
        } catch (IOException e) {
            return new Pair<>(null, "Структура файла не соответсвует шаблону файлов миссий типа json");
        }
        return new Pair<>(mission, "");
    }
}