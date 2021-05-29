package hu.fitforfun.controller;

import com.paypal.base.rest.PayPalRESTException;
import hu.fitforfun.model.request.OrderResponse;
import hu.fitforfun.services.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/paypal")
public class PaypalController {

    @Autowired
    PaypalService service;

    @PostMapping("/make/payment")
    public Map<String, Object> makePayment(@RequestBody OrderResponse order) {
        try {
            return service.createPayment(order);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            Map<String, Object> returnError = new HashMap<>();
            returnError.put("status", "error");
            return returnError;
        }
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) throws PayPalRESTException {
        return service.executePayment(paymentId, payerId);
    }
}
