package life.recruit.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 此DTO包裹的就是页面所承载的元素
 */
@Data
public class PaginationDTO {

    private List<ArticleDTO> articles;//保存当前文章信息

    private boolean showFirstPage; //第一页
    private boolean showEndPage; //最后一页
    private boolean showPrevious; //前一页
    private boolean showNext; //下一页
    private Integer currentPage; // 当前页面
    private List<Integer> pages = new ArrayList<>(); //显示当前所呈现的分页列表数组

    private int totalPage;
    /**
     *
     * @param totalCount 发布的文章总数目
     * @param page 页数
     * @param size 一页放多少文章
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.currentPage = page;
        //总共需要分几页 (文章总页数)
//        int totalPage;
        //计算文章分几页
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        //实现当前页面前面有三页后面有三页 总共显示7页
        pages.add(currentPage);
        for (int i = 1; i <=3 ; i++) {
            if(currentPage - i > 0){
                pages.add(0,currentPage - i);
            }
            if (currentPage + i <= totalPage) {
                pages.add(currentPage + i);
            }
        }


        //当前是第一页的时候没有showPrevious
        if(currentPage == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }
        //当前是最后一页的时候没有showNext
        if(currentPage.equals(totalPage)){
            showNext = false;
        }else{
            showNext = true;
        }
        //当分页中不包括第一页时显示showFirstPage
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //当分页中不包括最后一页时显示showEndPage
        if (pages.contains(totalPage)) {
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
