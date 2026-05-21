package Missions_data.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Rank {

    @XmlEnumValue("GRADE_1")
    GRADE_1("GRADE_1"),

    @XmlEnumValue("GRADE_2")
    GRADE_2("GRADE_2"),

    @XmlEnumValue("GRADE_3")
    GRADE_3("GRADE_3"),

    @XmlEnumValue("UNKNOWN")
    UNKNOWN("UNKNOWN");

    private final String value;

    Rank(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Rank fromString(String text) {
        for (Rank level : Rank.values()) {
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