

import Missions_data.Mission;
import Parsers.*;

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

    public Pair<Mission, String> FileProcessing(String filepath) {
        for (CommonParser parser : parsers) {
            if (parser.supportType(filepath)) {
                Pair<Mission, String> result = parser.parse(filepath);
                Mission mission = result.getFirst();
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