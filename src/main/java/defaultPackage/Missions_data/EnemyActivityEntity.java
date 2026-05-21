package defaultPackage.Missions_data;

import defaultPackage.Missions_data.Enums.EscalationRisk;
import defaultPackage.Missions_data.Enums.Mobility;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enemy_activities")
public class EnemyActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String behaviorType;
    private String targetPriority;

    @Enumerated(EnumType.STRING)
    private Mobility mobility;

    @Enumerated(EnumType.STRING)
    private EscalationRisk escalationRisk;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "enemy_attack_patterns", joinColumns = @JoinColumn(name = "enemy_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "pattern")
    private List<String> attackPatterns = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "enemy_countermeasures", joinColumns = @JoinColumn(name = "enemy_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "measure")
    private List<String> countermeasuresUsed = new ArrayList<>();

    public EnemyActivityEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBehaviorType() { return behaviorType; }
    public void setBehaviorType(String behaviorType) { this.behaviorType = behaviorType; }

    public String getTargetPriority() { return targetPriority; }
    public void setTargetPriority(String targetPriority) { this.targetPriority = targetPriority; }

    public Mobility getMobility() { return mobility; }
    public void setMobility(Mobility mobility) { this.mobility = mobility; }

    public EscalationRisk getEscalationRisk() { return escalationRisk; }
    public void setEscalationRisk(EscalationRisk escalationRisk) { this.escalationRisk = escalationRisk; }

    public List<String> getAttackPatterns() { return attackPatterns; }
    public void setAttackPatterns(List<String> attackPatterns) { this.attackPatterns = attackPatterns; }

    public List<String> getCountermeasuresUsed() { return countermeasuresUsed; }
    public void setCountermeasuresUsed(List<String> countermeasuresUsed) { this.countermeasuresUsed = countermeasuresUsed; }
}