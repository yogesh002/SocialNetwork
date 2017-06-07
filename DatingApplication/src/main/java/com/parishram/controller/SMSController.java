package com.parishram.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.parishram.common.Constant;
import com.parishram.model.LoginContext;
import com.parishram.model.SMSModel;
import com.parishram.service.ProfileService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;

@Controller
@SessionAttributes({ "loginContext" })
public class SMSController {
	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/sendText", method = RequestMethod.POST)
	public @ResponseBody String sendSMS(@RequestBody SMSModel smsModel,
			@ModelAttribute("loginContext") LoginContext context) {
		try {
			TwilioRestClient client = new TwilioRestClient(Constant.ACCOUNT_SID, Constant.AUTH_TOKEN);
			Account account = client.getAccount();
			MessageFactory messageFactory = account.getMessageFactory();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String searchUser = context.getSearchUser();
			int searchUserId = profileService.getLoginIdUsingUserName(searchUser);
			long phoneNumber = profileService.getPhoneNumber(searchUserId);
			params.add(new BasicNameValuePair("To", "+1" + String.valueOf(phoneNumber)));
			params.add(new BasicNameValuePair("From", Constant.FROM_NUMBER));
			params.add(new BasicNameValuePair("Body", smsModel.getMessage()));
			messageFactory.create(params);
			return Constant.SUCCESS;
		} catch (Exception exception) {
			return Constant.FAILURE;
		}
	}
}
