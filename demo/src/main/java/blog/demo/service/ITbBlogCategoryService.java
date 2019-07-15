package blog.demo.service;

import blog.demo.entity.TbBlogCategory;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
