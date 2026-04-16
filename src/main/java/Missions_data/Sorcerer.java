package Missions_data;


import Missions_data.Enums.Rank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Sorcerer {
    @XmlElement
    private String name;
    @XmlElement
    private Rank rank;

    public void setName(String name){
        this.name = name;
    }

    public void setRank(Rank rank){
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public Rank getRank() {
        return rank;
    }
}
