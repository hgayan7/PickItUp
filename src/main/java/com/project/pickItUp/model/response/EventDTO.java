package com.project.pickItUp.model.response;

import com.project.pickItUp.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private int isValid;
}
