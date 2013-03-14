package com.vijay.penphoto.entities;

import java.util.Arrays;
import java.util.List;

import com.kinvey.KinveyMetadata;
import com.kinvey.persistence.mapping.MappedEntity;
import com.kinvey.persistence.mapping.MappedField;

public class Event implements MappedEntity {

	private String id;
	private String name;
	private String location;
	private String date;
	private KinveyMetadata meta;
	
	@Override
	public List<MappedField> getMapping() {
		return Arrays.asList(new MappedField[] {
			new MappedField("id", "_id"),
			new MappedField("name", "name"),
			new MappedField("location", "location"),
			new MappedField("date", "date"),
			new MappedField("meta", KinveyMetadata.ENTITY_KEY)
		});
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public KinveyMetadata getMeta() {
		return meta;
	}
	public void setMeta(KinveyMetadata meta) {
		this.meta = meta;
	}
}
