package Missions_data;


import Missions_data.Enums.ThreatLevel;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Curse {

    @XmlElement()
    private String name;

    @XmlElement
    private ThreatLevel threatLevel;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setThreatLevel(ThreatLevel threatLevel){
        this.threatLevel = threatLevel;
    }

    public ThreatLevel getThreatLevel() {
        return threatLevel;
    }

}
