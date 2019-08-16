package blog.demo.dao;

import blog.demo.entity.TbLink;
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
public interface TbLinkMapper extends BaseMapper<TbLink> {
    /**
     * 分页查询
     * @param pageQueryUtil
     * @return
     */
    List<TbLink> findLinkList(PageQueryUtil pageQueryUtil);

    /**
     * 友链总数量
     * @param pageQueryUtil
     * @return
     */
    int getTotalLinks(PageQueryUtil pageQueryUtil);

}
