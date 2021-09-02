package com.project.pickItUp.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private int isRead;
    private String title;
}
