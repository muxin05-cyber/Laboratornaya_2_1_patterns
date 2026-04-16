package Missions_data.Enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum ThreatLevel {

    @XmlEnumValue("HIGH")
    HIGH("HIGH"),

    @XmlEnumValue("MEDIUM")
    MEDIUM("MEDIUM"),


    @XmlEnumValue("SPECIAL_GRADE")
    SPECIAL_GRADE("SPECIAL_GRADE"),

    @XmlEnumValue("UNKNOWN")
    UNKNOWN("UNKNOWN"),

    @XmlEnumValue("LOW")
    LOW("LOW");



    private final String value;

    ThreatLevel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ThreatLevel fromString(String text) {
        for (ThreatLevel level : ThreatLevel.values()) {
            if (level.value.equalsIgnoreCase(text)) {
                return level;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return value;
    }
}