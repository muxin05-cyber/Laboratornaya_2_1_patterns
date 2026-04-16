package Missions_data.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Mobility {

    @XmlEnumValue("HIGH")
    HIGH("HIGH"),
    @XmlEnumValue("UNKNOWN")
    UNKNOWN("UNKNOWN"),

    @XmlEnumValue("MEDIUM")
    MEDIUM("MEDIUM"),

    @XmlEnumValue("LOW")
    LOW("LOW");

    private final String value;

    Mobility(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Mobility fromString(String text) {
        for (Mobility level : Mobility.values()) {
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