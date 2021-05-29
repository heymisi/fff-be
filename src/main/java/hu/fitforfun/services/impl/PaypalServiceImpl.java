package hu.fitforfun.services.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import hu.fitforfun.configuration.properties.PaypalProperties;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.request.OrderResponse;
import hu.fitforfun.services.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaypalServiceImpl implements PaypalService {

    @Autowired
    APIContext apiContext;

    private PaypalProperties payPalProperties;

    public PaypalServiceImpl(PaypalProperties payPalProperties) {
        this.payPalProperties = payPalProperties;
    }

    @Override
    public Map<String, Object> createPayment(OrderResponse orderResponse) throws PayPalRESTException {
        Map<String, Object> response = new HashMap<String, Object>();

        Amount amount = new Amount();
        amount.setCurrency("HUF");
        amount.setTotal(orderResponse.getTotal().toString());

        Transaction transaction = new Transaction();
        transaction.setDescription("DESC");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(payPalProperties.getCancelUrl());
        redirectUrls.setReturnUrl(payPalProperties.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);
        Payment approvedPayment = payment.create(apiContext);
        response.put("status", "success");
        response.put("redirect_url", getApprovalLink(approvedPayment));
        return response;
    }

    @Override
    public Map<String, Object> executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Map<String, Object> response = new HashMap<>();
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        try {
            Payment createdPayment = payment.execute(apiContext, paymentExecute);
            if (createdPayment != null) {
                response.put("status", "success");
                response.put("payment", "createdPayment");
            }
        } catch (PayPalRESTException e) {
            response.put("status", "error");
        }
        return response;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }
}
