package se.uu.its.integration.model.events;

import se.uu.its.integration.model.identity.Person;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PersonEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class PersonEvent extends UUEvent {

    private static final long serialVersionUID = -5522728677632277563L;

    @XmlElementRef(type = Person.class)
    private Person person;

    @XmlElementRef(type = PersonEventData.class)
    private PersonEventData personEventData;

    protected PersonEvent() {
    }

    protected PersonEvent(String producer, String producerReferenceId, Person person) {
        super(producer, producerReferenceId);
        this.person = person;
    }

    protected PersonEvent(String producer, String producerReferenceId, Person person, PersonEventData personEventData) {
        super(producer, producerReferenceId);
        this.person = person;
        this.personEventData = personEventData;
    }

}
