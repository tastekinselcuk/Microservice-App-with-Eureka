package com.microservice.carDefectservice.domain;


import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name="cars")
public class Car {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="car_id")
	private int carId;	

	@Column(name="car_model")
	private String carModel;
	

    @Column(name = "is_deleted")
    private boolean deleted = false;
	
	
	
	@OneToMany(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Defect> defects;
		
}

		

