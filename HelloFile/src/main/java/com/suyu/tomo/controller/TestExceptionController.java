package com.suyu.tomo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/error")
//@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class TestExceptionController implements ErrorController
{
    @Value("${error.path:/error}")
    private String path = "/error";

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("hee", request.getAttribute("err"));
        return new ResponseEntity(result ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public  String getErrorPath()
    {
        return this.path;
    }
}
