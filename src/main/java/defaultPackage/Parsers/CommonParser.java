package defaultPackage.Parsers;


import defaultPackage.Missions_data.MissionEntity;


public interface CommonParser {
    public boolean supportType(String filepath);
    Pair <MissionEntity, String> parse(String filepath);
}
