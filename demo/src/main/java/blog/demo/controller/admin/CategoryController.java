package blog.demo.controller.admin;

import blog.demo.entity.TbBlogCategory;
import blog.demo.service.ITbBlogCategoryService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import com.alibaba.druid.sql.visitor.functions.If;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author towa
 */
@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Resource
    private ITbBlogCategoryService categoryService;

    @GetMapping("/categories")
    public String categoryPage(HttpServletRequest request){
        request.setAttribute("path","categories");
        return "admin/category";
    }

    /**
     * 分类列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/categories/list" ,method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam  Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageQueryUtil =new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(categoryService.getBlogCategoryPage(pageQueryUtil));
    }

    /**
     * 分类添加
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    @PostMapping("/categories/save")
    @ResponseBody
    public Result save(@RequestParam("categoryName") String categoryName,
                       @RequestParam("categoryIcon") String categoryIcon){
        if (StringUtils.isEmpty(categoryName)||StringUtils.isEmpty(categoryIcon)){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (categoryService.saveCategory(categoryName,categoryIcon)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("分类名称重复");
        }
    }

    /**
     * 分类修改
     * @param categoryId
     * @param categoryName
     * @param categoryIcno
     * @return
     */
    @PostMapping("/categories/update")
    @ResponseBody
    public Result update(@RequestParam("categoryId") Integer categoryId,
                         @RequestParam("categoryName") String categoryName,
                         @RequestParam("categoryIcon") String categoryIcno){
        if (StringUtils.isEmpty(categoryName)||StringUtils.isEmpty(categoryIcno)){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (categoryService.updateCategory(categoryId,categoryName,categoryIcno)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("分类名称重复");
        }
    }

    @PostMapping("/categories/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (ids.length<1){
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (categoryService.deleteBatch(ids)){
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
