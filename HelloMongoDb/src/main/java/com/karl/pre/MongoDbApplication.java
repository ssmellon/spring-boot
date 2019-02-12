package com.karl.pre;

import com.karl.pre.filter.PathFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MongoDbApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MongoDbApplication.class, args);
    }

    @Bean
    public PathFilter pathFilter()
    {
        return new PathFilter();
    }
}
