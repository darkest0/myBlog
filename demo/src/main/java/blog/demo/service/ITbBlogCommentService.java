package blog.demo.service;

import blog.demo.entity.TbBlogComment;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * 获取评论的数量
     */
    int getTotalComment();

    /**
     * 获取评论
     * @param pageQueryUtil
     * @return
     */
    PageResult getCompentPage(PageQueryUtil pageQueryUtil);

    /**
     * 批量审核
     * @param ids
     * @return
     */
    Boolean checkDone(Integer[] ids);

    /**
     * 回复
     * @param commentId
     * @param replyBody
     * @return
     */
    Boolean reply(Long commentId,String replyBody);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean deleteBath(Integer[] ids);
}
