package blog.demo.controller.admin;

import blog.demo.entity.TbLink;
import blog.demo.service.ITbLinkService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author  towa
 * 友链
 */
@Controller
@RequestMapping("/admin")
public class LinkController {
    @Resource
    private ITbLinkService linkService;

    @GetMapping("/links")
    public String linkPage(HttpServletRequest request){
        request.setAttribute("path","links");
        return "admin/link";
    }

    /**
     * 友链列表
     * @param params
     * @return
     */
    @GetMapping("/links/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("参数异常!");
        }
        PageQueryUtil pageQueryUtil =new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(linkService.getBlogLinkPage(pageQueryUtil));
    }

    /**
     * 友链添加
     * @param linkType
     * @param linkName
     * @param linkUrl
     * @param linkRank
     * @param linkDescription
     * @return
     */
    @PostMapping("/links/save")
    @ResponseBody
    public Result save(@RequestParam("linkType") Integer linkType,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription){
        if (linkType==null||linkType<0|linkRank<0||StringUtils.isEmpty(linkName)||StringUtils.isEmpty(linkUrl)||StringUtils.isEmpty(linkDescription)){
            return ResultGenerator.genFailResult("参数异常");
        }
        TbLink link =new TbLink();
        link.setLinkType(linkType);
        link.setLinkRank(linkRank);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.save(link));
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/links/info/{id}")
    @ResponseBody
    public Result info(@RequestParam("id") Integer id){
        TbLink link = linkService.getById(id);
        return ResultGenerator.genSuccessResult(link);
    }

    /**
     * 友链修改
     * @param linkId
     * @param linkType
     * @param linkName
     * @param linkUrl
     * @param linkRank
     * @param linkDescription
     * @return
     */
    @PostMapping("/links/update")
    @ResponseBody
    public Result update(@RequestParam("linkId") Integer linkId,
                         @RequestParam("linkType") Integer linkType,
                         @RequestParam("linkName") String linkName,
                         @RequestParam("linkUrl") String linkUrl,
                         @RequestParam("linkRank") Integer linkRank,
                         @RequestParam("linkDescription") String linkDescription) {
     TbLink link =linkService.getById(linkId);
     if (link == null){
         return ResultGenerator.genFailResult("无数据！");
     }
     link.setLinkType(linkType);
     link.setLinkRank(linkRank);
     link.setLinkUrl(linkUrl);
     link.setLinkDescription(linkDescription);
     return  ResultGenerator.genSuccessResult(linkService.updateById(link));
    }

    @PostMapping("/links/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常");
        }
        List<Integer> idList = Arrays.asList(ids);
        if (linkService.removeByIds(idList)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("删除失败！");
        }
    }
}
