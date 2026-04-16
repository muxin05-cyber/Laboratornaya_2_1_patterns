package Missions_data.Enums;

import Parsers.Pair;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.xml.bind.annotation.XmlEnumValue;

public enum PublicExposureRisk {
    @XmlEnumValue("HIGH")
    HIGH("HIGH"),

    @XmlEnumValue("MEDIUM")
    MEDIUM("MEDIUM"),

    @XmlEnumValue("UNKNOWN")
    UNKNOWN("UNKNOWN"),

    @XmlEnumValue("LOW")
    LOW("LOW");

    private final String value;

    PublicExposureRisk(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PublicExposureRisk fromString(String text) {
        for (PublicExposureRisk o : PublicExposureRisk.values()) {
            if (o.value.equalsIgnoreCase(text)) {
                return o;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return value;
    }
}
