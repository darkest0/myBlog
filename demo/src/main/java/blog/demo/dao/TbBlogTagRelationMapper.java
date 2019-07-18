package blog.demo.dao;

import blog.demo.entity.TbBlogTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface TbBlogTagRelationMapper extends BaseMapper<TbBlogTagRelation> {
    /**
     * 新增关系
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<TbBlogTagRelation> list);

}
