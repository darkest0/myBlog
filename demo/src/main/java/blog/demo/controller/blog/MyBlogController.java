package blog.demo.controller.blog;

import blog.demo.Input.CommentInput;
import blog.demo.controller.vo.BlogDetailVO;
import blog.demo.entity.TbBlog;
import blog.demo.entity.TbBlogCategory;
import blog.demo.entity.TbBlogComment;
import blog.demo.entity.TbLink;
import blog.demo.service.ITbBlogCategoryService;
import blog.demo.service.ITbBlogCommentService;
import blog.demo.service.ITbBlogService;
import blog.demo.service.ITbBlogTagService;
import blog.demo.service.ITbConfigService;
import blog.demo.service.ITbLinkService;
import blog.demo.service.impl.TbLinkServiceImpl;
import blog.demo.util.MyBlogUtils;
import blog.demo.util.PageResult;
import blog.demo.util.PatternUtil;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.chrono.IsoEra;
import java.util.List;
import java.util.Map;

/**
 * @author towa
 */
@Controller
public class MyBlogController {
    /**
     * 主题选择
     */
    public static String theme = "default";
    //public static String theme = "yummy-jekyll";
    //public static String theme = "amaze";

    @Autowired
    ITbBlogService blogService;
    @Autowired
    ITbBlogTagService tagService;
    @Autowired
    ITbConfigService configService;
    @Autowired
    ITbBlogCategoryService categoryService;
    @Autowired
    ITbBlogCommentService commentService;
    @Autowired
    ITbLinkService linkService;


    /**
     * 首页
     */

    @GetMapping(value = {"/","/index","index.html"})
    public String index(HttpServletRequest request){
        return this.page(request,1);
    }

    /**
     * 首页 分页数据
     */
    @GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request,@PathVariable("pageNum")int pageNum){
        //获取首页文章
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if(blogPageResult==null){
            return "error/error_404";
        }
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", blogService.getBlogListIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("pageName", "首页");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/"+theme+"/index";
    }

    /**
     * Categories页面（包括分类数据和标签数据）
     */
    @GetMapping({"/categories"})
    public String categories(HttpServletRequest request){
        request.setAttribute("hotTags",tagService.getBlogTagCountForIndex());
        request.setAttribute("categories",categoryService.getAllCategory());
        request.setAttribute("pageName","分类页面");
        request.setAttribute("configurations",configService.getAllConfigs());
        return "blog/"+theme+"/category";
    }

    /**
     * 详情页
     */
    @GetMapping({"/blog/{blogId}","/article/{blogId}"})
    public String detail(HttpServletRequest request,@PathVariable("blogId") Long blogId,
                         @RequestParam(value = "commentPage",required = false,defaultValue = "1") Integer commentPage){
        BlogDetailVO blogDetailVO =blogService.getBlogDetail(blogId);
        if (blogDetailVO!=null){
            //文章
            request.setAttribute("blogDetailVO",blogDetailVO);
            //评论
            request.setAttribute("commentPageResult",commentService.getCommentPageByBlogIdAndPageNum(blogId,commentPage));
        }
        request.setAttribute("pageName","详情");
        request.setAttribute("configurations",configService.getAllConfigs());
        return "blog/"+theme+"/detail";
    }

    /**
     * 标签列表
     */
    @GetMapping({"/tag/{tagName}"})
    public String tag(HttpServletRequest request,@PathVariable("tagName") String tagName){
        return this.tag(request,tagName,1);
    }

    /**
     * 标签列表
     */
    @GetMapping({"/tag/{tagName}/{page}"})
    public String tag(HttpServletRequest request,@PathVariable("tagName") String tagName,@PathVariable("page") Integer page){
        PageResult blogPageResult =blogService.getBlogPageByTag(tagName,page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagName);
        request.setAttribute("newBlogs", blogService.getBlogListIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/"+theme+"/list";
    }

    /**
     * 分类列表页面
     */
    @GetMapping({"/category/{categoryName}"})
    public String category(HttpServletRequest request,@PathVariable String categoryName){
        return this.category(request,categoryName,1);
    }

    /**
     * 分类列表页
     */
    @GetMapping({"/category/{categoryName}/{page}"})
    public String category(HttpServletRequest request,@PathVariable String categoryName,@PathVariable Integer page){
        PageResult blogPageResult = blogService.getBlogPageByCategory(categoryName,page);
        request.setAttribute("blogPageResult",blogPageResult);
        request.setAttribute("pageName","分类");
        request.setAttribute("pageUrl","category");
        request.setAttribute("keyword",categoryName);
        request.setAttribute("newBlogs", blogService.getBlogListIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 搜索列表页
     */
    @GetMapping({"/search/{keyword}"})
    public String search(HttpServletRequest request,@PathVariable String keyword){
        return this.search(request,keyword,1);
    }

    /**
     * 搜索列表页
     */

    public String search(HttpServletRequest request,@PathVariable String keyword,@PathVariable Integer page){
        PageResult blogPageResult = blogService.getBlogPageBySearch(keyword,page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        request.setAttribute("newBlogs", blogService.getBlogListIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 获取友联
     */
    @GetMapping({"/link"})
    public String link(HttpServletRequest request){
        request.setAttribute("pageName","友情链接");
        Map<Integer, List<TbLink>> linkMap = linkService.getLink();
        if (linkMap!=null){
            //  判断友链类型
            if(linkMap.containsKey(0)){
                request.setAttribute("favoriteLinks",linkMap.get(0));
            }
            if (linkMap.containsKey(1)){
                request.setAttribute("recommendLinks",linkMap.get(1));
            }
            if (linkMap.containsKey(2)){
                request.setAttribute("personalLinks",linkMap.get(2));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/link";
    }


    @PostMapping("/blog/comment")
    public Result comment(HttpServletRequest request, HttpSession session, @RequestBody CommentInput input){
        //校验验证码
        if (StringUtils.isEmpty(input.getVerifyCode())){
            return ResultGenerator.genFailResult("验证码不能为空");
        }
        String checkCode =session.getAttribute("verifyCode")+"";
        if (StringUtils.isEmpty(checkCode)){
            return  ResultGenerator.genFailResult("非法请求");
        }
        if(!input.getVerifyCode().equals(checkCode)){
            return  ResultGenerator.genFailResult("验证码错误");
        }
        String ref =request.getHeader("Referer");
        if (StringUtils.isEmpty(ref)){
            return ResultGenerator.genFailResult("非法请求");
        }
        if (input.getBlogId()==null && input.getBlogId()<0){
            return ResultGenerator.genFailResult("非法请求");
        }
        if (StringUtils.isEmpty(input.getNick())){
            return ResultGenerator.genFailResult("请输入昵称");
        }
        if (StringUtils.isEmpty(input.getEmail())){
            return ResultGenerator.genFailResult("请输入邮箱地址");
        }
        if (!PatternUtil.isEmail(input.getEmail())){
            return ResultGenerator.genFailResult("请输入正确的邮箱地址");
        }
        if (StringUtils.isEmpty(input.getCommentBody())){
            return ResultGenerator.genFailResult("请输入评论");
        }
        if (input.getCommentBody().trim().length()>200){
            return ResultGenerator.genFailResult("评论过长");
        }
        TbBlogComment comment =new TbBlogComment();
        comment.setBlogId(input.getBlogId());
        comment.setCommentator(MyBlogUtils.cleanString(input.getNick()));
        comment.setEmail(input.getEmail());
        if (PatternUtil.isURL(input.getWebUrl())){
            comment.setWebsiteUrl(input.getWebUrl());
        }
        comment.setCommentBody(MyBlogUtils.cleanString(input.getCommentBody()));
        return ResultGenerator.genSuccessResult(commentService.save(comment));
    }

    /**
     * 关于页面 以及其他配置了subUrl的文章页
     */
    @GetMapping({"/{subUrl}"})
    public String detail(HttpServletRequest request, @PathVariable String subUrl){
        BlogDetailVO blogDetailVO = blogService.getBlogDetailByStrUrl(subUrl);
        if (blogDetailVO!=null){
            request.setAttribute("blogDetailVO",blogDetailVO);
            request.setAttribute("pageName",subUrl);
            request.setAttribute("configurations",configService.getAllConfigs());
            return "blog/"+theme+"/detail";
        }
            return "error/error_400";
    }
}
