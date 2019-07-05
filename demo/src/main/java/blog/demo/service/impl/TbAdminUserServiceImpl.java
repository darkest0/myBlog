package blog.demo.service.impl;

import blog.demo.entity.TbAdminUser;
import blog.demo.dao.TbAdminUserMapper;
import blog.demo.service.ITbAdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
