package Parsers;

import Missions_data.Mission;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLParser implements CommonParser{

    JAXBContext context;
    Unmarshaller unmarshaller;
    public XMLParser(){
        try {
            context = JAXBContext.newInstance(Mission.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        try {
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportType(String filepath) {
     return  (filepath != null && (filepath.endsWith(".xml")));
    }

    @Override
    public Pair<Mission, String> parse(String filepath) {
        String xml = null;
        Mission mission = null;
        try {
            xml = Files.readString(Paths.get(filepath));
        } catch (IOException e) {
            return new Pair<>(null, "Возникла проблема"+e.getMessage());
        }
        StringReader reader = new StringReader(xml);
        try {
            mission = (Mission)unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            return new Pair<>(null, "Структура файла не соответсвует шаблону файлов миссий типа xml");
        }
        return new Pair<>(mission, "");
    }
}
