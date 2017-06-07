package com.parishram.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.parishram.common.Constant;
import com.parishram.eventandlistner.ProfileActivationEvent;
import com.parishram.json.response.AdminReplyMessage;
import com.parishram.model.ContactFormModel;
import com.parishram.model.UserSignUp;
import com.parishram.service.MailService;
import com.parishram.service.ProfileActivationService;
import com.parishram.service.ProfileService;
import com.parishram.service.exception.EventListnerException;
import com.parishram.service.exception.ServiceException;

@SessionAttributes({"usersignup"})
@Controller
public class MailController {
	private static Logger LOGGER = Logger.getLogger(MailController.class);

	@Autowired
	private ProfileService profileService;

	@Autowired
	private MailService mailService;

	@Autowired
	private ProfileActivationService profileActivationService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@RequestMapping(value = "/admin/adminMessages", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getMessagesForAdmin() {
		try {
			List<Map<String, Object>> incomingEmailDetails = mailService.retrieveEmailsSentFromCustomer();
			// int totalNewEmails = mailService.countNewEmails();
			// int totalEmails = mailService.countTotalEmails();
			Map<String, Object> incomingEmailInfo = new HashMap<String, Object>();
			incomingEmailInfo.put("incomingEmailDetails", incomingEmailDetails);
			// incomingEmailInfo.put("totalNewEmails", totalNewEmails);
			// incomingEmailInfo.put("totalEmails", totalEmails);
			return incomingEmailInfo;

			// mailService.replayEmail(1);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/admin/replycustomer", method = RequestMethod.POST)
	public void replyCustomer(@RequestBody AdminReplyMessage replyMsg) {
		Runnable reply = new Runnable() {
			ContactFormModel contactForm = new ContactFormModel();

			@Override
			public void run() {
				contactForm.setSenderEmail(Constant.PROVIDER_EMAIL);
				contactForm.setReceiverEmail(replyMsg.getSender());
				contactForm.setMessageBody(replyMsg.getContent());
				try {
					profileService.sendEmailFromContactForm(contactForm);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Runnable saveReplyEmail = new Runnable() {

			@Override
			public void run() {
				try {
					mailService.replayEmail(replyMsg.getEmailId(), Constant.PROVIDER_EMAIL, replyMsg.getSender(),
							replyMsg.getContent());
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		reply.run();
		saveReplyEmail.run();
	}

	@RequestMapping(value = "/admin/deleteEmails", method = RequestMethod.POST)
	public void deleteSelectedEmail(@RequestBody AdminReplyMessage replyMsg, Model model) {
		ArrayList<Map<String, Object>> emailSelectedToDelete = (ArrayList<Map<String, Object>>) replyMsg.getEmails();
		List<Integer> emailIdList = new ArrayList<Integer>();
		for (Map<String, Object> map : emailSelectedToDelete) {
			if (map != null) {
				int emailId = (Integer) map.get("email_id");
				emailIdList.add(emailId);
			}
		}
		try {
			mailService.deleteSelectedEmail(emailIdList);
		} catch (ServiceException e) {
			LOGGER.error("Unable to delete emails sent from customers to admin." + e.getMessage());
		}
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String contactUs(HttpServletRequest request) {
		ContactFormModel contactForm = new ContactFormModel();
		contactForm.setSenderEmail(request.getParameter("contact_email"));
		contactForm.setReceiverEmail(Constant.PROVIDER_EMAIL);
		contactForm.setMessageBody(request.getParameter("contact_msg"));
		contactForm.setName(request.getParameter("contact_name"));
		try {
			profileService.sendEmailFromContactForm(contactForm);
			LOGGER.debug("Mail sent successfully");
			MailService.MailServiceHelpers mailServiceHelper = new MailService.MailServiceHelpers();
			List<Map<String, Object>> emailDetails = mailServiceHelper.getIncomingEmailDetails();
			for (Map<String, Object> details : emailDetails) {
				mailService.saveIncomingEmail((String) details.get("from"), (String) details.get("to"),
						(String) details.get("subject"), (String) details.get("message"),
						(Date) details.get("sentDate"));
			}
			LOGGER.debug("Incoming email Successfully saved in the database.");
			return "redirect:/mailsent";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
	public ModelAndView sendSignUpConfirmationEmail(@ModelAttribute("usersignup") UserSignUp signUpUserModel, HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		// TODO: catch mail exception
		try {
			EventListnerException listnerException = new EventListnerException();
			eventPublisher.publishEvent(
					new ProfileActivationEvent(signUpUserModel, listnerException, request.getContextPath(), Locale.US));
			if (listnerException.getErrorObject() != null
					|| StringUtils.isNotBlank(listnerException.getErrorMessage())) {
				LOGGER.error("Unable to sign up the user and send confirmation email. " + listnerException.getMessage(),
						listnerException);
				redirectAttributes.addFlashAttribute("errorMessage",
						"Unable to sign up the user. Please try again later");
				return new ModelAndView("redirect:/signupfailure");
			} else {
				return new ModelAndView("redirect:/signupsuccess");
			}
		} catch (Exception exception) {
			LOGGER.error("Exception is thrown while sending confirmation email during profile sign up."
					+ exception.getMessage(), exception);
			redirectAttributes.addFlashAttribute("errorMessage", "Unable to sign up the user. Please try again later");
			return new ModelAndView("login/signupfailure");
		}

	}

	@RequestMapping(value = "/emailconfirmation", method = RequestMethod.GET)
	public ModelAndView confirmEmail(@ModelAttribute("usersignup") UserSignUp signUpUserModel,
			@RequestParam(Constant.TOKEN) String token, final RedirectAttributes redirectAttributes) {
		try {
			boolean isTokenValid = profileActivationService.isTokenValid(signUpUserModel.getEmail(), token);
			if (!isTokenValid) {
				redirectAttributes.addFlashAttribute("errorMessage", "The token has expired. please try again later.");
				return new ModelAndView("login/signupfailure");
			} else {
				profileActivationService.enableUser(signUpUserModel.getEmail());
				return new ModelAndView("redirect:/");
			}
		} catch (ServiceException exception) {
			LOGGER.error(
					"Exception is thrown while enabling the user during profile activation. " + exception.getMessage(),
					exception);
			redirectAttributes.addFlashAttribute("errorMessage",
					"Unable to activate your profile. We are experiencing technical difficulties. Please try again later.");
			return new ModelAndView("login/signupfailure");
		}
	}

	@RequestMapping(value = "/mailsent", method = RequestMethod.GET)
	public String notifyEmailSent() {
		return "mail/mailsent";
	}

}
