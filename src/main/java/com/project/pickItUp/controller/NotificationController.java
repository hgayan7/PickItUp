package com.project.pickItUp.controller;

import com.project.pickItUp.model.GenericApiResponse;
import com.project.pickItUp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/update/notification/read/{notificationId}/userId/{userId}")
    public ResponseEntity<GenericApiResponse> updateNotificationReadStatus(@PathVariable Long notificationId,
                                                                           @PathVariable Long userId) {
        return new ResponseEntity<>(new GenericApiResponse(notificationService.
                updateNotificationStatus(userId,notificationId)), HttpStatus.OK);
    }
}
