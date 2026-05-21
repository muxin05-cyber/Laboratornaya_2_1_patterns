package defaultPackage.Missions_data;

import defaultPackage.Missions_data.Enums.PublicExposureRisk;
import jakarta.persistence.*;

@Entity
@Table(name = "civilian_impacts")
public class CivilianImpactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int evacuated;
    private int injured;
    private int missing;

    @Enumerated(EnumType.STRING)
    private PublicExposureRisk publicExposureRisk;

    public CivilianImpactEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getEvacuated() { return evacuated; }
    public void setEvacuated(int evacuated) { this.evacuated = evacuated; }

    public int getInjured() { return injured; }
    public void setInjured(int injured) { this.injured = injured; }

    public int getMissing() { return missing; }
    public void setMissing(int missing) { this.missing = missing; }

    public PublicExposureRisk getPublicExposureRisk() { return publicExposureRisk; }
    public void setPublicExposureRisk(PublicExposureRisk publicExposureRisk) { this.publicExposureRisk = publicExposureRisk; }
}