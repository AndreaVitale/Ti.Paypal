/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.paypal;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.util.TiConvert;

import ti.paypal.util.PaymentItem;
import ti.paypal.util.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ShippingAddress;

//import ti.paypal.util.*;

// This proxy can be created by calling Paypal.createExample({message: "hello world"})
@Kroll.proxy(creatableInModule = PaypalModule.class)
public class PaymentProxy extends KrollProxy {
	// Standard Debugging variables
	String currencyCode, shortDescription;
	int intent;

	// Constructor
	public PaymentProxy() {
		super();
	}

	// Handle creation options
	@Override
	public void handleCreationDict(KrollDict options) {
		super.handleCreationDict(options);
		if (options.containsKeyAndNotNull("currencyCode")) {
			currencyCode = TiConvert.toString(options.get("currencyCode"));
		}
		if (options.containsKeyAndNotNull("shortDescription")) {
			shortDescription = TiConvert.toString(options
					.get("shortDescription"));
		}
		if (options.containsKeyAndNotNull("intent")) {
			intent = TiConvert.toInt(options.get("intent"));
		}

		/* now importing of configuration and/or paymentitems : */
		if (options.containsKeyAndNotNull("items")) {
			List<KrollDict> paymentItems = new ArrayList<KrollDict>();
			if (!(paymentItems instanceof Object)) {
				throw new IllegalArgumentException("Invalid argument type `"
						+ paymentItems.getClass().getName()
						+ "` passed to consume()");
			}
			List<PayPalItem> paypalItems = new ArrayList<PayPalItem>();
			/* iterating thru array */
			for (int i = 0; i < paymentItems.size(); i++) {
				String name = "", sku = "", currency = "EU";
				BigDecimal price = new BigDecimal(0);
				int quantify = 1;
				KrollDict paymentItem = paymentItems.get(i);
				if (paymentItem.containsKeyAndNotNull("name")) {
					name = TiConvert.toString(paymentItem.get("name"));
				}
				if (paymentItem.containsKeyAndNotNull("sku")) {
					sku = TiConvert.toString(paymentItem.get("sku"));
				}
				if (paymentItem.containsKeyAndNotNull("currency")) {
					currency = TiConvert.toString(paymentItem.get("currency"));
				}
				if (paymentItem.containsKeyAndNotNull("quantify")) {
					quantify = TiConvert.toInt(paymentItem.get("quantify"));
				}
				if (paymentItem.containsKeyAndNotNull("price")) {
					price = new BigDecimal(TiConvert.toString(paymentItem.get("price")));
				}
				// name, quant, price, sku, currency
				paypalItems.add(new PayPalItem(name, quantify, price,
						sku, currency));
			}
		}
		if (options.containsKeyAndNotNull("configuration")) {
			KrollDict configuration = options.getKrollDict("configuration");
			if (!(configuration instanceof KrollDict)) {
				throw new IllegalArgumentException("Invalid argument type `"
						+ configuration.getClass().getName()
						+ "` passed to consume()");
			}

			PayPalConfiguration ppConfiguration = new PayPalConfiguration();
			ppConfiguration.merchantName(configuration
					.getString("merchantName"));
		}
	}
}