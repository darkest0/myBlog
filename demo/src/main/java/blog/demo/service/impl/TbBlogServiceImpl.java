package blog.demo.service.impl;

import blog.demo.controller.vo.BlogDetailVO;
import blog.demo.controller.vo.BlogListVO;
import blog.demo.dao.TbBlogCategoryMapper;
import blog.demo.dao.TbBlogCommentMapper;
import blog.demo.dao.TbBlogTagMapper;
import blog.demo.dao.TbBlogTagRelationMapper;
import blog.demo.entity.TbBlog;
import blog.demo.dao.TbBlogMapper;
import blog.demo.entity.TbBlogCategory;
import blog.demo.entity.TbBlogComment;
import blog.demo.entity.TbBlogTag;
import blog.demo.entity.TbBlogTagRelation;
import blog.demo.service.ITbBlogService;
import blog.demo.util.MarkDownUtil;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import blog.demo.util.PatternUtil;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
@Service
public class TbBlogServiceImpl extends ServiceImpl<TbBlogMapper, TbBlog> implements ITbBlogService {

    @Autowired
    TbBlogMapper tbBlogMapper;
    @Autowired
    TbBlogCategoryMapper categoryMapper;
    @Autowired
    TbBlogCommentMapper commentMapper;
    @Autowired
    TbBlogTagMapper blogTagMapper;
    @Autowired
    TbBlogTagRelationMapper blogTagRelationMapper;

    /**
     * 获取文章列表
     * @param page
     * @return
     */
    @Override
    public PageResult getBlogsForIndexPage(int page) {
        Map params = new HashMap();
        params.put("page",page);
        //每页条数
        params.put("limit",8);
        params.put("blogStatus",1);
        PageQueryUtil pageQueryUtil =new PageQueryUtil(params);
        QueryWrapper<TbBlog> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0).eq("blog_status",1);
        //获取文章列表
        List<TbBlog> blogList = tbBlogMapper.selectList(queryWrapper);
        List<BlogListVO> listVOlis =getBlogListVOsByBlogs(blogList);
        int total =tbBlogMapper.getBlogTotal(pageQueryUtil);
        PageResult pageResult =new PageResult(listVOlis,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public List<BlogListVO> getBlogListIndexPage(int type) {
        QueryWrapper<TbBlog> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("blog_status",1)
                .eq("is_deleted",0);
        if (type==1){
            queryWrapper.orderByDesc("blog_views");
        }else if(type ==2){
            queryWrapper.orderByDesc("blog_id");
        }
        List<TbBlog> blogs =tbBlogMapper.selectByDiy(queryWrapper,9);
        // List<TbBlog> blogs =tbBlogMapper.findBlogListByType(type,9);
        List<BlogListVO> blogListVOS =new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogs)){
            for (TbBlog blog : blogs) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog,blogListVO);
                blogListVOS.add(blogListVO);
            }

        }
        return blogListVOS;
    }

    @Override
    public BlogDetailVO getBlogDetail(Long blogId) {
        TbBlog blog = tbBlogMapper.selectById(blogId);
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        if (blogDetailVO!=null){
            return blogDetailVO;
        }
        return null;
    }

    @Override
    public PageResult getBlogPageByTag(String tagName, int page) {
        //验证名字正常
        if (PatternUtil.validKeyword(tagName)){
            QueryWrapper<TbBlogTag> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("tag_name",tagName).eq("is_deleted",0);
            TbBlogTag blogTag = blogTagMapper.selectOne(queryWrapper);
            if (blogTag!=null && page>0){
                Map param =new HashMap();
                param.put("page",page);
                param.put("limit",9);
                param.put("tagId",blogTag.getTagId());
                PageQueryUtil queryUtil =new PageQueryUtil(param);
                List<TbBlog> blogList =tbBlogMapper.getBlogsPageByTagId(queryUtil);
                List<BlogListVO>  blogListVOS = getBlogListVOsByBlogs(blogList);
                QueryWrapper<TbBlogTagRelation> tbBlogTagRelationQueryWrapper =new QueryWrapper<>();
                tbBlogTagRelationQueryWrapper.select("blog_id").eq("tag_id",blogTag.getTagId());
                List<TbBlogTagRelation> tbBlogTagRelationList = blogTagRelationMapper.selectList(tbBlogTagRelationQueryWrapper);
                List<Long> tbBlogIdList =new ArrayList<>();
                for (TbBlogTagRelation tbBlog : tbBlogTagRelationList) {
                    tbBlogIdList.add(tbBlog.getBlogId());
                }
                QueryWrapper<TbBlog> tbBlogQueryWrapper =new QueryWrapper<>();
                tbBlogQueryWrapper.in("blog_id",tbBlogIdList).eq("blog_status",1).eq("is_deleted",0);
                int total = tbBlogMapper.selectCount(tbBlogQueryWrapper);
                PageResult pageResult =new PageResult(blogListVOS,total,queryUtil.getLimit(), queryUtil.getPage());
                return pageResult;
            }

        }
        return null;
    }

    @Override
    public PageResult getBlogPageByCategory(String categoryName, Integer page) {
        if (PatternUtil.validKeyword(categoryName)){
            QueryWrapper<TbBlogCategory> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("category_name",categoryName).eq("is_deleted",0);
            TbBlogCategory category = categoryMapper.selectOne(queryWrapper);
            //判断 category 存在
            if("默认分类".equals(categoryName) && category==null){
                category =new TbBlogCategory();
                category.setCategoryId(0);
            }
            if (category!=null && page>0){
                Map param =new HashMap();
                param.put("limit",9);
                param.put("page",page);
                param.put("blogCategoryId",category.getCategoryId());
                param.put("blogStatus",1);
                PageQueryUtil pageQueryUtil =new PageQueryUtil(param);
                List<TbBlog> blogList =tbBlogMapper.getBlogPageByXX(pageQueryUtil);
                List<BlogListVO> blogDetailVOS  =getBlogListVOsByBlogs(blogList);
                int total = tbBlogMapper.getBlogTotal(pageQueryUtil);
                PageResult pageResult =new PageResult(blogDetailVOS,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogPageBySearch(String keyword, Integer page) {
        if(PatternUtil.validKeyword(keyword) && page>0){
            Map param =new HashMap();
            param.put("limit",9);
            param.put("page",page);
            param.put("keyword",keyword);
            param.put("blogStatus",1);
            PageQueryUtil pageQueryUtil =new PageQueryUtil(param);
            List<TbBlog> blogList =tbBlogMapper.getBlogPageByXX(pageQueryUtil);
            List<BlogListVO> blogListVOS =getBlogListVOsByBlogs(blogList);
            int total =tbBlogMapper.getBlogTotal(pageQueryUtil);
            PageResult pageResult =new PageResult(blogListVOS,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
            return pageResult;
        }

        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailByStrUrl(String subUrl) {
        QueryWrapper<TbBlog> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("blog_sub_url",subUrl).eq("is_deleted",0);
        TbBlog tbBlog = tbBlogMapper.selectOne(queryWrapper);
        BlogDetailVO blogDetailVO = getBlogDetailVO(tbBlog);
        if (blogDetailVO!=null){
            return blogDetailVO;
        }
        return null;
    }

    @Override
    public int getTotalBlog() {
        QueryWrapper<TbBlog> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        int total = tbBlogMapper.selectCount(queryWrapper);
        return total;
    }

    /**
     * 转化为文章
     * @param blog
     * @return
     */
    private BlogDetailVO getBlogDetailVO(TbBlog blog){
        if (blog!=null && blog.getBlogStatus() ==1){
            //增加流量量
            blog.setBlogViews(blog.getBlogViews()+1);
            tbBlogMapper.updateById(blog);
            //copy
            BlogDetailVO blogDetailVO=new BlogDetailVO();
            BeanUtils.copyProperties(blog,blogDetailVO);
            //设置内容
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            TbBlogCategory blogCategory = categoryMapper.selectById(blog.getBlogCategoryId());
            if (blogCategory ==null){
                blogCategory =new TbBlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分组");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //分类信息
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            if (!StringUtils.isEmpty(blog.getBlogTags())){
                //标签的设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            //设置评论
            QueryWrapper<TbBlogComment> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("blog_id",blog.getBlogId()).eq("comment_status",1).eq("is_deleted",0);
            int commentCount = commentMapper.selectCount(queryWrapper);
            blogDetailVO.setCommentCount(commentCount);
            return blogDetailVO;
        }
        return null;
    }

    /**
     * 文章列表转换
     */
    private List<BlogListVO> getBlogListVOsByBlogs(List<TbBlog> blogList){
        List<BlogListVO> blogListVOS =new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)){
            List<Integer> categoryIds =blogList.stream().map(TbBlog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer,String> blogCategoryMap =new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)){
                QueryWrapper<TbBlogCategory> queryWrapper =new QueryWrapper<>();
                queryWrapper.in("category_id",categoryIds).eq("is_deleted",0);
                List<TbBlogCategory> blogCategories = categoryMapper.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(blogCategories)){
                    blogCategoryMap =blogCategories.stream().collect(Collectors
                            .toMap(TbBlogCategory::getCategoryId,TbBlogCategory::getCategoryIcon,(key1, key2) -> key2));
                }
            }

            for (TbBlog blog:blogList){
                BlogListVO blogListVO =new BlogListVO();
                BeanUtils.copyProperties(blog,blogListVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())){
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                }else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("默认分类");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }

}
