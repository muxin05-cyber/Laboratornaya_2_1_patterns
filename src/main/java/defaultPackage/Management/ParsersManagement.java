package defaultPackage.Management;

import defaultPackage.Missions_data.MissionEntity;
import defaultPackage.Parsers.*;
import java.util.Arrays;
import java.util.List;

public class ParsersManagement {
    private final List<CommonParser> parsers;

    public ParsersManagement() {
        this.parsers = getAvailableParsers();
    }

    private List<CommonParser> getAvailableParsers() {
        return Arrays.asList(
                new XMLParser(),
                new JSONParser(),
                new TXTParser(),
                new YAMLParser(),
                new BinaryParser()
        );
    }

    public Pair<MissionEntity, String> FileProcessing(String filepath) {
        for (CommonParser parser : parsers) {
            if (parser.supportType(filepath)) {
                Pair<MissionEntity, String> result = parser.parse(filepath);
                MissionEntity mission = result.getFirst();
                String error = result.getSecond();
                if (mission != null) {
                    return new Pair<>(mission, null);
                } else {
                    return new Pair<>(null, error);
                }
            }
        }
        return new Pair<>(null, "Не найден подходящий парсер для файла: " + filepath);
    }
}