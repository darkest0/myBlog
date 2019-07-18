package blog.demo.service.impl;

import blog.demo.dao.TbLinkMapper;
import blog.demo.entity.TbBlog;
import blog.demo.entity.TbBlogComment;
import blog.demo.dao.TbBlogCommentMapper;
import blog.demo.entity.TbLink;
import blog.demo.service.ITbBlogCommentService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.jsqlparser.statement.select.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
@Service
public class TbBlogCommentServiceImpl extends ServiceImpl<TbBlogCommentMapper, TbBlogComment> implements ITbBlogCommentService {

    @Autowired
    TbBlogCommentMapper blogcommentMapper;

    @Override
    public PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page) {
        if (page<1){
            return null;
        }
        Map param =new HashMap();
        param.put("page",page);
        param.put("limit",8);
        param.put("blogId", blogId);
        param.put("commentStatus", 1);//过滤审核通过的数据
        PageQueryUtil pageQueryUtil =new PageQueryUtil(param);
        List<TbBlogComment> comments = blogcommentMapper.getTotalBlogCommentDiy(pageQueryUtil);
        if (!CollectionUtils.isEmpty(comments)){
            QueryWrapper<TbBlogComment> queryWrapper2 =new QueryWrapper<>();
            queryWrapper2.eq("blog_id",blogId)
                    .eq("comment_status",1)
                    .eq("is_deleted",0);
            int total =blogcommentMapper.selectCount(queryWrapper2);
            PageResult pageResult =new PageResult(comments,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
            return pageResult;
        }
        return null;
    }

    @Override
    public int getTotalComment() {
        QueryWrapper<TbBlogComment> queryWrapper =new QueryWrapper();
        queryWrapper.eq("is_deleted",0);
        int total = blogcommentMapper.selectCount(queryWrapper);
        return total;
    }

    @Override
    public PageResult getCompentPage(PageQueryUtil pageQueryUtil) {
        List<TbBlogComment> comments = blogcommentMapper.findBlogCommentList(pageQueryUtil);
        int total =blogcommentMapper.getTotalComment(pageQueryUtil);
        PageResult pageResult =new PageResult(comments,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean checkDone(Integer[] ids) {
        TbBlogComment comment =new TbBlogComment();
        comment.setCommentStatus(1);
        QueryWrapper<TbBlogComment> queryWrapper =new QueryWrapper<>();
        queryWrapper.in("comment_id",ids).eq("comment_status",0);
        return blogcommentMapper.update(comment,queryWrapper)>0;
    }

    @Override
    public Boolean reply(Long commentId, String replyBody) {
        TbBlogComment blogComment = blogcommentMapper.selectById(commentId);
        //blogComment不为空且状态为已审核，则继续后续操作
        if (blogComment!=null && blogComment.getCommentStatus().intValue()==1){
            blogComment.setReplyBody(replyBody);
            blogComment.setReplyCreateTime(new Date());
            return blogcommentMapper.updateById(blogComment)>0;
        }
        return false;
    }

    @Override
    public Boolean deleteBath(Integer[] ids) {
        TbBlogComment comment =new TbBlogComment();
        comment.setIsDeleted(1);
        QueryWrapper<TbBlogComment> queryWrapper =new QueryWrapper();
        queryWrapper.in("comment_id",ids);
        return blogcommentMapper.update(comment,queryWrapper)>0;
    }
}
