package com.karl.pre.controller;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
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

    @GetMapping("/hello")
    public String hello()
    {
        return "hello ~ ";
    }

    @GetMapping("/services")
    public List<String> getServices()
    {
        return discoveryClient.getServices();
    }

    @GetMapping("/instances/{instanceId}")
    public List<ServiceInstance>  getInstances(@PathVariable("instanceId") String instanceId)
    {
        return discoveryClient.getInstances(instanceId);
    }

    @Autowired
    EurekaClientConfigBean eurekaClientConfigBean;

    @GetMapping("/query/eureka-server")
    public Object getEurekaServerUrl()
    {
        return eurekaClientConfigBean.getServiceUrl();
    }

    @Autowired
    private EurekaServiceRegistry serviceRegistry;

    @Autowired
    private EurekaRegistration registration;

    @Autowired
    private ApplicationContext context;

    @Autowired
    RestTemplate restTemplate ;


//    RestTemplateEurekaHttpClient eurekaHttpClient = new RestTemplateEurekaHttpClient(restTemplate, baseUrkl);
    String baseUrkl = "http://localhost:8761/eureka/";
    @PostMapping("/register/{appName}")
    public String register(@PathVariable("appName") String appName)
    {
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                .setAppName(appName)
                .setInstanceId(appName+":8001")
                .setIPAddr("127.0.0.1")
                .setVIPAddress(appName)
                .setDataCenterInfo(new MyDataCenterInfo(DataCenterInfo.Name.MyOwn))
                .setStatus(InstanceInfo.InstanceStatus.UP)
                .setHostName("localhost")
                .setOverriddenStatus(InstanceInfo.InstanceStatus.UNKNOWN)
                .setPort(8001)
                .setSecurePort(8002)
                .setMetadata(new HashMap<>())
                .build();

        try
        {
            String urlPath = baseUrkl + "apps/" + appName;
            System.out.println("urlPath " + urlPath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            Map<String, InstanceInfo> info = new HashMap<>();
            info.put("instance", instanceInfo);

            HttpEntity<Map<String, InstanceInfo>> entity = new HttpEntity<>(info, headers);
            ResponseEntity<String> response = restTemplate.exchange(urlPath, HttpMethod.POST, entity, String.class);
            System.out.println("status " + response.getStatusCode());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "register ~";
    }


}