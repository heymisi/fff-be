package hu.fitforfun.controller;

import hu.fitforfun.exception.Response;
import hu.fitforfun.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("/contact-us")
    public Response contactUs(@RequestParam(value = "email", required = true) String email,
                              @RequestParam(value = "message", required = true) String message) {
        try {
            emailService.sendContactUsEmail(email, message);
            return Response.createOKResponse("Contact us mail sent");
        } catch (Exception e) {
            return Response.createOKResponse("Error in contact us mail");
        }

    }
}
