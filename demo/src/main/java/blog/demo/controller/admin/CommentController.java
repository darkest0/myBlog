package blog.demo.controller.admin;

import blog.demo.service.ITbBlogCommentService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author towa
 * 评论管理
 */
@Controller
@RequestMapping("/admin")
public class CommentController {
    @Resource
    private ITbBlogCommentService commentService;

    @GetMapping("/comments")
    public String list(HttpServletRequest request){
        request.setAttribute("path","comments");
        return "admin/comment";
    }

    /**
     * 评论列表
     * @param params
     * @return
     */
    @GetMapping("/comments/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageQueryUtil =new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(commentService.getCompentPage(pageQueryUtil));
    }

    @PostMapping("/comments/checkDone")
    public Result checkDone(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (commentService.checkDone(ids)){
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult("审核失败");
        }
    }

    /**
     * 回复
     * @param commentId
     * @param replyBody
     * @return
     */
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result reply(@RequestParam("commentId")Long commentId,
                        @RequestParam("replyBody")String replyBody){
        if (commentId==null||commentId<1||StringUtils.isEmpty(replyBody)){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (commentService.reply(commentId,replyBody)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("回复失败");
        }
    }

    @PostMapping("/comments/delete")
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length <1){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (commentService.deleteBath(ids)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("刪除失败");
        }
    }
}
