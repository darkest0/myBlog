package blog.demo.service.impl;

import blog.demo.entity.TbConfig;
import blog.demo.dao.TbConfigMapper;
import blog.demo.service.ITbConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Max;
import java.util.Collection;
import java.util.Collections;
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
public class TbConfigServiceImpl extends ServiceImpl<TbConfigMapper, TbConfig> implements ITbConfigService {

    @Autowired
    TbConfigMapper configMapper;

    public static final String websiteName = "personal blog";
    public static final String websiteDescription = "personal blog是SpringBoot2+Thymeleaf+Mybatis建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建";
    public static final String websiteLogo = "/admin/dist/img/logo2.png";
    public static final String websiteIcon = "/admin/dist/img/favicon.png";

    public static final String yourAvatar = "/admin/dist/img/13.png";
    public static final String yourEmail = "2449207463@qq.com";
    public static final String yourName = "十三";

    public static final String footerAbout = "your personal blog. have fun.";
    public static final String footerICP = "浙ICP备 xxxxxx-x号";
    public static final String footerCopyRight = "@2018 十三";
    public static final String footerPoweredBy = "personal blog";
    public static final String footerPoweredByURL = "##";

    @Override
    public Map<String, String> getAllConfigs() {
        QueryWrapper<TbConfig> queryWrapper =new QueryWrapper();
        List<TbConfig> configs = configMapper.selectList(queryWrapper);
        Map<String,String> configMap = configs.stream().collect(Collectors.toMap(TbConfig::getConfigName,TbConfig::getConfigValue));
        for (Map.Entry<String,String> config : configMap.entrySet()) {
            if ("websiteName".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteName);
            }
            if ("websiteDescription".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteDescription);
            }
            if ("websiteLogo".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteLogo);
            }
            if ("websiteIcon".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteIcon);
            }
            if ("yourAvatar".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourAvatar);
            }
            if ("yourEmail".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourEmail);
            }
            if ("yourName".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourName);
            }
            if ("footerAbout".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerAbout);
            }
            if ("footerICP".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerICP);
            }
            if ("footerCopyRight".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerCopyRight);
            }
            if ("footerPoweredBy".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerPoweredBy);
            }
            if ("footerPoweredByURL".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerPoweredByURL);
            }
        }
        return configMap;
    }
}
