package Parsers;


import Missions_data.Mission;


public interface CommonParser {
    public boolean supportType(String filepath);
    Pair <Mission, String> parse(String filepath);
}
