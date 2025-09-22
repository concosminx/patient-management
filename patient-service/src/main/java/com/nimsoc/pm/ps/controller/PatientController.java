package com.nimsoc.pm.ps.controller;

import com.nimsoc.pm.ps.dto.PatientRequestDTO;
import com.nimsoc.pm.ps.dto.PatientResponseDTO;
import com.nimsoc.pm.ps.dto.validators.CreatePatientValidationGroup;
import com.nimsoc.pm.ps.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Patient management APIs")
public class PatientController {

  private final PatientService patientService;

  @GetMapping
  @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
  public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
    List<PatientResponseDTO> patients = patientService.getAllPatients();
    return ResponseEntity.ok().body(patients);
  }

  @PostMapping
  @Operation(summary = "Create a new patient", description = "Create a new patient with the provided details")
  public ResponseEntity<PatientResponseDTO> createPatient(
      @RequestBody @Validated({Default.class, CreatePatientValidationGroup.class})
          PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
    return ResponseEntity.ok().body(patient);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing patient", description = "Update the details of an existing patient by ID")
  public ResponseEntity<PatientResponseDTO> updatePatient(
      @PathVariable("id") UUID id,
      @RequestBody @Validated({Default.class}) PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
    return ResponseEntity.ok().body(patientResponseDTO);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a patient", description = "Delete an existing patient by ID")
  public ResponseEntity<Void> deletePatient(@PathVariable("id") UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
