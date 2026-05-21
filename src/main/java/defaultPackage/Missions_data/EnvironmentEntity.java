package defaultPackage.Missions_data;

import defaultPackage.Missions_data.Enums.Visibility;
import jakarta.persistence.*;

@Entity
@Table(name = "environments")
public class EnvironmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weather;
    private String timeOfDay;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private double cursedEnergyDensity;

    public EnvironmentEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public String getTimeOfDay() { return timeOfDay; }
    public void setTimeOfDay(String timeOfDay) { this.timeOfDay = timeOfDay; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }

    public double getCursedEnergyDensity() { return cursedEnergyDensity; }
    public void setCursedEnergyDensity(double cursedEnergyDensity) { this.cursedEnergyDensity = cursedEnergyDensity; }
}