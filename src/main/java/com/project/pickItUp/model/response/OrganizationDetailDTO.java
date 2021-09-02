package com.project.pickItUp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDetailDTO {
    private OrganizationDTO organizationDTO;
    private List<AddressDTO> addressDTO;
}
