package com.nimsoc.pm.ps.service;

import com.nimsoc.pm.ps.dto.PatientRequestDTO;
import com.nimsoc.pm.ps.dto.PatientResponseDTO;
import com.nimsoc.pm.ps.exception.EmailAlreadyExistsException;
import com.nimsoc.pm.ps.exception.PatientNotFoundException;
import com.nimsoc.pm.ps.mapper.PatientMapper;
import com.nimsoc.pm.ps.model.Patient;
import com.nimsoc.pm.ps.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientRepository patientRepository;

  public List<PatientResponseDTO> getAllPatients() {
    List<Patient> patients = patientRepository.findAll();
    return patients.stream().map(PatientMapper::toDTO).toList();
  }

  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
    }

    Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
    return PatientMapper.toDTO(newPatient);
  }

  public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
    Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

    if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
      throw new EmailAlreadyExistsException("A patient with this email already exists: " + patientRequestDTO.getEmail());
    }

    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

    Patient updatedPatient = patientRepository.save(patient);

    return PatientMapper.toDTO(updatedPatient);
  }

  public void deletePatient(UUID id) {
    if (!patientRepository.existsById(id)) {
      throw new PatientNotFoundException("Patient not found with id: " + id);
    }
    patientRepository.deleteById(id);
  }
}
