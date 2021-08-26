package com.project.pickItUp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateRequest {
    private String address;
    private Long cityId;
    private Long requesterId;
    private Integer parentType;
}
