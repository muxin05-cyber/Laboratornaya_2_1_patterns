package Missions_data;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Sorcerer {
    @XmlElement
    private String name;
    @XmlElement
    private String rank;

    public void setName(String name){
        this.name = name;
    }

    public void setRank(String rank){
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }
}
