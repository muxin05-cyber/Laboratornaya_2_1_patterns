package Missions_data;

import Missions_data.Enums.TechniqueType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Technique {
    @XmlElement
    private String name;
    @XmlElement
    private TechniqueType type;
    @XmlElement
    private String owner;
    @XmlElement
    private int damage;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }


    public void setType(TechniqueType type){
        this.type = type;
    }

    public TechniqueType getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }
}
