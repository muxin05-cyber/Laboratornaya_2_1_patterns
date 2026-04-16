package Missions_data;


import Missions_data.Enums.PublicExposureRisk;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class CivilianImpact {

    @XmlElement()
    private int evacuated;

    @XmlElement()
    private int injured;

    @XmlElement()
    private int missing;


    @XmlElement(name = "publicExposureRisk")
    private PublicExposureRisk publicExposureRisk;

    public void setEvacuated(int evacuated){
        this.evacuated = evacuated;
    }

    public int getInjured() {
        return injured;
    }


    public void setInjured(int injured){
        this.injured = injured;
    }

    public int getMissing() {
        return missing;
    }

    public void setMissing(int missing){
        this.missing = missing;
    }

    public int getEvacuated() {
        return evacuated;
    }


    public void setPublicExposureRisk(PublicExposureRisk publicExposureRisk){
        this.publicExposureRisk = publicExposureRisk;
    }

    public PublicExposureRisk getPublicExposureRisk() {
        return publicExposureRisk;
    }


}
