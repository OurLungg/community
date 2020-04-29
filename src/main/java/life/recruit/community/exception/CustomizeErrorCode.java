package life.recruit.community.exception;

/**
 * 为系统做一些通用的错误封装
 */
public enum CustomizeErrorCode implements  ICustomizeErrorCode{
    ARTICLE_NOT_FOUND("你要找的文章已经不存在了，要不换一篇试试？");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
