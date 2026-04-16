package Missions_data;
import Missions_data.Enums.Outcome;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "mission")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mission {
    @XmlElement(name = "missionId")
    private String missionId;

    @XmlElement
    private String date;
    @XmlElement
    private String location;
    @XmlElement
    private Outcome outcome;
    @XmlElement(name = "damageCost")
    private int damageCost;
    @XmlElement(name = "curse")
    private Curse curse;
    @XmlElementWrapper(name = "sorcerers")
    @XmlElement(name = "sorcerer")
    private List<Sorcerer> sorcerers;
    @XmlElementWrapper(name = "techniques")
    @XmlElement(name = "technique")
    private List<Technique> techniques;
    @XmlElement(name = "enemyActivity")
    private EnemyActivity enemyActivity;
    @XmlElement(name = "environment")
    private Environment environment;

    @XmlElement(name = "economicAssessment")
    private EconomicAssessment economicAssessment;

    @XmlElement(name = "civilianImpact")
    private CivilianImpact civilianImpact;

    @XmlElement
    private String comment = "";

    @XmlElementWrapper(name = "operationTimeline")
    @XmlElement(name = "event")
    private List<OperationEvent> operationTimeline;

    @XmlElementWrapper(name = "operationTags")
    @XmlElement(name = "tag")
    private List<String> operationTags;

    @XmlElementWrapper(name = "supportUnits")
    @XmlElement(name = "unit")
    private List<String> supportUnits;

    @XmlElementWrapper(name = "recommendations")
    @XmlElement(name = "recommendation")
    private List<String> recommendations;

    @XmlElement
    private String notes;

    @XmlElementWrapper(name = "artifactsRecovered")
    @XmlElement(name = "artifact")
    private List<String> artifactsRecovered;

    @XmlElementWrapper(name = "evacuationZones")
    @XmlElement(name = "zone")
    private List<String> evacuationZones;

    @XmlElementWrapper(name = "statusEffects")
    @XmlElement(name = "effect")
    private List<String> statusEffects;

    public List<OperationEvent> getOperationTimeline() {
        return operationTimeline;
    }

    public void setOperationTimeline(List<OperationEvent> operationTimeline) {
        this.operationTimeline = operationTimeline;
    }

    public void addOperationEvent(OperationEvent event) {
        if (this.operationTimeline == null) {
            this.operationTimeline = new ArrayList<>();
        }
        this.operationTimeline.add(event);
    }


    public CivilianImpact getCivilianImpact(){
        return civilianImpact;
    }
    public void setCivilianImpact(CivilianImpact civilianImpact){
        this.civilianImpact = civilianImpact;
    }
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    public void setEconomicAssessment(EconomicAssessment economicAssessment){this.economicAssessment = economicAssessment;}

    public Environment getEnvironment(){
        return environment;
    }



    public EnemyActivity getEnemyActivity() {
        return enemyActivity;
    }

    public void setEnemyActivity(EnemyActivity enemyActivity) {
        this.enemyActivity = enemyActivity;
    }

    public EconomicAssessment getEconomicAssessment(){
        return economicAssessment;
    }






    public Mission() {}

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setDamageCost(int damageCost) {
        this.damageCost = damageCost;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    public int getDamageCost() {
        return damageCost;
    }

    public Curse getCurse() {
        return curse;
    }

    public void setSorcerers(List<Sorcerer> sorcerers) {
        this.sorcerers = sorcerers;
    }
    public void setTechniques(List<Technique> techniques){this.techniques = techniques;}

    public void setTechnique(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getOperationTags() {
        return operationTags;
    }

    public void setOperationTags(List<String> operationTags) {
        this.operationTags = operationTags;
    }

    public void addOperationTag(String tag) {
        if (this.operationTags == null) {
            this.operationTags = new ArrayList<>();
        }
        this.operationTags.add(tag);
    }

    public List<String> getSupportUnits() {
        return supportUnits;
    }

    public void setSupportUnits(List<String> supportUnits) {
        this.supportUnits = supportUnits;
    }

    public void addSupportUnit(String unit) {
        if (this.supportUnits == null) {
            this.supportUnits = new ArrayList<>();
        }
        this.supportUnits.add(unit);
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public void addRecommendation(String recommendation) {
        if (this.recommendations == null) {
            this.recommendations = new ArrayList<>();
        }
        this.recommendations.add(recommendation);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getArtifactsRecovered() {
        return artifactsRecovered;
    }

    public void setArtifactsRecovered(List<String> artifactsRecovered) {
        this.artifactsRecovered = artifactsRecovered;
    }

    public void addArtifact(String artifact) {
        if (this.artifactsRecovered == null) {
            this.artifactsRecovered = new ArrayList<>();
        }
        this.artifactsRecovered.add(artifact);
    }

    public List<String> getEvacuationZones() {
        return evacuationZones;
    }

    public void setEvacuationZones(List<String> evacuationZones) {
        this.evacuationZones = evacuationZones;
    }

    public void addEvacuationZone(String zone) {
        if (this.evacuationZones == null) {
            this.evacuationZones = new ArrayList<>();
        }
        this.evacuationZones.add(zone);
    }

    public List<String> getStatusEffects() {
        return statusEffects;
    }

    public void setStatusEffects(List<String> statusEffects) {
        this.statusEffects = statusEffects;
    }

    public void addStatusEffect(String effect) {
        if (this.statusEffects == null) {
            this.statusEffects = new ArrayList<>();
        }
        this.statusEffects.add(effect);
    }
}
