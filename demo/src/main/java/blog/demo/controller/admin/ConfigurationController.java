package blog.demo.controller.admin;

import blog.demo.service.ITbConfigService;
import blog.demo.util.Result;
import blog.demo.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author towa
 *系统配置
 */
@Controller
@RequestMapping("/admin")
public class ConfigurationController {

    @Resource
    private ITbConfigService configService;

    @GetMapping("/configurations")
    public String list(HttpServletRequest request){
        request.setAttribute("path","configurations");
        request.setAttribute("configurations",configService.getAllConfigs());
        return "admin/configuration";
    }

    @PostMapping("/configurations/website")
    @ResponseBody
    public Result website(@RequestParam(value = "websiteName",required = false) String websiteName,
                          @RequestParam(value = "websiteDescription",required = false) String websiteDescription,
                          @RequestParam(value = "websiteLogo",required = false) String websiteLogo,
                          @RequestParam(value = "websiteIcon",required = false) String websiteIcon){
        //作为修改的标记
        int updateResult =0;
        if (!StringUtils.isEmpty(websiteName)){
            updateResult +=configService.updateConfig("websiteName",websiteName);
        }
        if (!StringUtils.isEmpty(websiteDescription)){
            updateResult +=configService.updateConfig("websiteDescription",websiteDescription);
        }
        if (!StringUtils.isEmpty(websiteLogo)){
            updateResult +=configService.updateConfig("websiteLogo",websiteLogo);
        }
        if (!StringUtils.isEmpty(websiteIcon)){
            updateResult +=configService.updateConfig("websiteIcon",websiteIcon);
        }
        return ResultGenerator.genSuccessResult(updateResult > 0);
    }

    @PostMapping("/configurations/userInfo")
    @ResponseBody
    public Result userInfo(@RequestParam(value = "yourAvatar",required = false) String yourAvatar,
                           @RequestParam(value = "yourNamem",required = false) String yourNamem,
                           @RequestParam(value = "yourEmail",required = false) String yourEmail){
        int updateResult =0;
        if (!StringUtils.isEmpty(yourAvatar)){
            updateResult +=configService.updateConfig("yourAvatar",yourAvatar);
        }
        if (!StringUtils.isEmpty(yourNamem)){
            updateResult +=configService.updateConfig("yourNamem",yourNamem);
        }
        if (!StringUtils.isEmpty(yourEmail)){
            updateResult +=configService.updateConfig("yourEmail",yourEmail);
        }
        return ResultGenerator.genSuccessResult(updateResult>0);
    }

    @PostMapping("/configurations/footer")
    @ResponseBody
    public Result footer(@RequestParam(value = "footerAbout", required = false) String footerAbout,
                         @RequestParam(value = "footerICP", required = false) String footerICP,
                         @RequestParam(value = "footerCopyRight", required = false) String footerCopyRight,
                         @RequestParam(value = "footerPoweredBy", required = false) String footerPoweredBy,
                         @RequestParam(value = "footerPoweredByURL", required = false) String footerPoweredByURL) {
        int updateResult=0;
        if (!StringUtils.isEmpty(footerAbout)){
            updateResult += configService.updateConfig("footerAbout",footerAbout);
        }
        if (!StringUtils.isEmpty(footerICP)){
            updateResult += configService.updateConfig("footerICP",footerICP);
        }
        if (!StringUtils.isEmpty(footerCopyRight)){
            updateResult += configService.updateConfig("footerCopyRight",footerCopyRight);
        }
        if (!StringUtils.isEmpty(footerPoweredBy)){
            updateResult += configService.updateConfig("footerPoweredBy",footerPoweredBy);
        }
        if (!StringUtils.isEmpty(footerPoweredByURL)){
            updateResult += configService.updateConfig("footerPoweredByURL",footerPoweredByURL);
        }
        return ResultGenerator.genSuccessResult(updateResult>0);
    }
}
