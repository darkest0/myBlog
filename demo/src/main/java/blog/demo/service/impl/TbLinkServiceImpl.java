package blog.demo.service.impl;

import blog.demo.controller.vo.BlogListVO;
import blog.demo.dao.TbBlogMapper;
import blog.demo.entity.TbBlog;
import blog.demo.entity.TbLink;
import blog.demo.dao.TbLinkMapper;
import blog.demo.service.ITbLinkService;
import blog.demo.util.PageQueryUtil;
import blog.demo.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
public class TbLinkServiceImpl extends ServiceImpl<TbLinkMapper, TbLink> implements ITbLinkService {

    @Autowired
    TbLinkMapper linkMapper;

    @Override
    public Map<Integer, List<TbLink>> getLink() {
        QueryWrapper<TbLink> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0).orderByDesc("link_id");
        List<TbLink> tbLinks = linkMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(tbLinks)){
            //根据type 分组
            Map<Integer,List<TbLink>> linkMap =  tbLinks.stream().collect(Collectors.groupingBy(TbLink::getLinkType));
            return  linkMap;
        }
        return null;
    }
}
