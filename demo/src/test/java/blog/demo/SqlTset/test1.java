package blog.demo.SqlTset;

import blog.demo.DemoApplicationTests;
import blog.demo.dao.TbAdminUserMapper;
import blog.demo.dao.TbBlogMapper;
import blog.demo.entity.TbAdminUser;
import blog.demo.entity.TbBlog;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class test1 extends DemoApplicationTests {
    @Autowired
    private TbAdminUserMapper tbAdminUserMapper;

    @Autowired
    private TbBlogMapper tbBlogMapper;

    @Test
    public void test1(){
        QueryWrapper<TbAdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("nick_name").eq("login_user_name","admin");
        TbAdminUser user = tbAdminUserMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void test2(){
        QueryWrapper<TbBlog> queryWrapper = new QueryWrapper<>();
        List<TbBlog> blog = tbBlogMapper.selectList(queryWrapper);
        for (TbBlog tbBlog : blog) {
            System.out.println(tbBlog);
        }
    }
}
