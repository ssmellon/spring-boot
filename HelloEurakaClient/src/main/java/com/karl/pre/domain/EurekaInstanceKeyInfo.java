package com.karl.pre.domain;

import java.util.Objects;

public class EurekaInstanceKeyInfo
{
    private int port;
    private String app;
    private String ipAddr;
    private String hostName;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EurekaInstanceKeyInfo that = (EurekaInstanceKeyInfo) o;
        return port == that.port &&
                Objects.equals(app, that.app) &&
                Objects.equals(ipAddr, that.ipAddr) &&
                Objects.equals(hostName, that.hostName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, app, ipAddr, hostName);
    }
}
