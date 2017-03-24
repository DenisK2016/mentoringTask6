package com.dk.mentoring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "computer")
public class Computer {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column
	private Date created;
	@Column
	private Boolean active;
	@Column
	private byte[] image;

	public Integer getId() {
		return id;
	}

	@XmlElement
	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(final String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	@XmlElement
	public void setCreated(final Date created) {
		this.created = created;
	}

	public Boolean getActive() {
		return active;
	}

	@XmlElement
	public void setActive(final Boolean active) {
		this.active = active;
	}

	public byte[] getImage() {
		return image;
	}

	@XmlElement
	public void setImage(byte[] image) {
		this.image = image;
	}
}
