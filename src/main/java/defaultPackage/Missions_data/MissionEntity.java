package defaultPackage.Missions_data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import defaultPackage.Missions_data.Enums.Outcome;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "missions")
@JacksonXmlRootElement(localName = "mission")
public class MissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mission_id", unique = true)
    private String missionId;

    @Column(name = "mission_date")
    private String date;

    private String location;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    @Column(name = "damage_cost")
    private int damageCost;

    @Column(length = 2000)
    private String notes;

    @Column(length = 2000)
    private String comment;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "curse_id")
    private CurseEntity curse;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SorcererEntity> sorcerers = new ArrayList<>();

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TechniqueEntity> techniques = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "economic_id")
    private EconomicAssessmentEntity economicAssessment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "enemy_id")
    private EnemyActivityEntity enemyActivity;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "environment_id")
    private EnvironmentEntity environment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "civilian_id")
    private CivilianImpactEntity civilianImpact;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OperationEventEntity> operationTimeline = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mission_tags", joinColumns = @JoinColumn(name = "mission_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "tag")
    private List<String> operationTags = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mission_units", joinColumns = @JoinColumn(name = "mission_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "unit")
    private List<String> supportUnits = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mission_recommendations", joinColumns = @JoinColumn(name = "mission_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "recommendation")
    private List<String> recommendations = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mission_artifacts", joinColumns = @JoinColumn(name = "mission_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "artifact")
    private List<String> artifactsRecovered = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mission_zones", joinColumns = @JoinColumn(name = "mission_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "zone")
    private List<String> evacuationZones = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mission_effects", joinColumns = @JoinColumn(name = "mission_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "effect")
    private List<String> statusEffects = new ArrayList<>();

    public MissionEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Outcome getOutcome() { return outcome; }
    public void setOutcome(Outcome outcome) { this.outcome = outcome; }

    public int getDamageCost() { return damageCost; }
    public void setDamageCost(int damageCost) { this.damageCost = damageCost; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public CurseEntity getCurse() { return curse; }
    public void setCurse(CurseEntity curse) { this.curse = curse; }

    public List<SorcererEntity> getSorcerers() { return sorcerers; }
    public void setSorcerers(List<SorcererEntity> sorcerers) { this.sorcerers = sorcerers; }

    public List<TechniqueEntity> getTechniques() { return techniques; }
    public void setTechniques(List<TechniqueEntity> techniques) { this.techniques = techniques; }

    public EconomicAssessmentEntity getEconomicAssessment() { return economicAssessment; }
    public void setEconomicAssessment(EconomicAssessmentEntity economicAssessment) { this.economicAssessment = economicAssessment; }

    public EnemyActivityEntity getEnemyActivity() { return enemyActivity; }
    public void setEnemyActivity(EnemyActivityEntity enemyActivity) { this.enemyActivity = enemyActivity; }

    public EnvironmentEntity getEnvironment() { return environment; }
    public void setEnvironment(EnvironmentEntity environment) { this.environment = environment; }

    public CivilianImpactEntity getCivilianImpact() { return civilianImpact; }
    public void setCivilianImpact(CivilianImpactEntity civilianImpact) { this.civilianImpact = civilianImpact; }

    public List<OperationEventEntity> getOperationTimeline() { return operationTimeline; }
    public void setOperationTimeline(List<OperationEventEntity> operationTimeline) { this.operationTimeline = operationTimeline; }

    public List<String> getOperationTags() { return operationTags; }
    public void setOperationTags(List<String> operationTags) { this.operationTags = operationTags; }

    public List<String> getSupportUnits() { return supportUnits; }
    public void setSupportUnits(List<String> supportUnits) { this.supportUnits = supportUnits; }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }

    public List<String> getArtifactsRecovered() { return artifactsRecovered; }
    public void setArtifactsRecovered(List<String> artifactsRecovered) { this.artifactsRecovered = artifactsRecovered; }

    public List<String> getEvacuationZones() { return evacuationZones; }
    public void setEvacuationZones(List<String> evacuationZones) { this.evacuationZones = evacuationZones; }

    public List<String> getStatusEffects() { return statusEffects; }
    public void setStatusEffects(List<String> statusEffects) { this.statusEffects = statusEffects;
    }

}