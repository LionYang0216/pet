package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.gzsoftware.pet.entity.po.User;
import com.gzsoftware.pet.entity.po.UserProdFav;
import com.gzsoftware.pet.entity.po.UserShopFav;
import com.gzsoftware.pet.entity.po.UserWareFav;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.UploadFileService;
import com.gzsoftware.pet.service.UserProdFavService;
import com.gzsoftware.pet.service.UserService;
import com.gzsoftware.pet.service.UserShopFavService;
import com.gzsoftware.pet.service.UserWareFavService;
import com.gzsoftware.pet.utils.CommonUtil;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

	private static Log log = LogFactory.getLog(UserController.class);

	@Resource
	private UserService userService;

	@Resource
	private JavaMailSenderImpl mailSender;

	@Resource
	private UploadFileService uploadFileService;
	@Resource
	private UserWareFavService userWareFavService;
	@Resource
	private UserShopFavService userShopFavService;
	@Resource
	private UserProdFavService userPordFavService;

	/**
	 * 获取所有User
	 * 
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 **/

	@ResponseBody
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public Result getUserList(@RequestBody DataTablesRequest dtRequest) {
		List<User> userList = userService.getUserList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS, userList, userService.countAll(dtRequest),
				userService.countAll(dtRequest));
	}

	@ResponseBody
	@RequestMapping(value = "/getUserListForSelect", method = RequestMethod.POST)
	public Result getUserListForSelect() {
		List<User> userList = userService.getUserListForSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS, userList);
	}

	@ResponseBody
	@RequestMapping(value = "/getUserListByUserName", method = RequestMethod.POST)
	public Result getUserListByUserName(@RequestParam("skeyword") String skeyword) {
		List<User> userList = userService.getUserListByUserName(skeyword);
		return new Result(Result.RESULT_FLAG_SUCCESS, userList);
	}

	/**
	 * 根据ID获取User
	 **/

	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public Result getUser(@RequestParam("id") int id) {
		User user = userService.getUser(id);
		return new Result(Result.RESULT_FLAG_SUCCESS, user);
	}

	/**
	 * 新增 User
	 **/
	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public Result addUser(@RequestBody User user) {
		user.setUpdateTime(new Date());
		user.setRegTime(new Date());
		user.setLastLoginTime(new Date());
		user.setLoginCount(0);
		int effCnt = userService.addUser(user);
		if (effCnt > 0) {
			if (user.getHeadFileId() != null) {
				this.uploadFileService.updateFileToUsed(user.getHeadFileId());
			}
			return new Result(Result.RESULT_FLAG_SUCCESS, "添加成功", user);
		} else {
			return new Result(Result.RESULT_FLAG_FAIL, "添加失败", user);
		}
	}

	/**
	 * 更新 User
	 **/
	@ResponseBody
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public Result updateUser(@RequestBody User user, HttpSession session) {
		user.setUpdateTime(new Date());
		int effCnt = userService.updateUser(user);
		if (effCnt > 0) {
			if (user.getHeadFileId() != null) {
				this.uploadFileService.updateFileToUsed(user.getHeadFileId());
			}
			if (super.getCurrentUser(session) != null) {
				session.setAttribute("user", user);
			}
			return new Result(Result.RESULT_FLAG_SUCCESS, "更新成功", user);
		} else {
			return new Result(Result.RESULT_FLAG_FAIL, "更新失败", user);
		}
	}

	/**
	 * 删除 User
	 **/
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public Result deleteUser(@RequestParam("id") int id) {
		User user = userService.getUser(id);
		if (user.getHeadFileId() != null) {
			uploadFileService.updateFileToUnUsed(user.getHeadFileId());
		}
		int effCnt = userService.deleteUser(id);

		if (effCnt > 0) {
			return new Result(Result.RESULT_FLAG_SUCCESS, "success");
		} else {
			return new Result(Result.RESULT_FLAG_FAIL, "fail");
		}
	}

	/**
	 * 检查账户是否已经存在
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAccount", method = RequestMethod.GET)
	public Result getUserByAccount(@RequestParam("account") String account) {
		User user = userService.getUserByAccount(account);
		if (user != null) {
			return new Result(Result.RESULT_FLAG_FAIL, "该账户已经存在！！");
		} else {
			return new Result(Result.RESULT_FLAG_SUCCESS, "该账户可以注册！！");
		}
	}

	/**
	 * 更新用户头像
	 * 
	 * @param userId
	 * @param updateFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserHeadFile", method = RequestMethod.GET)
	public Result updateUserHeadFile(@RequestParam("userId") int userId, @RequestParam("updateFileId") int updateFileId,
			HttpSession session) {
		User user = userService.getUser(userId);
		user.setHeadFileId(updateFileId);
		user.setUpdateTime(new Date());
		int effCnt = userService.updateUser(user);
		if (effCnt > 0) {
			if (user.getHeadFileId() != null) {
				this.uploadFileService.updateFileToUsed(user.getHeadFileId());
			}
			if (super.getCurrentUser(session) != null) {
				session.setAttribute("user", user);
			}
			return new Result(Result.RESULT_FLAG_SUCCESS, "更新成功", user);
		} else {
			return new Result(Result.RESULT_FLAG_FAIL, "更新失败", user);
		}
	}

	/**
	 * 返回session user to json
	 * 
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSessionUser", method = RequestMethod.GET) // @RequestMapping 注解可以用指定的URL路径访问本控制层
	public Result getSessionUser(HttpSession session) throws IOException {
		User currentUser = super.getCurrentUser(session);
		if (currentUser != null) {
			return new Result(Result.RESULT_FLAG_SUCCESS, currentUser);
		} else {
			return new Result(Result.RESULT_FLAG_FAIL, "get session user failed");
		}
	}

	/**
	 * @param 会员登陆
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody User user, HttpSession session) {
		User userLogin = this.userService.login(user);
		if (userLogin != null) {
			userLogin.setLastLoginIp(getRequestIP());
			userLogin.setLastLoginTime(new Date());
			userLogin.setLoginCount(userLogin.getLoginCount() + 1);
			this.setCurrentUser(session, userLogin); // set login user to session.
			log.info("帐号:" + user.getAccount() + " 密码：" + user.getPassword() + " 登录成功");
			return new Result(Result.RESULT_FLAG_SUCCESS, "会员登录成功,欢迎您回来: " + userLogin.getUserName(), userLogin);
		} else {
			log.info("帐号:" + user.getAccount() + " 密码：" + user.getPassword() + " 登录失败");
			return new Result(Result.RESULT_FLAG_FAIL, "登录失败，帐号或密码有错");
		}
	}

	/**
	 * 会员登出
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Result logout(HttpSession session, SessionStatus sessionStatus) {
		// 清除所有session
		session.removeAttribute("user");
		sessionStatus.setComplete();
		return new Result(Result.RESULT_FLAG_SUCCESS, "登出成功");
	}

	/**
	 * 会员注册
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public Result reg(@RequestBody User user, HttpSession session) {
		String account = user.getAccount();
		if (CommonUtil.isStrEmpty(account)) {
			return new Result(Result.RESULT_FLAG_FAIL, "用户账户不能为空！！");
		} else {
			if (userService.getUserByAccount(account) != null) {
				return new Result(Result.RESULT_FLAG_FAIL, "该账户已经存在！！");
			} else {
				user.setUpdateTime(new Date());
				user.setRegTime(new Date());
				user.setLastLoginTime(new Date());
				user.setLoginCount(0);
				user.setLastLoginIp(getRequestIP());
				user.setIsEnabled((short) 1);
				int effCnt = userService.addUser(user);
				if (effCnt > 0) {
					this.setCurrentUser(session, user); // set login user to session.
					return new Result(Result.RESULT_FLAG_SUCCESS, "会员注册成功", user);
				} else {
					return new Result(Result.RESULT_FLAG_FAIL, "会员注册失败", user);
				}
			}
		}

	}

	@ResponseBody
	@RequestMapping(value = "/getProdFavList", method = RequestMethod.POST)
	public Result getProdFavList(@RequestBody DataTablesRequest dtRequest, HttpSession session) {
		User currentUser = super.getCurrentUser(session);
		if (currentUser == null) {
			return new Result(Result.RESULT_FLAG_FAIL, "会话失效，请重新登陆");
		}
		dtRequest.getCondition().put("userId", currentUser.getId());
		List<UserProdFav> favList = userPordFavService.getProdFavList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS, favList, userPordFavService.countAll(dtRequest),
				userPordFavService.countAll(dtRequest));
	}

	@ResponseBody
	@RequestMapping(value = "/getShopFavList", method = RequestMethod.POST)
	public Result getShopFavList(@RequestBody DataTablesRequest dtRequest, HttpSession session) {
		User currentUser = super.getCurrentUser(session);
		if (currentUser == null) {
			return new Result(Result.RESULT_FLAG_FAIL, "会话失效，请重新登陆");
		}
		dtRequest.getCondition().put("userId", currentUser.getId());
		List<UserShopFav> favList = userShopFavService.getShopFavList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS, favList, userShopFavService.countAll(dtRequest),
				userShopFavService.countAll(dtRequest));
	}

	@ResponseBody
	@RequestMapping(value = "/getWareFavList", method = RequestMethod.POST)
	public Result getWareFavList(@RequestBody DataTablesRequest dtRequest, HttpSession session) {
		User currentUser = super.getCurrentUser(session);
		if (currentUser == null) {
			return new Result(Result.RESULT_FLAG_FAIL, "会话失效，请重新登陆");
		}
		dtRequest.getCondition().put("userId", currentUser.getId());
		List<UserWareFav> favList = userWareFavService.getWareFavList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS, favList, userWareFavService.countAll(dtRequest),
				userWareFavService.countAll(dtRequest));
	}

	/**
	 * 找回密码
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/forgetPwd", method = RequestMethod.GET)
	public Result forgetPwd(@RequestParam("account") String account) {
		User user = userService.getUserByAccount(account);
		if (user == null) {
			return new Result(Result.RESULT_FLAG_FAIL, "请输入正确的账号！！！");
		}
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			// 设置发件人/收件人信息
			helper.setFrom(mailSender.getUsername());
			helper.setTo(user.getEmail());
			String content = " 您在PET的账号名为：" + user.getAccount() + " 您的密码是：" + user.getPassword()
					+ "， 请妥善保管，不要再忘记了哦!账号及密码，可通过PET首页登录及使用。感谢！";
			helper.setSubject("您的PET密码");
			helper.setText(content, true);
			mailSender.send(message);
			return new Result(Result.RESULT_FLAG_SUCCESS, "密码已经发送到" + user.getEmail() + "，请查收.");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Result.RESULT_FLAG_FAIL, "发送失败！！请联系系统管理员");
		}
	}

}
