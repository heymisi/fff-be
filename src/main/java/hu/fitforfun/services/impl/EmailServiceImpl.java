package hu.fitforfun.services.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import hu.fitforfun.enums.MailTemplate;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.FreeMarkerConsoleEx;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.EmailService;
import hu.fitforfun.services.UserService;
import hu.fitforfun.util.EmailConstants;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String RECIPIENT = "recipient";

    public void sendPasswordResetRequest(User user, String token) throws Exception {
        HashMap<String, Object> templateData = new HashMap<>();
        templateData.put(RECIPIENT, user.getContactData().getEmail());
        templateData.put("firstName", user.getFirstName());
        templateData.put("lastName", user.getLastName());
        templateData.put("token", token);

        sendMailFromTemplate(MailTemplate.PASSWORD_RESET, templateData);
    }

    @Override
    public void sendOrderRecapMail(Transaction transaction) throws Exception {
        String createdDate = DateFormatUtils.format(transaction.getTransactionCreated(), "yyyy-MM-dd");

        HashMap<String, Object> templateData = new HashMap<>();

        templateData.put(RECIPIENT, transaction.getPurchaser().getContactData().getEmail());
        templateData.put("userFirstName", transaction.getPurchaser().getFirstName());
        templateData.put("userLastName", transaction.getPurchaser().getLastName());
        templateData.put("email", transaction.getPurchaser().getContactData().getEmail());
        templateData.put("phone", transaction.getPurchaser().getContactData().getTelNumber());
        templateData.put("address", transaction.getPurchaser().getShippingAddress());
        templateData.put("total", transaction.getSumTotal());
        templateData.put("trackingNumber", transaction.getTrackingNumber());
        templateData.put("items", transaction.getTransactionItems());
        templateData.put("date", createdDate);

        sendMailFromTemplate(MailTemplate.ORDER_RECAP, templateData);

    }

    @Override
    public void sendApplyForTrainingSession(User client, TrainingSession session) throws Exception {
        HashMap<String, Object> templateData = new HashMap<>();

        templateData.put(RECIPIENT, session.getInstructor().getUser().getContactData().getEmail());
        templateData.put("firstName", session.getInstructor().getUser().getFirstName());
        templateData.put("lastName", session.getInstructor().getUser().getLastName());
        templateData.put("client", client);
        templateData.put("clientContactData", client.getContactData());
        templateData.put("sessionType", session.getTrainingSessionType());
        templateData.put("sessionDay", session.getDay());
        templateData.put("sessionStart", session.getSessionStart());
        templateData.put("sessionEnd", session.getSessionEnd());

        sendMailFromTemplate(MailTemplate.APPLY_FOR_TRAINING_SESSION, templateData);
    }


    @Override
    public void sendRegistrationMail(User user) throws Exception {
        HashMap<String, Object> templateData = new HashMap<>();

        templateData.put(RECIPIENT, user.getContactData().getEmail());
        templateData.put("firstName", user.getFirstName());
        templateData.put("lastName", user.getLastName());
        templateData.put("token", user.getEmailVerificationToken());

        sendMailFromTemplate(MailTemplate.REGISTRATION, templateData);
    }

    @Override
    public void sendContactUsEmail(String email, String message) throws Exception {
        HashMap<String, Object> templateData = new HashMap<>();

        templateData.put(RECIPIENT, "heymisi99@gmail.com");
        templateData.put("email", email);
        templateData.put("message", message);
        templateData.put("date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));

        sendMailFromTemplate(MailTemplate.CONTACT_US, templateData);
    }

    private String getProcessedMailSubject(MailTemplate mailTemplate, Map<String, Object> templateData) throws Exception {
        try {
            Configuration cfg = new Configuration(new Version("2.3.30"));
            cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/templates/");
            cfg.setDefaultEncoding("UTF-8");

            Template mailBodyTemplate = cfg.getTemplate(mailTemplate.getSubject());
            StringWriter mailBody = new StringWriter();
            mailBodyTemplate.process(templateData, mailBody);
            return mailBody.toString();
        } catch (Exception e) {
            throw e;
        }

    }

    private String getProcessedMailTemplate(MailTemplate mailTemplate, Map<String, Object> templateData) throws Exception {
        try {
            Configuration cfg = new Configuration(new Version("2.3.30"));
            cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/templates/");
            cfg.setDefaultEncoding("UTF-8");

            Template mailBodyTemplate = cfg.getTemplate(mailTemplate.getTemplateName());
            StringWriter mailBody = new StringWriter();
            mailBodyTemplate.process(templateData, mailBody);
            return mailBody.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public void sendMailFromTemplate(MailTemplate mailTemplate, Map<String, Object> templateData) throws Exception {
        String mailSubject = getProcessedMailSubject(mailTemplate, templateData);
        String mailBody = getProcessedMailTemplate(mailTemplate, templateData);

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().
                withRegion(Regions.EU_CENTRAL_1).build();

        SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses((String) templateData.get(RECIPIENT)))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(mailBody))
                                .withText(new Content().withCharset("UTF-8").withData(mailBody)))
                        .withSubject(new Content().withCharset("UTF-8").withData(mailSubject)))
                .withSource(EmailConstants.getEmailSender());
        client.sendEmail(request);
    }

}
