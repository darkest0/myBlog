package blog.demo.controller.admin;

import blog.demo.entity.TbBlogTag;
import blog.demo.service.ITbBlogTagService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import com.baomidou.mybatisplus.extension.api.R;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author  towa
 * 标签
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Resource
    private ITbBlogTagService tagService;

    @GetMapping("/tags")
    public String tagPage(HttpServletRequest request){
        request.setAttribute("path","tags");
        return "admin/tag";
    }

    @GetMapping("/tags/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("参数异常");
        }
        PageQueryUtil pageQueryUtil =new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(tagService.getTagPage(pageQueryUtil));
    }

    @PostMapping("/tags/save")
    @ResponseBody
    public Result save(@RequestParam("tagName") String tagName){
        if (StringUtils.isEmpty(tagName)){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (tagService.saveTag(tagName)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("标签重复");
        }
    }

    @PostMapping("/tags/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常！");
        }
        List<Integer> idList = Arrays.asList(ids);
        if (tagService.deleteTag(idList)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("存在关联数据请谨慎对待！");
        }
    }
}
