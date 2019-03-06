package com.pre.karl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class EurekaServerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}