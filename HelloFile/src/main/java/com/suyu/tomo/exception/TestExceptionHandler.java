package com.suyu.tomo.exception;


import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//@Component
public class TestExceptionHandler
//        implements HandlerExceptionResolver
{

    public static final String DEFAULT_ERROR_VIEW = "error";
//
    @ExceptionHandler(value = TestException.class)
    @ResponseBody
    public String businessExceptionHandler(TestException e) throws Exception {

        return "hahaa----s" + e.getCurCause();
    }
//
//    @ExceptionHandler(value = TestException.class)
//    @ResponseBody
//    public Map<String, String> jsonExceptionHandler(HttpServletRequest req, Exception e) {
//
//        Map<String, String> re = new HashMap<String, String>();
//        re.put("error", "1");
//        re.put("msg", e.getMessage());
//        return re;
//    }
//@Override
//public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//
//    //添加自己的异常处理逻辑，如日志记录等
//    System.out.println("<<<<<<<<<<<<<------->>>>>>>>>>>>>");
//    // TODO Auto-generated method stub
//    return new ModelAndView("exception");
//}
//    public String getErrorPath() {
//        return this.errorProperties.getPath();
//    }
}
