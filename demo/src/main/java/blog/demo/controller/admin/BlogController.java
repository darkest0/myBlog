package blog.demo.controller.admin;

import blog.demo.config.Constant;
import blog.demo.controller.vo.BlogDetailVO;
import blog.demo.entity.TbBlog;
import blog.demo.entity.TbBlogCategory;
import blog.demo.service.ITbBlogCategoryService;
import blog.demo.service.ITbBlogService;
import blog.demo.service.ITbConfigService;
import blog.demo.util.MyBlogUtils;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @author towa
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Resource
    private ITbBlogService blogService;
    @Resource
    private ITbBlogCategoryService categoryService;


    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(blogService.getBlogsPage(pageQueryUtil));
    }

    @GetMapping("/blogs")
    public String list(HttpServletRequest request){
        request.setAttribute("path","blogs");
        return "admin/blog";
    }

    /**
     * 写博客，使用edit
     * @param request
     * @return
     */
    @GetMapping("/blogs/edit")
    public String edit(HttpServletRequest request){
        request.setAttribute("path","edit");
        request.setAttribute("categories",categoryService.getAllCategory());
        return "admin/edit";
    }

    /**
     * id获取文章
     * @param request
     * @param blogId
     * @return
     */
    @GetMapping("/blogs/edit/{blogId}")
    public String edit(HttpServletRequest request, @PathVariable("blogId") Long blogId){
        request.setAttribute("path","edit");
        TbBlog blog = blogService.getBlogById(blogId);
        if (blog == null){
            return "error/error_400";
        }
        request.setAttribute("blog",blog);
        request.setAttribute("categories",categoryService.getAllCategory());
        return "admin/edit";
    }

    /**
     * 保存文章
     * @param blogTitle
     * @param blogSubUrl
     * @param blogCategoryId
     * @param blogTags
     * @param blogContent
     * @param blogCoverImage
     * @param blogStatus
     * @param enableComment
     * @return
     */
    @PostMapping("blogs/save")
    @ResponseBody
    public Result save(@RequestParam("blogTitle") String blogTitle,
                       @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
                       @RequestParam("blogCategoryId") Integer blogCategoryId,
                       @RequestParam("blogTags") String blogTags,
                       @RequestParam("blogContent") String blogContent,
                       @RequestParam("blogCoverImage") String blogCoverImage,
                       @RequestParam("blogStatus") Byte blogStatus,
                       @RequestParam("enableComment") Byte enableComment) {
        if (StringUtils.isEmpty(blogTitle)){
            return ResultGenerator.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length()>150){
            return ResultGenerator.genFailResult("标题过长");
        }
        if (StringUtils.isEmpty(blogTags)){
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length()>150){
            return ResultGenerator.genFailResult("标签过长");
        }
        if (blogSubUrl.trim().length()>150){
            return ResultGenerator.genFailResult("路径过长");
        }
        if (StringUtils.isEmpty(blogContent)){
            return ResultGenerator.genFailResult("请输入文章内容");
        }
        if (blogContent.trim().length() > 100000){
            return ResultGenerator.genFailResult("文章内容过长");
        }
        if (StringUtils.isEmpty(blogCoverImage)){
            return ResultGenerator.genFailResult("封面图不能为空");
        }
        TbBlog blog =new TbBlog();
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus.intValue());
        blog.setEnableComment(enableComment.intValue());
        String saveBlog=blogService.saveBlog(blog);
        if ("success".equals(saveBlog)){
            return ResultGenerator.genSuccessResult("添加成功");
        }else {
            return ResultGenerator.genFailResult(saveBlog);
        }
    }

    /**
     * 文章修改
     * @param blogId
     * @param blogTitle
     * @param blogSubUrl
     * @param blogCategoryId
     * @param blogTags
     * @param blogContent
     * @param blogCoverImage
     * @param blogStatus
     * @param enableComment
     * @return
     */
    @PostMapping("/blogs/update")
    @ResponseBody
    public  Result update(@RequestParam("blogId") Long blogId,
                          @RequestParam("blogTitle") String blogTitle,
                          @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
                          @RequestParam("blogCategoryId") Integer blogCategoryId,
                          @RequestParam("blogTags") String blogTags,
                          @RequestParam("blogContent") String blogContent,
                          @RequestParam("blogCoverImage") String blogCoverImage,
                          @RequestParam("blogStatus") Byte blogStatus,
                          @RequestParam("enableComment") Byte enableComment){
        if (StringUtils.isEmpty(blogTitle)){
            return ResultGenerator.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length()>150){
            return ResultGenerator.genFailResult("标题过长");
        }
        if (StringUtils.isEmpty(blogTags)){
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length()>150){
            return ResultGenerator.genFailResult("标签过长");
        }
        if (StringUtils.isEmpty(blogContent)){
            return ResultGenerator.genFailResult("请输入文章内容");
        }
        if (blogContent.trim().length()>100000){
            return ResultGenerator.genFailResult("文章内容过长");
        }
        if (StringUtils.isEmpty(blogCoverImage)){
            return ResultGenerator.genFailResult("封面图不能为空");
        }
        TbBlog blog = new TbBlog();
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus.intValue());
        blog.setEnableComment(enableComment.intValue());
        String updateBlog =blogService.updateBlog(blog);
        if ("success".equals(updateBlog)){
            return ResultGenerator.genSuccessResult("修改成功");
        }else {
            return ResultGenerator.genFailResult(updateBlog);
        }
    }

    @PostMapping("/blogs/md/uploadfile")
    public void uploadFileEditormd(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(name = "editormd-image-file", required = true)
                                           MultipartFile file) throws URISyntaxException, IOException {
        String fileName =file.getOriginalFilename();
        String suffixName =fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyMMdd_HHmmss");
        Random r= new Random();
        StringBuilder tempName =new StringBuilder();
        tempName.append(simpleDateFormat.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        //创建文件
        File destFile =new File(Constant.FILE_UPLOAD_DIC+newFileName);
        String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL()+""))+"/upload/"+newFileName;
        File fileDirectory =new File(Constant.FILE_UPLOAD_DIC);
        try {
            if (!fileDirectory.exists()){
                if (fileDirectory.mkdirs()){
                    throw new IOException("文件夹创建失败，路径为"+fileDirectory);
                }
            }
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            response.getWriter().write("{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}");
        } catch (UnsupportedEncodingException e) {
            response.getWriter().write("{\"success\":0}");
        } catch (IOException e) {
            response.getWriter().write("{\"success\":0}");
        }
    }
        @PostMapping("/blogs/delete")
        @ResponseBody
        public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (blogService.deleteBath(ids)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}