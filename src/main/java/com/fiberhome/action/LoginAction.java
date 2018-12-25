package com.fiberhome.action;

import com.alibaba.fastjson.JSONObject;
import com.fiberhome.util.AccountUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author huangfan
 * @date 2018/12/21
 */
@Controller
@RequestMapping("loginController")
public class LoginAction {

    private static Logger logger= LogManager.getLogger(LoginAction.class);

    @Autowired
    private StringRedisTemplate redisTemplate;
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        HttpSession session=request.getSession();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Assert.notNull(username);
        Assert.notNull(password);
        if (username.equals("admin")&&password.equals("admin")){
            session.setAttribute("username",username);
            session.setAttribute("password",password);
        }
        return "loginSuccess";
    }

    @RequestMapping("/auth")
    public void auth(HttpServletRequest request,HttpServletResponse response){
        String result= AccountUtil.postAuth(request);
        outJson(response,result);
    }
    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest request, ModelMap map){
        map.put("username", request.getSession().getAttribute("username"));
        return "loginSuccess";
    }
    @RequestMapping
    public String test(){
        return "test";
    }

    private void outJson(HttpServletResponse response, Object object) {
        //将实体对象转换为JSON Object转换
        Object responseJSONObject = JSONObject.toJSON(object);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
            out.flush();
            logger.debug(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
