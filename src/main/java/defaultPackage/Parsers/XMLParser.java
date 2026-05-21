package defaultPackage.Parsers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import defaultPackage.Missions_data.*;

import java.io.File;

public class XMLParser implements CommonParser {

    private final XmlMapper xmlMapper;

    public XMLParser() {
        xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public boolean supportType(String filepath) {
        return filepath != null && filepath.toLowerCase().endsWith(".xml");
    }

    @Override
    public Pair<MissionEntity, String> parse(String filepath) {
        try {
            MissionEntity mission = xmlMapper.readValue(new File(filepath), MissionEntity.class);

            if (mission.getSorcerers() != null)
                mission.getSorcerers().forEach(s -> s.setMission(mission));
            if (mission.getTechniques() != null)
                mission.getTechniques().forEach(t -> t.setMission(mission));
            if (mission.getOperationTimeline() != null)
                mission.getOperationTimeline().forEach(e -> e.setMission(mission));

            return new Pair<>(mission, "");
        } catch (Exception e) {
            return new Pair<>(null, "Ошибка XML: " + e.getMessage());
        }
    }
}