package life.recruit.community.controller;

import life.recruit.community.mapper.CompanyMapper;
import life.recruit.community.model.Company;
import life.recruit.community.model.User;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class CompanyController {

    @Value("${cbs.imagesPath}")
    private String theSetDir; //全局配置文件中设置的图片的路径

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private UserService userService;

    @RequestMapping("companyInfo")
    private String companyInfo(Model model,
                               HttpServletRequest request) {
        User tb_user = (User) request.getSession().getAttribute("tb_user");
        Company company = companyMapper.selectByUserId(tb_user.getId());
        if(company == null){
            return "companyEdit";
        }
        model.addAttribute("company",company);
        return "companyInfo";
    }


    @RequestMapping("companyEditInfo")
    private String companyEditInfo(MultipartFile filename,
                                   @RequestParam("name") String name,
                                   @RequestParam("type") String type,
                                   @RequestParam("info")String info,
                                   @RequestParam("address") String address,
                                   @RequestParam("email") String email,
                                   HttpServletRequest request)
                                    throws Exception {
        String parentDirPath = theSetDir.substring(theSetDir.indexOf(':')+1, theSetDir.length()); //通过设置的那个字符串获得存放图片的目录路径
        String fileName = filename.getOriginalFilename();
        File parentDir = new File(parentDirPath);
        if(!parentDir.exists()) //如果那个目录不存在先创建目录
        {
            parentDir.mkdir();
        }
        filename.transferTo(new File(parentDirPath + fileName)); //全局配置文件中配置的目录加上文件名

        User tb_user = (User) request.getSession().getAttribute("tb_user");
        Company company = new Company();
        company.setName(name);
        company.setInfo(info);
        company.setAddress(address);
        company.setEmail(email);
        company.setType(type);
        company.setUser_id(tb_user.getId());
        companyMapper.create(company);
        userService.updateCompany(name,fileName,tb_user.getId());
//        model.addAttribute("company", company);
        return "redirect:/";
    }
}
