package se.uu.its.integration.model.events;

import se.uu.its.integration.model.common.ModelUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

@XmlRootElement(name = "UUEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class UUEvent implements Serializable {

    private static final long serialVersionUID = 6670201491567203786L;

    @XmlAttribute(name = "identifier")
    String identifier;

    @XmlAttribute(name = "type")
    String type;

    @XmlElement(name = "ProcessedTime")
    Calendar processedTime;

    @XmlElement(name = "IssuedTime")
    Calendar issuedTime;

    @XmlElement(name = "Producer")
    String producer;


    @XmlElement(name = "ProducerReferenceId")
    String producerReferenceId;

    public UUEvent() {
    }

    public String toString() {

        String result = ModelUtils.TO_STRING_ERROR_MSG;

        try {
            result = ModelUtils.getMarchalledObjectXml(this.getClass(), this);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return result;

    }

    public UUEvent exportUUEvent() {
        UUEvent uuEvent = new UUEvent();
        uuEvent.identifier = this.identifier;
        uuEvent.issuedTime = this.issuedTime;
        uuEvent.processedTime = this.processedTime;
        uuEvent.producer = this.producer;
        uuEvent.producerReferenceId = this.producerReferenceId;
        uuEvent.type = this.type;
        return uuEvent;
    }

    protected UUEvent(String producer, String producerReferenceId) {
        this.issuedTime = GregorianCalendar.getInstance();
        this.type = this.getClass().getSimpleName();
        this.producer = producer;
        this.producerReferenceId = producerReferenceId;
    }

    public String getProducer() {
        return producer;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getProducerReferenceId() {
        return producerReferenceId;
    }

}
