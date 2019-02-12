package com.karl.pre.web;

import com.karl.pre.mongo.domain.StrCar;
import com.karl.pre.mongo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class MongoController
{
    @Autowired
    private CarService carService;
    @Autowired
    private MongoTemplate mongoTemplate;


    @PostMapping("/save")
    public StrCar save(@RequestBody Map<String, String> carInfo)
    {
        String name = carInfo.getOrDefault("name", "benzi");
        String version = carInfo.getOrDefault("version", "Tang");
        StrCar user = new StrCar(name, version);
        mongoTemplate.save(user);
        return user;
    }

    @GetMapping("/find")
    public List<StrCar> find() {
        List<StrCar> userList = mongoTemplate.findAll(StrCar.class);
        return userList;
    }

    /**
     * input String name "Tseng"
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    public StrCar findByName(@RequestParam("name") String name)
    {
        StrCar user = carService.findByName(name);
        return user;
    }

    @PostMapping("/test")
    public String server(ServerHttpRequest request)
    {

        System.out.println("<<<<<<<<<<<<<<<-------------->>>>>>>>>>>>");
        System.out.println(request);
        return "test";
    }
}
