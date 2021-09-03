package com.project.pickItUp.service;

import com.project.pickItUp.entity.Event;
import com.project.pickItUp.entity.Notification;
import com.project.pickItUp.entity.PickupRequest;
import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.helper.AllVolunteerPicker;
import com.project.pickItUp.helper.VolunteerPicker;
import com.project.pickItUp.model.PickupRequestStatus;
import com.project.pickItUp.model.request.PickupJSONRequest;
import com.project.pickItUp.model.response.PickupRequestDTO;
import com.project.pickItUp.repository.EventRepository;
import com.project.pickItUp.repository.NotificationRepository;
import com.project.pickItUp.repository.PickupRepository;
import com.project.pickItUp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PickupService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private PickupRepository pickupRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper mapper;

    public String createPickup(PickupJSONRequest request) {
        Optional<User> requestedBy = this.userRepository.findById(request.getRequestedBy());
        Optional<Event> forEvent = this.eventRepository.findById(request.getForEvent());
        if(requestedBy.isPresent() && forEvent.isPresent()) {
            PickupRequest pickupRequest = new PickupRequest();
            pickupRequest.setRequestedBy(requestedBy.get());
            String uniqueId = UUID.randomUUID().toString();
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date requestedDate = formatter.parse(request.getRequestedDate());
                pickupRequest.setRequestedDate(requestedDate);
            } catch (Exception e) {
                throw new ApiRequestException("Invalid date format", HttpStatus.BAD_REQUEST);
            }
            pickupRequest.setUniqueId(uniqueId);
            pickupRequest.setDescription(request.getDescription());
            pickupRequest.setRequestStatus(PickupRequestStatus.REQUESTED);
            pickupRequest.setForEvent(forEvent.get());
            requestedBy.get().getPickupRequests().add(pickupRequest);
            forEvent.get().getPickupRequests().add(pickupRequest);
            pickupRepository.save(pickupRequest);
            Optional<PickupRequest> savedRequest = pickupRepository.findByUniqueId(uniqueId);
            savedRequest.ifPresent(value -> fanOutNotification(value, forEvent.get(),
                    requestedBy.get(), new AllVolunteerPicker()));
            return "Pickup requested";
        } else {
            throw new ApiRequestException("Invalid user id or event id", HttpStatus.BAD_REQUEST);
        }
    }

    public void fanOutNotification(PickupRequest request, Event event, User user, VolunteerPicker picker) {
        picker.selectVolunteers(event.getEventVolunteers()).forEach(volunteer -> {
            Notification notification = new Notification();
            notification.setIsRead(0);
            notification.setRequest(request);
            notification.setUser(volunteer);
            notification.setTitle("Pickup request by "+user.getFirstName() + " for "+ event.getName());
            notificationRepository.save(notification);
        });
    }

    public List<PickupRequestDTO> getPickupRequestForCurrentUser() {
        long userId = accountService.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get().getPickupRequests().stream().map(request ->  mapper.map(request, PickupRequestDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new ApiRequestException("Invalid user id", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public String deletePickupRequest(Long requestId) {
        long userId = accountService.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            if(user.get().getPickupRequests().stream().filter(request -> request.getId() == requestId).count() == 0){
                throw new ApiRequestException("Not authorized to access this resource", HttpStatus.FORBIDDEN);
            }
            notificationRepository.deleteNotificationByPickupRequestId(requestId);
            pickupRepository.deleteById(requestId);
            return "Pickup request deleted";
        } else {
            throw new ApiRequestException("Not authorized to access this resource", HttpStatus.FORBIDDEN);
        }
    }

}
