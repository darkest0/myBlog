package blog.demo.service;

import blog.demo.entity.TbBlogTag;
import blog.demo.entity.TbBlogTagCount;
import blog.demo.entity.TbBlogTagRelation;
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
public interface ITbBlogTagService extends IService<TbBlogTag> {
    /**
     * 查看首页标签
     * @return
     */
    List<TbBlogTagCount> getBlogTagCountForIndex();

    /**
     * 获取标签数量
     */
    int getTotalTag();
}
