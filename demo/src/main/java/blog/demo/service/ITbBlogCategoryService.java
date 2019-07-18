package blog.demo.service;

import blog.demo.entity.TbBlogCategory;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface ITbBlogCategoryService extends IService<TbBlogCategory> {

    /**
     * 获取 分类的列表
     */
    List<TbBlogCategory> getAllCategory();

    /**
     * 获取 分类的数量
     */
    int getTotalCategory();

    /**
     * 获取分类分页
     * @param pageQueryUtil
     * @return
     */
    PageResult getBlogCategoryPage(PageQueryUtil pageQueryUtil);


    /**
     * 添加分类
     * @param categoryName
     * @param categoryIcon
     * @return
     */
     Boolean saveCategory(String categoryName,String categoryIcon);

    /**
     * 修改分类
     * @param categoryId
     * @param categoryName
     * @param categoryIcon
     * @return
     */
     Boolean updateCategory(Integer categoryId,String categoryName,String categoryIcon);

    /**
     * 删除数据
     * @param ids
     * @return
     */
     Boolean deleteBatch(Integer[] ids);
}
