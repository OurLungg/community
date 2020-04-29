package life.recruit.community.interceptor;

import life.recruit.community.model.User;
import life.recruit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//将当前的类给spring管理 才能注入userService 否则报空指针异常
@Service
public class Sessioninterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    //在程序处理之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果之前登录过 则token已经被保存到数据库中，此时需要拿到cookie来查询此时的token是否在数据库中
        //若已经在数据库中则直接显示登录
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userService.findBytoken(token);
                    if(user != null){
                        //如果登录之后 就把user信息放到session中去 做持久化处理
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
