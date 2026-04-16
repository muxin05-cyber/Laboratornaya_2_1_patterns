package Missions_data;


import Missions_data.Enums.EscalationRisk;
import Missions_data.Enums.Mobility;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class EnemyActivity {

    @XmlElement(name = "behaviorType")
    private String behaviorType;

    @XmlElement(name = "targetPriority")
    private String targetPriority;

    @XmlElement(name = "mobility")
    private Mobility mobility;

    @XmlElement(name = "escalationRisk")
    private EscalationRisk escalationRisk;

    @XmlElementWrapper(name = "attackPatterns")
    @XmlElement(name = "pattern")
    private List<String> attackPatterns;

    @XmlElementWrapper(name = "countermeasuresUsed")
    @XmlElement(name = "measure")
    private List<String> countermeasuresUsed;

    @XmlElement(name = "retreatStrategy")
    private String retreatStrategy;

    @XmlElement(name = "coordinationLevel")
    private String coordinationLevel;

    @XmlElementWrapper(name = "attackTypes")
    @XmlElement(name = "type")
    private List<String> attackTypes;

    @XmlElementWrapper(name = "vulnerabilities")
    @XmlElement(name = "vulnerability")
    private List<String> vulnerabilities;

    public EnemyActivity() {
        this.attackPatterns = new ArrayList<>();
        this.countermeasuresUsed = new ArrayList<>();
        this.attackTypes = new ArrayList<>();
        this.vulnerabilities = new ArrayList<>();
    }


    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getTargetPriority() {
        return targetPriority;
    }

    public void setTargetPriority(String targetPriority) {
        this.targetPriority = targetPriority;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    public EscalationRisk getEscalationRisk() {
        return escalationRisk;
    }

    public void setEscalationRisk(EscalationRisk escalationRisk) {
        this.escalationRisk = escalationRisk;
    }

    public List<String> getAttackPatterns() {
        return attackPatterns;
    }

    public void setAttackPatterns(List<String> attackPatterns) {
        this.attackPatterns = attackPatterns != null ? attackPatterns : new ArrayList<>();
    }

    public void addAttackPattern(String pattern) {
        if (this.attackPatterns == null) {
            this.attackPatterns = new ArrayList<>();
        }
        this.attackPatterns.add(pattern);
    }

    public List<String> getCountermeasuresUsed() {
        return countermeasuresUsed;
    }

    public void setCountermeasuresUsed(List<String> countermeasuresUsed) {
        this.countermeasuresUsed = countermeasuresUsed != null ? countermeasuresUsed : new ArrayList<>();
    }

    public void addCountermeasure(String measure) {
        if (this.countermeasuresUsed == null) {
            this.countermeasuresUsed = new ArrayList<>();
        }
        this.countermeasuresUsed.add(measure);
    }

    public String getRetreatStrategy() {
        return retreatStrategy;
    }

    public void setRetreatStrategy(String retreatStrategy) {
        this.retreatStrategy = retreatStrategy;
    }

    public String getCoordinationLevel() {
        return coordinationLevel;
    }

    public void setCoordinationLevel(String coordinationLevel) {
        this.coordinationLevel = coordinationLevel;
    }

    public List<String> getAttackTypes() {
        return attackTypes;
    }

    public void setAttackTypes(List<String> attackTypes) {
        this.attackTypes = attackTypes != null ? attackTypes : new ArrayList<>();
    }

    public void addAttackType(String type) {
        if (this.attackTypes == null) {
            this.attackTypes = new ArrayList<>();
        }
        this.attackTypes.add(type);
    }

    public List<String> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<String> vulnerabilities) {
        this.vulnerabilities = vulnerabilities != null ? vulnerabilities : new ArrayList<>();
    }

    public void addVulnerability(String vulnerability) {
        if (this.vulnerabilities == null) {
            this.vulnerabilities = new ArrayList<>();
        }
        this.vulnerabilities.add(vulnerability);
    }

}