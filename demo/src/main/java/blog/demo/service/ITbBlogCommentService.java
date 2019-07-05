package blog.demo.service;

import blog.demo.entity.TbBlogComment;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface ITbBlogCommentService extends IService<TbBlogComment> {
    /**
     * 根据文章Id与分页参数获取文章评论列表
     * @param blogId
     * @param page
     * @return
     */
    PageResult getCommentPageByBlogIdAndPageNum(Long blogId,int page);

}
