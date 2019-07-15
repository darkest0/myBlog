package blog.demo.controller.admin;

import blog.demo.Input.LoginInput;
import blog.demo.entity.TbAdminUser;
import blog.demo.service.ITbAdminUserService;
import blog.demo.service.ITbBlogCategoryService;
import blog.demo.service.ITbBlogCommentService;
import blog.demo.service.ITbBlogService;
import blog.demo.service.ITbBlogTagService;
import blog.demo.service.ITbLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author towa
 * 登陆 及 admin 首页
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    ITbAdminUserService adminUserService;
    @Autowired
    ITbBlogCategoryService categoryService;
    @Autowired
    ITbBlogCommentService  commentService;
    @Autowired
    ITbBlogTagService tagService;
    @Autowired
    ITbBlogService blogService;
    @Autowired
    ITbLinkService linkService;

    @GetMapping({"/login"})
    public String login(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login( LoginInput input, HttpSession session){
        if (StringUtils.isEmpty(input.getVerifyCode())){
            session.setAttribute("errorMsg","验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(input.getUserName())||StringUtils.isEmpty(input.getPassword())){
            session.setAttribute("errorMsg","用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode")+"";
        if (StringUtils.isEmpty(kaptchaCode)||!input.getVerifyCode().equals(kaptchaCode)){
            session.setAttribute("errorMsg","验证码错误");
            return "admin/login";
        }
        TbAdminUser adminUser = adminUserService.login(input.getUserName(),input.getPassword());
        if (adminUser!=null){
            session.setAttribute("loginUser",adminUser.getNickName());
            session.setAttribute("loginUserId",adminUser.getAdminUserId());
            return "redirect:/admin/index";
        }else {
            session.setAttribute("errorMsg","登陆失败！");
            return "admin/login";
        }
    }

    @GetMapping({"","/","/index","index.html"})
    public String index(HttpServletRequest request){
        request.setAttribute("path","index");
        request.setAttribute("categoryCount",categoryService.getTotalCategory());
        request.setAttribute("blogCount",blogService.getTotalBlog());
        request.setAttribute("linkCount",linkService.getTotalLink());
        request.setAttribute("tagCount",tagService.getTotalTag());
        request.setAttribute("commentCount",commentService.getTotalComment());
        request.setAttribute("path","index");
        return "admin/index";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request){
        Integer loginUserId = (Integer) request.getSession().getAttribute("loginUserId");
        TbAdminUser adminUser = adminUserService.getById(loginUserId);
        if (adminUser ==null){
            return "admin/login";
        }
        request.setAttribute("path","profile");
        request.setAttribute("loginUserName",adminUser.getLoginUserName());
        request.setAttribute("nickName",adminUser.getNickName());
        return "admin/profile";
    }
}
