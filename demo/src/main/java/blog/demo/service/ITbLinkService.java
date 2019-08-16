package blog.demo.service;

import blog.demo.controller.vo.BlogListVO;
import blog.demo.entity.TbLink;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface ITbLinkService extends IService<TbLink> {
    /**
     * 获取友链所需的信息
     * @return
     */
    Map<Integer,List<TbLink>> getLink();

    /**
     * 获取友链数量
     */
    int getTotalLink();

    /**
     * 友链的分页查询
     */
    PageResult getBlogLinkPage(PageQueryUtil pageQueryUtil);

}
