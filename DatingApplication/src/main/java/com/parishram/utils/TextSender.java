package com.parishram.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;

public class TextSender {
	public static final String ACCOUNT_SID = "AC2f8e6dabd9037dafd8e7cbabfc91ef39";
	public static final String AUTH_TOKEN = "86b2c53a5314885db88ec67a7d545993";

	public static void main(String[] args) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		Account account = client.getAccount();
		MessageFactory messageFactory = account.getMessageFactory();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", "+17044019724"));
		params.add(new BasicNameValuePair("From", "+16078212972 "));
		params.add(new BasicNameValuePair("Body", "Hello.."));
		messageFactory.create(params);
	}
}
