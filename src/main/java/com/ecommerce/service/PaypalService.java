package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.PaypalDao;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalService {

	@Autowired
	PaypalDao paypalDao;

	public Payment createPayment(Double total, String currency, String method, String intent, String description,
			String cancelUrl, String successUrl) throws PayPalRESTException {
		return paypalDao.createPayment(total, currency, method, intent, description, cancelUrl, successUrl);
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		return paypalDao.executePayment(paymentId, payerId);
	}
}
