package hu.fitforfun.services;

import com.paypal.base.rest.PayPalRESTException;
import hu.fitforfun.model.request.OrderResponse;

import java.util.Map;

public interface PaypalService {
    Map<String, Object> createPayment(
            OrderResponse orderResponse) throws PayPalRESTException;

    Map<String, Object> executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
