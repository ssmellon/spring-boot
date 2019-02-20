package com.suyu.tomo.servlet;

import com.suyu.tomo.exception.TestException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebServlet(urlPatterns = "/serv/hello")
public class TestServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doGet----------------");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doPost----------------");
//        throw new TestException("ha");
        try
        {
            int f = 1/0;
        }
        catch(Exception e)
        {
            req.setAttribute("err",1212);
            throw new TestException("ha");
        }
        resp.getWriter().print("<h1>Hello MyServlet Response return you this</h1>");
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
