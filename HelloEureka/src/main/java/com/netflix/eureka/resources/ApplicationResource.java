//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.netflix.eureka.resources;

import com.netflix.appinfo.AmazonInfo;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.EurekaAccept;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.UniqueIdentifier;
import com.netflix.appinfo.AmazonInfo.MetaDataKey;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.Version;
import com.netflix.eureka.registry.Key;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.registry.ResponseCache;
import com.netflix.eureka.registry.Key.EntityType;
import com.netflix.eureka.registry.Key.KeyType;
import com.netflix.eureka.util.EurekaMonitors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces({"application/xml", "application/json"})
public class ApplicationResource {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationResource.class);
    private final String appName;
    private final EurekaServerConfig serverConfig;
    private final PeerAwareInstanceRegistry registry;
    private final ResponseCache responseCache;

    ApplicationResource(String appName, EurekaServerConfig serverConfig, PeerAwareInstanceRegistry registry) {
        this.appName = appName.toUpperCase();
        this.serverConfig = serverConfig;
        this.registry = registry;
        this.responseCache = registry.getResponseCache();
    }

    public String getAppName() {
        return this.appName;
    }

    @GET
    public Response getApplication(@PathParam("version") String version, @HeaderParam("Accept") String acceptHeader, @HeaderParam("X-Eureka-Accept") String eurekaAccept) {
        if (!this.registry.shouldAllowAccess(false)) {
            return Response.status(Status.FORBIDDEN).build();
        } else {
            EurekaMonitors.GET_APPLICATION.increment();
            CurrentRequestVersion.set(Version.toEnum(version));
            KeyType keyType = KeyType.JSON;
            if (acceptHeader == null || !acceptHeader.contains("json")) {
                keyType = KeyType.XML;
            }

            Key cacheKey = new Key(EntityType.Application, this.appName, keyType, CurrentRequestVersion.get(), EurekaAccept.fromString(eurekaAccept));
            String payLoad = this.responseCache.get(cacheKey);
            if (payLoad != null) {
                logger.debug("Found: {}", this.appName);
                return Response.ok(payLoad).build();
            } else {
                logger.debug("Not Found: {}", this.appName);
                return Response.status(Status.NOT_FOUND).build();
            }
        }
    }

    @Path("{id}")
    public InstanceResource getInstanceInfo(@PathParam("id") String id) {
        return new InstanceResource(this, id, this.serverConfig, this.registry);
    }

    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addInstance(InstanceInfo info, @HeaderParam("x-netflix-discovery-replication") String isReplication) {
        logger.debug("Registering instance {} (replication={})", info.getId(), isReplication);
        if (this.isBlank(info.getId())) {
            logger.warn("Missing instanceId");
            return Response.status(400).entity("Missing instanceId").build();
        } else if (this.isBlank(info.getHostName())) {
            logger.warn("Missing hostname");
            return Response.status(400).entity("Missing hostname").build();
        } else if (this.isBlank(info.getIPAddr())) {
            logger.warn("Missing ip address");
            return Response.status(400).entity("Missing ip address").build();
        } else if (this.isBlank(info.getAppName())) {
            logger.warn("Missing appName");
            return Response.status(400).entity("Missing appName").build();
        } else if (!this.appName.equals(info.getAppName())) {
            logger.warn("Mismatched appName, expecting " + this.appName + " but was " + info.getAppName());
            return Response.status(400).entity("Mismatched appName, expecting " + this.appName + " but was " + info.getAppName()).build();
        } else if (info.getDataCenterInfo() == null) {
            logger.warn("Missing dataCenterInfo");
            return Response.status(400).entity("Missing dataCenterInfo").build();
        } else if (info.getDataCenterInfo().getName() == null) {
            logger.warn("Missing dataCenterInfo Name");
            return Response.status(400).entity("Missing dataCenterInfo Name").build();
        } else {
            DataCenterInfo dataCenterInfo = info.getDataCenterInfo();
            if (dataCenterInfo instanceof UniqueIdentifier) {
                String dataCenterInfoId = ((UniqueIdentifier)dataCenterInfo).getId();
                if (this.isBlank(dataCenterInfoId)) {
                    boolean experimental = "true".equalsIgnoreCase(this.serverConfig.getExperimental("registration.validation.dataCenterInfoId"));
                    if (experimental) {
                        String entity = "DataCenterInfo of type " + dataCenterInfo.getClass() + " must contain a valid id";
                        logger.warn("entity " + entity);
                        return Response.status(400).entity(entity).build();
                    }

                    if (dataCenterInfo instanceof AmazonInfo) {
                        AmazonInfo amazonInfo = (AmazonInfo)dataCenterInfo;
                        String effectiveId = amazonInfo.get(MetaDataKey.instanceId);
                        if (effectiveId == null) {
                            amazonInfo.getMetadata().put(MetaDataKey.instanceId.getName(), info.getId());
                        }
                    } else {
                        logger.warn("Registering DataCenterInfo of type {} without an appropriate id", dataCenterInfo.getClass());
                    }
                }
            }

            this.registry.register(info, "true".equals(isReplication));
            return Response.status(204).build();
        }
    }

    String getName() {
        return this.appName;
    }

    private boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }
}
