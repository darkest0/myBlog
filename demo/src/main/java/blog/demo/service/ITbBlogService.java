package blog.demo.service;

import blog.demo.controller.vo.BlogDetailVO;
import blog.demo.controller.vo.BlogListVO;
import blog.demo.entity.TbBlog;
import blog.demo.util.PageResult;
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
public interface ITbBlogService extends IService<TbBlog> {

    /**
     * 获取首页文章
     */

    PageResult getBlogsForIndexPage(int page);

    /**
     * 首页左侧数据栏列表
     * 0-最热 1-最新
     */
    List<BlogListVO> getBlogListIndexPage(int type);
    /**
     * 文章详情
     */
    BlogDetailVO getBlogDetail(Long blogId);

    /**
     * 根据标签获取文章列表
     */
    PageResult getBlogPageByTag(String tagName,int page);

    /**分类获取文章列表
     * 根据分类
     */
    PageResult getBlogPageByCategory(String categoryName,Integer page);

    /**
     * 搜索获取文章列表
     */
    PageResult getBlogPageBySearch(String keyword,Integer page);

    /**
     * 根据 subUrl获取 文章详情
     */
    BlogDetailVO getBlogDetailByStrUrl(String subUrl);

    /**
     * 获取文章数量
     */
    int getTotalBlog();
}
