package Missions_data.Enums;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum TechniqueType{

    @XmlEnumValue("INNATE")
    INNATE("INNATE"),

    @XmlEnumValue("BODY")
    BODY("BODY"),

    @XmlEnumValue("UNKNOWN")
    UNKNOWN("UNKNOWN"),

    @XmlEnumValue("WEAPON")
    WEAPON("WEAPON"),


    @XmlEnumValue("BARRIER")
    BARRIER("BARRIER"),

    @XmlEnumValue("SHIKIGAMI")
    SHIKIGAMI("SHIKIGAMI");


    private final String value;

    TechniqueType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TechniqueType fromString(String text) {
        for (TechniqueType level : TechniqueType.values()) {
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