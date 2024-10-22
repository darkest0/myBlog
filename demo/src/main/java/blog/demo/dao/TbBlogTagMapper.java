package blog.demo.dao;

import blog.demo.entity.TbBlogTag;
import blog.demo.entity.TbBlogTagCount;
import blog.demo.entity.TbBlogTagRelation;
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
public interface TbBlogTagMapper extends BaseMapper<TbBlogTag> {
    /**
     * 查看标签
     * @return
     */
   List<TbBlogTagCount> getTagCount();

   int batchInsertBlogTag(List<TbBlogTag> tagList);

    /**
     * 标签分页
     * @param pageQueryUtil
     * @returnc
     */
    List<TbBlogTag> selectTagPage(PageQueryUtil pageQueryUtil);

}
