function postComment( ) {
    var articleId = $("#article_id").val();
    // alert(articleId);
    var content = $("#comment_content").val();
    // alert(content);

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
    })
    alert("评论成功！");
    //发完隐藏
    $("#comment_section").hide();
}