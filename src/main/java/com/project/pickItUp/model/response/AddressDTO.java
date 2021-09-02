package com.project.pickItUp.model.response;

import com.project.pickItUp.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String address;
    private CityDTO city;
}
