package com.ctrip.framework.apollo.metaservice.service;

import com.ctrip.framework.apollo.core.ServiceNameConsts;
import com.ctrip.framework.apollo.tracer.Tracer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DiscoveryService {

  private final ZookeeperDiscoveryClient zookeeperDiscoveryClient;

  public DiscoveryService(final ZookeeperDiscoveryClient zookeeperDiscoveryClient) {
    this.zookeeperDiscoveryClient = zookeeperDiscoveryClient;
  }

  public List<ServiceInstance> getConfigServiceInstances() {
    List<ServiceInstance> application = zookeeperDiscoveryClient.getInstances(ServiceNameConsts.APOLLO_CONFIGSERVICE);
    if (application == null) {
      Tracer.logEvent("Apollo.ZkDiscovery.NotFound", ServiceNameConsts.APOLLO_CONFIGSERVICE);
    }
    return application != null ? application : Collections.emptyList();
  }

  public List<ServiceInstance> getMetaServiceInstances() {
    List<ServiceInstance> application = zookeeperDiscoveryClient.getInstances(ServiceNameConsts.APOLLO_METASERVICE);
    if (application == null) {
      Tracer.logEvent("Apollo.ZkDiscovery.NotFound", ServiceNameConsts.APOLLO_METASERVICE);
    }
    return application != null ? application : Collections.emptyList();
  }

  public List<ServiceInstance> getAdminServiceInstances() {
    List<ServiceInstance> application = zookeeperDiscoveryClient.getInstances(ServiceNameConsts.APOLLO_ADMINSERVICE);
    if (application == null) {
      Tracer.logEvent("Apollo.ZkDiscovery.NotFound", ServiceNameConsts.APOLLO_ADMINSERVICE);
    }
    return application != null ? application : Collections.emptyList();
  }
}
