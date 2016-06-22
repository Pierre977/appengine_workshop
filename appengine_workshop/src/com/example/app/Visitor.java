package com.example.app;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Visitor {

	@Id
	private Long id;
	
	private String nickName;
	
	@Index
	private String email;
	
	@Index
	private Long visitTimeStamp;
	
	private String visitTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getVisitTimeStamp() {
		return visitTimeStamp;
	}

	public void setVisitTimeStamp(Long visitTimeStamp) {
		this.visitTimeStamp = visitTimeStamp;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	
}
