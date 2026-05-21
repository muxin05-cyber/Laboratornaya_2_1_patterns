package defaultPackage.Missions_data;

import defaultPackage.Missions_data.Enums.ThreatLevel;
import jakarta.persistence.*;

@Entity
@Table(name = "curses")
public class CurseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "threat_level")
    private ThreatLevel threatLevel;

    public CurseEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ThreatLevel getThreatLevel() { return threatLevel; }
    public void setThreatLevel(ThreatLevel threatLevel) { this.threatLevel = threatLevel; }
}