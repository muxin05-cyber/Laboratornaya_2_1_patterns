package Missions_data.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Outcome {

    @XmlEnumValue("SUCCESS")
    SUCCESS("SUCCESS"),

    @XmlEnumValue("UNKNOWN")
    UNKNOWN("UNKNOWN"),

    @XmlEnumValue("FAILURE")
    FAILURE("FAILURE"),

    @XmlEnumValue("PARTIAL_SUCCESS")
    PARTIAL_SUCCESS("PARTIAL_SUCCESS");

    private final String value;

    Outcome(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Outcome fromString(String text) {
        for (Outcome o : Outcome.values()) {
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