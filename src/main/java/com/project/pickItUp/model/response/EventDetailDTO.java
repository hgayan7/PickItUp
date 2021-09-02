package com.project.pickItUp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailDTO {
    private EventDTO event;
    private List<AddressDTO> address;
}
