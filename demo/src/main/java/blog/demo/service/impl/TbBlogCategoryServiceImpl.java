package blog.demo.service.impl;

import blog.demo.entity.TbBlogCategory;
import blog.demo.dao.TbBlogCategoryMapper;
import blog.demo.service.ITbBlogCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class TbBlogCategoryServiceImpl extends ServiceImpl<TbBlogCategoryMapper, TbBlogCategory> implements ITbBlogCategoryService {

    @Autowired
    TbBlogCategoryMapper categoryMapper;

    /**
     * 获取所有的分类列表
     * @return
     */
    @Override
    public List<TbBlogCategory> getAllCategory() {
        QueryWrapper<TbBlogCategory> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0).orderByDesc("category_rank","create_time");
        List<TbBlogCategory> categoryList=categoryMapper.selectList(queryWrapper);
        return categoryList;
    }
}
