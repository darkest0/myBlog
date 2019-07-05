package blog.demo.service;

import blog.demo.entity.TbConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author towa
 * @since 2019-07-03
 */
public interface ITbConfigService extends IService<TbConfig> {
    /**
     * 获取所有配置
     */
    Map<String , String> getAllConfigs();

}
