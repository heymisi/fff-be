package hu.fitforfun.services;

import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.user.User;

public interface EmailService {

    void sendPasswordResetRequest(User user, String token) throws Exception;

    void sendRegistrationMail(User user) throws Exception;

    void sendContactUsEmail(String email, String message) throws Exception;

    void sendOrderRecapMail(Transaction transaction) throws Exception;

    void sendApplyForTrainingSession(User client, TrainingSession session) throws Exception;

}
