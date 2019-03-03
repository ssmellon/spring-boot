package com.karl.pre.domain;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.LookupService;
import com.netflix.eureka.lease.LeaseManager;
import org.springframework.cloud.client.ServiceInstance;

public class InstanceInfomation
{
    private InstanceInfo info;
    private ServiceInstance instance;
    private InstanceInfo.InstanceStatus status;

    private LeaseManager leaseManager;
    private LookupService lookupService;

}
