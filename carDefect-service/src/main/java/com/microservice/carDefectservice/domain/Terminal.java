package com.microservice.carDefectservice.domain;

import java.util.List;

import com.microservice.carDefectservice.enums.TerminalStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "terminals")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="terminal_id")
    private int terminalId;

    @Column(name="terminal_name", nullable = false)
    private String terminalName;
    
    
    @Enumerated(EnumType.STRING)
    private TerminalStatus status=TerminalStatus.ACTIVE;
    
    
    
    @OneToMany(mappedBy = "terminal", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Defect> defects;

    



}