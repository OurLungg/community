function postComment( ) {
    var articleId = $("#article_id").val();
    // alert(articleId);
    var content = $("#comment_content").val();
    // alert(content);

    if(!content){
        alert("评论的内容不能为空");
        return ;
    }

    $.ajax({
        type: "POST",
        //加上/ 是基于根目录 而不是基于article.html
        url:"/comment",
        contentType: 'application/json',
        //将复杂的数据类型表示为JSON  JSON.stringify
        //将一个js对象转换为字符串，在后端commentController的RequestBody将字符串反序列化成对象
        data: JSON.stringify({
            "parent_id":articleId,
            "content":content,
            "type":1
        }),
        dataType: "json"
    });
    alert("评论成功！");
    //刷新页面
    window.location.reload();
    //发完隐藏
    $("#comment_section").hide();
}

function selectTag(value) {
    var textTag = $("#tag").val();
    //添加的内容不存在时再添加

    if (textTag.indexOf(value) === -1) {
        if (textTag) {
            //内容不为空
            $("#tag").val(textTag + ',' + value)
        } else {
            //内容为空
            $("#tag").val(value)
        }
    }

}


$(document).ready(function () {
    $("#tag").focus(function(){
        $("#select-tag").show();
    });
});
