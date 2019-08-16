package blog.demo.service.impl;

import blog.demo.dao.TbBlogTagRelationMapper;
import blog.demo.entity.TbBlogTag;
import blog.demo.dao.TbBlogTagMapper;
import blog.demo.entity.TbBlogTagCount;
import blog.demo.entity.TbBlogTagRelation;
import blog.demo.service.ITbBlogTagService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    TbBlogTagRelationMapper relationMapper;

    @Override
    public List<TbBlogTagCount> getBlogTagCountForIndex() {
        return blogTagMapper.getTagCount();
    }

    @Override
    public int getTotalTag() {
        QueryWrapper<TbBlogTag> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        int total = blogTagMapper.selectCount(queryWrapper);
        return  total;
    }

    @Override
    public PageResult getTagPage(PageQueryUtil pageQueryUtil) {
        List<TbBlogTag> tags =blogTagMapper.selectTagPage(pageQueryUtil);
        QueryWrapper<TbBlogTag> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        int total =blogTagMapper.selectCount(queryWrapper);
        PageResult pageResult =new PageResult(tags,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean saveTag(String tagName) {
        QueryWrapper<TbBlogTag> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("tag_name",tagName).eq("is_deleted",0);
        TbBlogTag tag =blogTagMapper.selectOne(queryWrapper);
        if (tag==null){
            TbBlogTag blogTag =new TbBlogTag();
            blogTag.setTagName(tagName);
            return blogTagMapper.insert(blogTag)>0;
        }
        return false;
    }

    @Override
    public Boolean deleteTag(List<Integer> ids) {
        QueryWrapper<TbBlogTagRelation> queryWrapper =new QueryWrapper();
        queryWrapper.in("tag_id",ids);
        List<TbBlogTagRelation> relations = relationMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(relations)){
            return false;
        }
        return blogTagMapper.deleteBatchIds(ids)>0;
    }

}
