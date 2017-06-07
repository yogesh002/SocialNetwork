package com.parishram.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.parishram.common.Constant;
import com.parishram.json.response.AdminReplyMessage;
import com.parishram.model.UserModel;
import com.parishram.service.LoginService;
import com.parishram.service.MailService;
import com.parishram.service.exception.ServiceException;

@Controller
public class AdminController {
	private static Logger LOGGER = Logger.getLogger(AdminController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/admin/countemail", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Integer> countEmails() {
		try {
			int totalNewEmails = mailService.countNewEmails();
			int totalEmails = mailService.countTotalEmails();
			Map<String, Integer> emailCountMap = new HashMap<String, Integer>();
			emailCountMap.put("newEmails", totalNewEmails);
			emailCountMap.put("totalEmails", totalEmails);
			return emailCountMap;
		} catch (ServiceException exception) {
			LOGGER.error("Unable to count email." + exception.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "admin/updateemailcount", method = RequestMethod.POST)
	public void updateEmailCount(@RequestBody AdminReplyMessage replyMsg) {
		try {
			mailService.markEmailAsRead(replyMsg.getEmailId());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/deleteUsers", method = { RequestMethod.POST })
	public @ResponseBody String deleteUsers(@RequestBody TextNode loginId) {
		try {
			loginService.deleteUser(Integer.valueOf(loginId.asText()));
			return Constant.SUCCESS;
		} catch (ServiceException e) {
			return Constant.FAILURE;
		}
	}

	@RequestMapping(value = "/editRole", method = { RequestMethod.POST })
	public @ResponseBody String editUserRole(@RequestBody UserModel user) {
		try {
			int roleId = Integer.valueOf(user.getRole());
			loginService.editRoleOfUser(roleId, user.getLoginId());
			return loginService.getRole(user.getLoginId());
		} catch (ServiceException exception) {
			return Constant.FAILURE;
		}
	}
}
