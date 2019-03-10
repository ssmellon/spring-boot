package com.karl.pre.controller;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by caibosi on 2018-06-20.
 */
@RestController
@RequestMapping("")
public class QueryController
{
    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/consumer/{service}")
    public String getServices(@PathVariable("service") String service,
                              @RequestBody Map<String, String> params)
    {
        String path = params.getOrDefault("path", "/hello");
        System.out.println("path  " + path);
        String url = "http://" + service + path;
        ResponseEntity<String> response = new ResponseEntity<>("origin ", HttpStatus.OK);
        try
        {
            response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), String.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return response.getBody();
    }

    @GetMapping("/service/{serviceId}")
    public List<ServiceInstance> getInstance(@PathVariable("serviceId") String serviceId)
    {
        return discoveryClient.getInstances(serviceId);
    }

    @GetMapping("/services")
    public List<String> getServices()
    {
        return discoveryClient.getServices();
    }
}