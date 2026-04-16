package Missions_data;

import Missions_data.Enums.Visibility;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class Environment {
    @XmlElement
    private String weather;
    @XmlElement
    private String timeOfDay;
    @XmlElement
    private Visibility visibility;
    @XmlElement
    private double cursedEnergyDensity;

    public void setWeather(String weather){this.weather = weather;}
    public String getWeather() {
        return weather;
    }

    public String getTimeOfDay(){
        return timeOfDay;
    }
    public void setTimeOfDay(String timeOfDay){
        this.timeOfDay = timeOfDay;
    }

    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }


    public void setCursedEnergyDensity(double cursedEnergyDensity){
        this.cursedEnergyDensity = cursedEnergyDensity;
    }

    public double getCursedEnergyDensity() {
        return cursedEnergyDensity;
    }

    public Visibility getVisibility() {
        return visibility;
    }



}
