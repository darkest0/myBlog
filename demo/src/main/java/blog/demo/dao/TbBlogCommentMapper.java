package blog.demo.dao;

import blog.demo.entity.TbBlogComment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface TbBlogCommentMapper extends BaseMapper<TbBlogComment> {
    List<TbBlogComment> getTotalBlogCommentDiy(Map map);

    /**
     * 获取评论
     * @param map
     * @return
     */
    List<TbBlogComment> findBlogCommentList(Map map);

    int getTotalComment(Map map);

}
