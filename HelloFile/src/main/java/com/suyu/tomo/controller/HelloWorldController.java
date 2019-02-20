package com.suyu.tomo.controller;

import com.suyu.tomo.exception.TestException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class HelloWorldController {
    @RequestMapping("/getUser")
    public String getUser() {
//        throw new TestException("hello");
        System.out.println("hello");
        return "user";
    }


    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file",required=false)MultipartFile file) throws IOException
    {

        String url = "http://127.0.0.1:8080/forwardTest";
        String filePath = "C:\\Users\\Administrator\\AppData\\Local\\Postman\\app-6.7.1\\api-ms-win-core-console-l1-1-0.dll";

        String fileName = file.getOriginalFilename();
        try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(fileName)));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RestTemplate rest = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(fileName));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);

        return rest.postForObject(url, param, String.class);
    }

    @RequestMapping("/params/{path}")
    @ResponseBody
    public String params(@RequestParam(value = "pwd",required=false) String pwd,
                         @PathVariable("path") String path)
    {
//        throw new TestException("hello");
//        HttpServletRequest request = new H
//        response.sendRedirect("http://localhost:8080/forwardTest");
//        return "redirect:http://localhost:8080/forwardTest";
//        return "forward:http://localhost:8080/forwardTest";
        return pwd + "_" + path;
    }

    @RequestMapping("/forwardTest")
    @ResponseBody
    public String forwardTest(@RequestParam(value = "file",required=false)MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                /*
                 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到
                 * d:/files大家是否能实现呢？ 等等;
                 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；
                 * 3、文件格式; 4、文件大小的限制;
                 */
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(
                                file.getOriginalFilename()+"haha")));
                System.out.println(file.getName());
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        }

        return "上传失败，因为文件是空的.";
    }
}
