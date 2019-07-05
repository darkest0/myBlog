package blog.demo.dao;

import blog.demo.entity.TbBlog;
import blog.demo.util.PageQueryUtil;
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
public interface TbBlogMapper extends BaseMapper<TbBlog> {

    List<TbBlog> selectByDiy(@Param("ew") QueryWrapper queryWrapper, @Param("limit") int limit);

    //List<TbBlog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);

    /**
     * 通过标签获取文章列表
     */
    List<TbBlog> getBlogsPageByTagId(PageQueryUtil map);

    /**
     * 通过分类
     * 搜索
     * 获取文章列表
     */
    List<TbBlog> getBlogPageByXX(PageQueryUtil map);

    /**
     * 获取 分类
     * 搜索 文章的总数量
     */
    int getBlogTotal(PageQueryUtil map);

}
