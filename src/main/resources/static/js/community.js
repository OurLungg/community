function postComment( ) {
    var article_id = $("#article_id").val();
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
            "parent_id":article_id,
            "content":content,
            //type = 1是评论文章 type =2为回复评论 二级评论
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

function check(){
    var info = $('input:radio:checked').val();
    if(info == 1){
        $("#select-tag").show();
    } else{
        $("#select-tag").hide();
    }
}


function giveHelp() {
    //获取文章id
    var article_id = $("#article_id").val();
    //获取文章作者
    var article_creator = $("#article_creator").val();
    //获取帮助人id
    var helper = $("#helper").val();


    $.ajax({
        type: "POST",
        //加上/ 是基于根目录 而不是基于help.html
        url:"/help",
        //表示发送的是json字符串
        contentType: 'application/json',
        //将复杂的数据类型表示为JSON  JSON.stringify
        //将一个js对象转换为字符串，在后端commentController的RequestBody将字符串反序列化成对象
        data: JSON.stringify({
            "article_id":article_id,
            "article_creator":article_creator,
            "helper": helper,
            "accomplish":0
        }),
        success:function(data){
          if(data.code === 200) alert("感谢您的爱心，请尽快与主人取得联系！");
            //刷新页面
            window.location.reload();
        },
        dataType: "json"
    });

}


function finish() {
    //获取文章id
    var article_id = $("#article_id").val();
    //获取文章作者
    var article_title = $("#article_title").val();


    $.ajax({
        type: "POST",
        //加上/ 是基于根目录 而不是基于help.html
        url:"/finish",
         // contentType: 'application/json',
        //上面传的是json字符串， 下面是键值对
        contentType: 'application/x-www-form-urlencoded',
        data: {
            id:article_id,
            title:article_title,
        },
        success:function(data){
            if(data.code === 200) alert("成功！");
            //刷新页面
            window.location.reload();
        },
    });
}
