package com.karl.pre.domain;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;

import java.util.Map;

public class EurekaInstanceInfo
{
    private volatile String instanceId;
    private volatile String app;
    private volatile String appGroutName;
    private volatile String ipAddr;
    private volatile String sid;
    private volatile String homePageUrl;
    private volatile String statusPageUrl;
    private volatile String healthCheckUrl;
    private volatile String secureHealthCheckUrl;
    private volatile String vipAddress;
    private volatile String secureVipAddress;

    private volatile int countryId;

    private volatile DataCenterInfo dataCenterInfo;

    private volatile String hostName;
    private volatile String status;

    private volatile LeaseInfo leaseInfo;

    private volatile boolean isCoordinatingDiscoveryServer;
    private volatile long lastUpdatedTimestamp;
    private volatile long lastDirtyTimestamp;

    private volatile InstanceInfo.ActionType actionType;

    private volatile String asgName;

    private volatile InstanceInfo.InstanceStatus overridden_status;

    private volatile Map<String, Object> port;

    private volatile Map<String, Object> securePort;

    private volatile Map<String, String> metadata;

    public EurekaInstanceInfo()
    {
        sid = "na";
        status = "UP";
        countryId = 1;
        lastUpdatedTimestamp = 1529391461000L;
        lastDirtyTimestamp = 1529391461000L;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppGroutName() {
        return appGroutName;
    }

    public void setAppGroutName(String appGroutName) {
        this.appGroutName = appGroutName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    public void setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getSecureHealthCheckUrl() {
        return secureHealthCheckUrl;
    }

    public void setSecureHealthCheckUrl(String secureHealthCheckUrl) {
        this.secureHealthCheckUrl = secureHealthCheckUrl;
    }

    public String getVipAddress() {
        return vipAddress;
    }

    public void setVipAddress(String vipAddress) {
        this.vipAddress = vipAddress;
    }

    public String getSecureVipAddress() {
        return secureVipAddress;
    }

    public void setSecureVipAddress(String secureVipAddress) {
        this.secureVipAddress = secureVipAddress;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public DataCenterInfo getDataCenterInfo() {
        return dataCenterInfo;
    }

    public void setDataCenterInfo(DataCenterInfo dataCenterInfo) {
        this.dataCenterInfo = dataCenterInfo;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LeaseInfo getLeaseInfo() {
        return leaseInfo;
    }

    public void setLeaseInfo(LeaseInfo leaseInfo) {
        this.leaseInfo = leaseInfo;
    }

    public boolean isCoordinatingDiscoveryServer() {
        return isCoordinatingDiscoveryServer;
    }

    public void setCoordinatingDiscoveryServer(boolean coordinatingDiscoveryServer) {
        isCoordinatingDiscoveryServer = coordinatingDiscoveryServer;
    }

    public long getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(long lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public long getLastDirtyTimestamp() {
        return lastDirtyTimestamp;
    }

    public void setLastDirtyTimestamp(long lastDirtyTimestamp) {
        this.lastDirtyTimestamp = lastDirtyTimestamp;
    }

    public InstanceInfo.ActionType getActionType() {
        return actionType;
    }

    public void setActionType(InstanceInfo.ActionType actionType) {
        this.actionType = actionType;
    }

    public String getAsgName() {
        return asgName;
    }

    public void setAsgName(String asgName) {
        this.asgName = asgName;
    }

    public InstanceInfo.InstanceStatus getOverridden_status() {
        return overridden_status;
    }

    public void setOverridden_status(InstanceInfo.InstanceStatus overridden_status) {
        this.overridden_status = overridden_status;
    }

    public Map<String, Object> getPort() {
        return port;
    }

    public void setPort(Map<String, Object> port) {
        this.port = port;
    }

    public Map<String, Object> getSecurePort() {
        return securePort;
    }

    public void setSecurePort(Map<String, Object> securePort) {
        this.securePort = securePort;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
