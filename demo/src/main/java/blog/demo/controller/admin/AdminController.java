package blog.demo.controller.admin;

import blog.demo.Input.LoginInput;
import blog.demo.entity.TbAdminUser;
import blog.demo.service.ITbAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

/**
 * @author towa
 * 登陆 及 admin 首页
 */
public class AdminController {

    @Autowired
    ITbAdminUserService adminUserService;

    @GetMapping({"/login"})
    public String login(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginInput input, HttpSession session){
        if (StringUtils.isEmpty(input.getVerifyCode())){
            session.setAttribute("errorMsg","验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(input.getUserName())||StringUtils.isEmpty(input.getPassWord())){
            session.setAttribute("errorMsg","用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode")+"";
        if (StringUtils.isEmpty(kaptchaCode)||!input.getVerifyCode().equals(kaptchaCode)){
            session.setAttribute("errorMsg","验证码错误");
            return "admin/login";
        }
        TbAdminUser adminUser = adminUserService.login(input.getUserName(),input.getPassWord());
        if (adminUser!=null){
            session.setAttribute("loginUser",adminUser.getNickName());
            session.setAttribute("loginUserId",adminUser.getAdminUserId());
            return "redirect:/admin/index";
        }else {
            session.setAttribute("errorMsg","登陆失败！");
            return "admin/login";
        }

    }
}
