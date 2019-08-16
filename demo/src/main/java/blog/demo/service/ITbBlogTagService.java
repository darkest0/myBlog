package blog.demo.service;

import blog.demo.entity.TbBlogTag;
import blog.demo.entity.TbBlogTagCount;
import blog.demo.entity.TbBlogTagRelation;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.alibaba.druid.sql.PagerUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     *查询标签的分页数据
     * @param pageQueryUtil
     * @return
     */
    PageResult getTagPage(PageQueryUtil pageQueryUtil);

    /**
     * 保存标签
     * @param tagName
     * @return
     */
    Boolean saveTag(String tagName);

    Boolean deleteTag (List<Integer> ids);
}
