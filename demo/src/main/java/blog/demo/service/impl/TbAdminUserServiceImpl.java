package blog.demo.service.impl;

import blog.demo.entity.TbAdminUser;
import blog.demo.dao.TbAdminUserMapper;
import blog.demo.service.ITbAdminUserService;
import blog.demo.util.MD5Util;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
@Service
public class TbAdminUserServiceImpl extends ServiceImpl<TbAdminUserMapper, TbAdminUser> implements ITbAdminUserService {

    @Autowired
    TbAdminUserMapper adminUserMapper;


    @Override
    public TbAdminUser login(String userName, String passWord) {
        String passWordMd5 = MD5Util.MD5Encode(passWord,"UTF-8");
        QueryWrapper<TbAdminUser>   queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("login_user_name",userName).eq("login_password",passWordMd5);
        TbAdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        return adminUser;
    }
}
