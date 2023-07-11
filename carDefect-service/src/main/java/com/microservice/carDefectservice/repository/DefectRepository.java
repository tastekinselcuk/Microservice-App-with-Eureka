package com.microservice.carDefectservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.carDefectservice.domain.Defect;



@Repository
public interface DefectRepository extends JpaRepository<Defect, Integer> {

	
	List<Defect> findByDeletedFalse();

	Optional<Defect> findByDefectIdAndDeletedFalse(Integer id);
	
	Page<Defect> findByDeletedFalse(Pageable pageable);
	
    Page<Defect> findByDeletedFalseAndDefectPartCategoryContainingAndDefectPartNameContainingAndReportedByContainingAndTerminal_TerminalNameContaining(
            String defectPartCategory,
            String defectPartName,
            String reportedBy,
            String terminalName,
            Pageable pageable
    );
}