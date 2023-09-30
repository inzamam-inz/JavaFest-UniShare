package com.unishare.backend.service;

import com.unishare.backend.DTO.Request.NotificationRequest;
import com.unishare.backend.DTO.Response.NotificationResponse;
import com.unishare.backend.exceptionHandlers.ErrorMessageException;
import com.unishare.backend.model.Notification;
import com.unishare.backend.repository.NotificationRepository;
import com.unishare.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class NotificationService {

    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public NotificationResponse makeNotificationResponse(Notification notification) {
        return new NotificationResponse(
            notification.getId(),
            notification.getMessage(),
            notification.getCreatedAt().toString(),
            notification.getSender().getId(),
            notification.getReceiver().getId()
        );
    }

    public List<NotificationResponse> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream().map(this::makeNotificationResponse).collect(Collectors.toList());
    }

    public NotificationResponse getNotification(Long id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            return makeNotificationResponse(notificationOptional.get());
        }
        throw new ErrorMessageException("Notification not found with ID: " + id);
    }

    public NotificationResponse createNotification(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setMessage(notificationRequest.getMessage());
        notification.setSender(userRepository.findById(notificationRequest.getSenderId())
                .orElseThrow(() -> new ErrorMessageException("User not found with ID: " + notificationRequest.getSenderId())));
        notification.setReceiver(userRepository.findById(notificationRequest.getReceiverId())
                .orElseThrow(() -> new ErrorMessageException("Receiver not found with ID: " + notificationRequest.getReceiverId())));
        return makeNotificationResponse(notificationRepository.save(notification));
    }

    public List<NotificationResponse> getNotificationsByReceiverId(Long receiverId) {
        List<Notification> notifications = notificationRepository.findByReceiverId(receiverId);
        return notifications.stream().map(this::makeNotificationResponse).collect(Collectors.toList());
    }
}
