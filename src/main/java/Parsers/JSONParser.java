package Parsers;


import Missions_data.Mission;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONParser implements CommonParser {

    @Override
    public boolean supportType(String filepath) {
        return filepath != null && (filepath.endsWith(".json"));
    }

    @Override
    public Pair<Mission, String> parse(String filepath) {
        ObjectMapper mapper = new ObjectMapper();
        Mission mission = null;
        try {
            mission = mapper.readValue(new File(filepath), Mission.class);
        } catch (IOException e) {
            return new Pair<>(null, "Структура файла не соответсвует шаблону файлов миссий типа json");
        }
        return new Pair<>(mission, "");
    }
}