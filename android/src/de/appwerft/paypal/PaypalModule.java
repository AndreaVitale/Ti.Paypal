/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package de.appwerft.paypal;

import java.util.Locale;
import java.util.Currency;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.titanium.TiProperties;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.kroll.common.Log;

import android.content.Context;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.util.ArrayList;

// alternativ: https://github.com/luis1987/PayPalAppcelerator/blob/master/android/src/com/bea/paypal/ModulopaypalModule.java

@Kroll.module(name = "Paypal", id = "de.appwerft.paypal")
public class PaypalModule extends KrollModule {
	private static final String LCAT = "PaypalModule  💰";
	private int debugLevel;
	private String clientIdSandbox;
	private String clientIdProduction;
	public static String clientId;
	private static boolean acceptCreditCards = true;
	private static int environment;
	public static String CONFIG_ENVIRONMENT;

	public static int debug = 0;

	public static PayPalConfiguration ppConfiguration;

	@Kroll.constant
	public static final int ENVIRONMENT_SANDBOX = 0;
	@Kroll.constant
	public static final int ENVIRONMENT_PRODUCTION = 1;
	@Kroll.constant
	public static final int PAYMENT_INTENT_SALE = 0;
	@Kroll.constant
	public static final int PAYMENT_INTENT_AUTHORIZE = 1;
	@Kroll.constant
	public static final int PAYMENT_INTENT_ORDER = 2;

	private TiProperties appProperties;

	public PaypalModule() {
		super();
	}

	@Kroll.method
	public void initialize(@Kroll.argument(optional = true) KrollDict args) {
		this.initPayment(args);
	}

	@Kroll.method
	public void initPayment(@Kroll.argument(optional = true) KrollDict args) {
		appProperties = TiApplication.getInstance().getAppProperties();
		String environmentString = appProperties.getString(
				"PAYPAL_ENVIRONMENT", "SANDBOX");
		if (environmentString.equals("SANDBOX")) {
			environment = ENVIRONMENT_SANDBOX;
		}
		if (environmentString.equals("PRODUCTION")) {
			environment = ENVIRONMENT_PRODUCTION;
		}
		clientIdSandbox = appProperties.getString("PAYPAL_CLIENT_ID_SANDBOX",
				"");
		clientIdProduction = appProperties.getString(
				"PAYPAL_CLIENT_ID_PRODUCTION", "");

		/* these was defaults from tiapp.xml, now runtime parameters: */
		if (args != null && args instanceof KrollDict) {
			if (args.containsKeyAndNotNull("clientIdSandbox")) {
				Log.d(LCAT, "importing clientIdSandbox from init");
				clientIdSandbox = TiConvert.toString(args
						.get("clientIdSandbox"));
			}
			if (args.containsKeyAndNotNull("clientIdProduction")) {
				Log.d(LCAT, "importing clientIdProd from init");
				clientIdProduction = TiConvert.toString(args
						.get("clientIdProduction"));
			}
			if (args.containsKeyAndNotNull("environment")) {
				Log.d(LCAT, "importing environment from init");
				environment = TiConvert.toInt(args.get("environment"));
			}
			if (args.containsKeyAndNotNull("acceptCreditCards")) {
				acceptCreditCards = TiConvert.toBoolean(args
						.get("acceptCreditCards"));
			}
			if (environment == ENVIRONMENT_SANDBOX) {
				Log.d(LCAT, "env is sandbox");
				CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
				clientId = clientIdSandbox;
			}
			if (environment == ENVIRONMENT_PRODUCTION) {
				Log.d(LCAT, "env is prod");
				CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
				clientId = clientIdProduction;
			}

		}
		Log.d(LCAT, ">>>>>>>> clientId=" + clientId);
		Context context = TiApplication.getInstance().getApplicationContext();
		/* starting paypal service */
		ppConfiguration = new PayPalConfiguration()
				.environment(CONFIG_ENVIRONMENT).clientId(clientId)
				.languageOrLocale("en").acceptCreditCards(false);
		Intent intent = new Intent(context, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
				ppConfiguration);
		context.startService(intent);
		Log.d(LCAT, "PayPalService started " + CONFIG_ENVIRONMENT
				+ " clientId=" + clientId);
	}

	@Kroll.method
	public KrollDict createConfiguration(KrollDict args) {
		return args;

	}

	@Kroll.method
	public KrollDict createPaymentItem(KrollDict args) {
		return args;
	}

	@Kroll.method
	public void setDebug(int level) {
		debug = level;
	}

	@Kroll.method
	public ArrayList<KrollDict> getAllCurrencySigns() {
		ArrayList<KrollDict> list = new ArrayList<KrollDict>();
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale l : locales) {
			if (null == l.getCountry() || l.getCountry().isEmpty())
				continue;
			Currency c = Currency.getInstance(l);
			KrollDict item = new KrollDict();
			item.put("country", l.getCountry());
			item.put("displayCountry", l.getDisplayCountry());
			item.put("iso3country", l.getISO3Country());
			item.put("symbol", c.getSymbol());
			item.put("displayName", c.getDisplayName());
			list.add(item);
		}
		return list;
	}
}
