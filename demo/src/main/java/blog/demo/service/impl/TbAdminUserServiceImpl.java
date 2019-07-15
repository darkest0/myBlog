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

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        TbAdminUser adminUser = adminUserMapper.selectById(loginUserId);
        //用户不为空 才可进行更改
        if(adminUser!=null){
            String originalPasswordMd5 =MD5Util.MD5Encode(originalPassword,"UTF-8");
            String newPasswordMd5 =MD5Util.MD5Encode(newPassword,"UTF-8");
            //对比原密码
            if (originalPasswordMd5.equals(adminUser.getLoginPassword())){
                adminUser.setLoginPassword(newPasswordMd5);
                if (adminUserMapper.updateById(adminUser)>0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        TbAdminUser user =adminUserMapper.selectById(loginUserId);
        //用户存在
        if (user!=null){
            user.setLoginUserName(loginUserName);
            user.setNickName(nickName);
            if (adminUserMapper.updateById(user)>1){
                return true;
            }
        }
        return false;
    }
}
