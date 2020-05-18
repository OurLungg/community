package life.recruit.community.controller;

import life.recruit.community.model.User;
import life.recruit.community.model.UserInfo;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class UserInfoController {

    @Autowired
    private UserService userService;

    @Value("${cbs.imagesPath}")
    private String theSetDir; //全局配置文件中设置的图片的路径

    @GetMapping("/{page}")
    public String toPate(@PathVariable("page") String page)
    {
        return page;
    }

    @RequestMapping("/userInfo")
    public String userInfo(Model model, HttpServletRequest request) {
        User tb_user = (User) request.getSession().getAttribute("tb_user");
        UserInfo userInfo =  userService.findInfoById(tb_user.getId());
        if(userInfo == null) return "userEdit";
        model.addAttribute("tb_user", tb_user);
        model.addAttribute("userInfo", userInfo);
        return "userInfo";
    }

    @RequestMapping("/userEdit")
    public String userEdit() {
        return "userEdit";
    }

    @RequestMapping("/userEditInfo")
    public String userEditInfo(MultipartFile filename,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("school") String school,
                               @RequestParam("subject") String subject,
                               @RequestParam("tel") String tel,
                               @RequestParam("skill") String skill,
                               @RequestParam("project") String project,
                               @RequestParam("info") String info,
                               Model model,
                               HttpServletRequest request) throws Exception{

        String parentDirPath = theSetDir.substring(theSetDir.indexOf(':')+1, theSetDir.length()); //通过设置的那个字符串获得存放图片的目录路径
        String fileName = filename.getOriginalFilename();
        File parentDir = new File(parentDirPath);
        if(!parentDir.exists()) //如果那个目录不存在先创建目录
        {
            parentDir.mkdir();
        }
        filename.transferTo(new File(parentDirPath + fileName)); //全局配置文件中配置的目录加上文件名

        User tb_user = (User) request.getSession().getAttribute("tb_user");
        UserInfo userInfo = new UserInfo();

        tb_user.setName(name);
        tb_user.setEmail(email);
        tb_user.setAvatar_url(fileName);
        userInfo.setUser_id(tb_user.getId());
        userInfo.setSchool(school);
        userInfo.setSubject(subject);
        userInfo.setTel(tel);
        userInfo.setSkill(skill);
        userInfo.setProject(project);
        userInfo.setInfo(info);
        userService.updateInfo(tb_user);
        userService.createUserInfo(userInfo);

        model.addAttribute("userInfo", userInfo);
//        model.addAttribute("pic_name", fileName);
//        model.addAttribute("tb_user", tb_user);
        return "redirect:/userInfo";
    }


    //查看他人资料
    @GetMapping("/userInfo/{id}")
    public String scanUserInfo(Model model,
                               @PathVariable("id") Integer id) {
        User tb_user = userService.findById(id);
        UserInfo userInfo = userService.findInfoById(id);
        model.addAttribute("tb_user", tb_user);
        model.addAttribute("userInfo", userInfo);
        return "userInfo";
    }


}
