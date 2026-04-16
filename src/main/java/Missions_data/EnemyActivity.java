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


    public EnemyActivity() {
        this.attackPatterns = new ArrayList<>();
        this.countermeasuresUsed = new ArrayList<>();
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


}