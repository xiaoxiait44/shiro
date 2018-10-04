package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiro.usershiro.User;
@Controller
public class ShiroFilerController {
	@RequestMapping(value="subLogin",method=RequestMethod.POST,
    produces = "application/json;charset=utf-8")
	@ResponseBody
	public String login(User user) {
		//获取当前主体
		Subject  subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassWord());
		try {
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
			return "登陆失败";
		}
		return "登陆成功";
	}
}
