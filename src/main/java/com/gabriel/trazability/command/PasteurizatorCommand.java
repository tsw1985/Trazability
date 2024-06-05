package com.gabriel.trazability.command;

public class PasteurizatorCommand {

	private Long id;
	private String description;
	private String pasteurizing;
	private Double caudalSecond;
	private String ip;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		public String getPasteurizing() {
		return pasteurizing;
	}
	public void setPasteurizing(String pasteurizing) {
		this.pasteurizing = pasteurizing;
	}
	public Double getCaudalSecond() {
		return caudalSecond;
	}
	public void setCaudalSecond(Double caudalSecond) {
		this.caudalSecond = caudalSecond;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}