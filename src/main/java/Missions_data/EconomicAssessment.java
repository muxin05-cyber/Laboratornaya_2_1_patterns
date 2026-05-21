package Missions_data;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class EconomicAssessment {
    @XmlElement()
    private int totalDamageCost;


    @XmlElement()
    private int infrastructureDamage;

    @XmlElement()
    private int transportDamage;

    @XmlElement()
    private int commercialDamage;

    @XmlElement()
    private int recoveryEstimateDays;

    @XmlElement()
    private boolean insuranceCovered;

    public int getTotalDamageCost() {
        return totalDamageCost;
    }

    public void setTotalDamageCost(int totalDamageCost) {
        this.totalDamageCost = totalDamageCost;
    }

    public int getInfrastructureDamage() {
        return infrastructureDamage;
    }

    public void setInfrastructureDamage(int infrastructureDamage) {
        this.infrastructureDamage = infrastructureDamage;
    }

    public int getTransportDamage() {
        return transportDamage;
    }

    public void setTransportDamage(int transportDamage) {
        this.transportDamage = transportDamage;
    }

    public int getCommercialDamage() {
        return commercialDamage;
    }

    public void setCommercialDamage(int commercialDamage) {
        this.commercialDamage = commercialDamage;
    }

    public int getRecoveryEstimateDays() {
        return recoveryEstimateDays;
    }

    public void setRecoveryEstimateDays(int recoveryEstimateDays) {
        this.recoveryEstimateDays = recoveryEstimateDays;
    }

    public boolean isInsuranceCovered() {
        return insuranceCovered;
    }

    public void setInsuranceCovered(boolean insuranceCovered) {
        this.insuranceCovered = insuranceCovered;
    }



}
