package com.microservice.userTerminalManagementservice.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "locations")
public class Location {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="location_id")
    private int locationId;
    
	@Column(name="latitude")
    private double latitude;
	
	@Column(name="longitude")
    private double longitude;

    @Column(name = "is_deleted")
    private boolean deleted = false;
	
	
    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Defect> defects = new ArrayList<>();
}