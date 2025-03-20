package com.user_organization_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDTO {
	private Long id;
    private String name;
   
    public OrganizationDTO(Long id, String name) {
    	super();
    	this.id = id;
    	this.name = name;
	}
    
    public OrganizationDTO(String name) {
    	super();
    	this.name = name;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}

