package blog.demo.dao;

import blog.demo.entity.TbBlogCategory;
import blog.demo.util.PageQueryUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface TbBlogCategoryMapper extends BaseMapper<TbBlogCategory> {
    /**
     * 获取标签分页
     * @param pageQueryUtil
     * @return
     */
    List<TbBlogCategory> findCategoryList(PageQueryUtil pageQueryUtil);

}
