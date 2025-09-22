package com.nimsoc.pm.ps.dto;

import com.nimsoc.pm.ps.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequestDTO {
  @NotBlank(message = "Name is mandatory")
  @Size(max = 100, message = "Name can not be longer than 100 characters")
  private String name;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Address is mandatory")
  private String address;

  @NotBlank(message = "Date of Birth is mandatory")
  private String dateOfBirth;

  @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered Date is mandatory")
  private String registeredDate;
}
