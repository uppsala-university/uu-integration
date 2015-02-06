package se.uu.its.integration.model.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class UUEventDataProperty implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4728647207440127582L;

	private UUEventDataProperty(){
	}
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlElement(name = "CurrentValue")
	private String currentValue;
	
	@XmlElement(name = "PreviousValue")
	private String previousValue;

	public UUEventDataProperty(String name, String newValue) {
		this.name = name;
		this.currentValue = newValue;
	}
	
	public UUEventDataProperty(String name, String oldValue, String newValue) {
		this.name = name;
		this.previousValue = oldValue;
		this.currentValue = newValue;
	}	
}
