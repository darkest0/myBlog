package blog.demo.service.impl;

import blog.demo.entity.TbBlogComment;
import blog.demo.dao.TbBlogCommentMapper;
import blog.demo.service.ITbBlogCommentService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.jsqlparser.statement.select.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
}
