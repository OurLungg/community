package life.recruit.community.controller;


import life.recruit.community.dto.PaginationDTO;
import life.recruit.community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private ArticleService articleService;

@GetMapping("/search")
public String search(Model model,
                    @RequestParam(name = "page",defaultValue = "1") Integer page,
                    @RequestParam(name = "size",defaultValue = "5") Integer size,
                    @RequestParam(name="search",required = false) String search){

    //将整个页面信息(搜索的文章+用户+分页)装入model模型
    PaginationDTO paginationBySearch = articleService.listBysearch(search , page, size);
    model.addAttribute("paginationBySearch",paginationBySearch);
    model.addAttribute("search", search);
    //在跳转页面之前把列表信息放入model， 然后在model中渲染html
    return "search";
    }
}
