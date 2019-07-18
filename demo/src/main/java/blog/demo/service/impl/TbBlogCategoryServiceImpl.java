package blog.demo.service.impl;

import blog.demo.dao.TbBlogMapper;
import blog.demo.entity.TbBlog;
import blog.demo.entity.TbBlogCategory;
import blog.demo.dao.TbBlogCategoryMapper;
import blog.demo.service.ITbBlogCategoryService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private TbBlogCategoryMapper categoryMapper;
    @Autowired
    private TbBlogMapper blogMapper;

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

    @Override
    public int getTotalCategory() {
        QueryWrapper<TbBlogCategory> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        int total = categoryMapper.selectCount(queryWrapper);
        return total;
    }

    @Override
    public PageResult getBlogCategoryPage(PageQueryUtil pageQueryUtil) {
        List<TbBlogCategory> categoryList = categoryMapper.findCategoryList(pageQueryUtil);
        QueryWrapper<TbBlogCategory> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        int total =categoryMapper.selectCount(queryWrapper);
        PageResult pageResult =new PageResult(categoryList,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean saveCategory(String categoryName, String categoryIcon) {
       QueryWrapper<TbBlogCategory> queryWrapper =new QueryWrapper<>();
       queryWrapper.eq("category_name",categoryName).eq("is_deleted",0);
       TbBlogCategory temp = categoryMapper.selectOne(queryWrapper);
       if (temp == null){
            TbBlogCategory blogCategory =new TbBlogCategory();
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            return categoryMapper.insert(blogCategory)>0;
       }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon) {
        TbBlogCategory blogCategory= categoryMapper.selectById(categoryId);
        if (blogCategory!=null){
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            //修改blog实体的分类

            TbBlog blog =new TbBlog();
            blog.setBlogCategoryId(categoryId);
            blog.setBlogCategoryName(categoryName);
            QueryWrapper<TbBlog> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("blog_category_id",blogCategory.getCategoryId());
            blogMapper.update(blog,queryWrapper);
            return categoryMapper.updateById(blogCategory)>0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length<1){
            return false;
        }
        //修改tb_blog表
        TbBlog blog =new TbBlog();
        blog.setBlogCategoryName("默认分类");
        blog.setBlogId(0L);
        QueryWrapper<TbBlog> blogQueryWrapper= new QueryWrapper<>();
        blogQueryWrapper.in("blog_category_id",ids);
        blogMapper.update(blog,blogQueryWrapper);
        //删除分类数据
        TbBlogCategory category =new TbBlogCategory();
        category.setIsDeleted(1);
        QueryWrapper<TbBlogCategory> queryWrapper =new QueryWrapper<>();
        queryWrapper.in("category_id",ids);
        return categoryMapper.update(category,queryWrapper)>0 ;
    }


}
