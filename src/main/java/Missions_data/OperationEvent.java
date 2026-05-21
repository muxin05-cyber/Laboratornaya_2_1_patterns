package Missions_data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OperationEvent {

    @XmlElement
    private String timestamp;

    @XmlElement
    private String type;

    @XmlElement
    private String description;

    public OperationEvent() {}

    public OperationEvent(String timestamp, String type, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}