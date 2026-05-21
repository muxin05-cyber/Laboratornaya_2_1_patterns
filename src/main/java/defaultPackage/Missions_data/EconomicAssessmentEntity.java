package defaultPackage.Missions_data;

import jakarta.persistence.*;

@Entity
@Table(name = "economic_assessments")
public class EconomicAssessmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalDamageCost;
    private int infrastructureDamage;
    private int transportDamage;
    private int commercialDamage;
    private int recoveryEstimateDays;
    private boolean insuranceCovered;

    public EconomicAssessmentEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getTotalDamageCost() { return totalDamageCost; }
    public void setTotalDamageCost(int totalDamageCost) { this.totalDamageCost = totalDamageCost; }

    public int getInfrastructureDamage() { return infrastructureDamage; }
    public void setInfrastructureDamage(int infrastructureDamage) { this.infrastructureDamage = infrastructureDamage; }

    public int getTransportDamage() { return transportDamage; }
    public void setTransportDamage(int transportDamage) { this.transportDamage = transportDamage; }

    public int getCommercialDamage() { return commercialDamage; }
    public void setCommercialDamage(int commercialDamage) { this.commercialDamage = commercialDamage; }

    public int getRecoveryEstimateDays() { return recoveryEstimateDays; }
    public void setRecoveryEstimateDays(int recoveryEstimateDays) { this.recoveryEstimateDays = recoveryEstimateDays; }

    public boolean isInsuranceCovered() { return insuranceCovered; }
    public void setInsuranceCovered(boolean insuranceCovered) { this.insuranceCovered = insuranceCovered; }
}