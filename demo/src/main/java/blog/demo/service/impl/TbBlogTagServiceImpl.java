package blog.demo.service.impl;

import blog.demo.entity.TbBlogTag;
import blog.demo.dao.TbBlogTagMapper;
import blog.demo.entity.TbBlogTagCount;
import blog.demo.entity.TbBlogTagRelation;
import blog.demo.service.ITbBlogTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
@Service
public class TbBlogTagServiceImpl extends ServiceImpl<TbBlogTagMapper, TbBlogTag> implements ITbBlogTagService {

    @Autowired
    TbBlogTagMapper blogTagMapper;

    @Override
    public List<TbBlogTagCount> getBlogTagCountForIndex() {
        return blogTagMapper.getTagCount();
    }
}
