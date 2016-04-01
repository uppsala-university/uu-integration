package se.uu.its.integration.ladok2groups.dto;

import java.util.Date;

public class Tst {
	
	Long id;
	String processorName;
	String messageId;
	Date createdAt;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcessorName() {
		return processorName;
	}
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "Tst [id=" + id + ", processorName=" + processorName
				+ ", messageId=" + messageId + ", createdAt=" + createdAt + "]";
	}
	
}
