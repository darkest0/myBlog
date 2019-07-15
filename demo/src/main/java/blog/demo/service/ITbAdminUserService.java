package blog.demo.service;

import blog.demo.entity.TbAdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface ITbAdminUserService extends IService<TbAdminUser> {
    /**
     * 获取用户
     * @param userName
     * @param passWord
     * @return
     */
    TbAdminUser login(String userName,String passWord);

    /**
     * 修改当前登陆用户的密码
     */
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /**
     * 修改当前登陆用户的名称信息
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}
