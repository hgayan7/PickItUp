package com.project.pickItUp.service;

import com.project.pickItUp.entity.Notification;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.response.NotificationDTO;
import com.project.pickItUp.repository.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper mapper;

    public String updateNotificationStatus(long userId, long  notificationId) {
        if(accountService.getUserId() == userId) {
            Optional<Notification> notification = notificationRepository.findById(notificationId);
            if(notification.isPresent()) {
                notification.get().setIsRead(1);
                notificationRepository.save(notification.get());
                return "Notification read status updated";
            } else {
                throw new ApiRequestException("Invalid notification id", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ApiRequestException("You don't have access to this resource", HttpStatus.FORBIDDEN);
        }
    }

    public List<NotificationDTO> findNotificationByUserId(Long userId) {
        return notificationRepository.findNotificationByUserId(userId).stream()
                .map( notification -> mapper.map(notification, NotificationDTO.class) ).collect(Collectors.toList());
    }
}
