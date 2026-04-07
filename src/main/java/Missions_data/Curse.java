package Missions_data;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Curse {

    @XmlElement()
    private String name;

    @XmlElement
    private String threatLevel;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setThreatLevel(String threatLevel){
        this.threatLevel = threatLevel;
    }

    public String getThreatLevel() {
        return threatLevel;
    }
}
