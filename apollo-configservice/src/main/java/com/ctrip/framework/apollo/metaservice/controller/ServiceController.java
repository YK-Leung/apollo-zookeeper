package com.ctrip.framework.apollo.metaservice.controller;

import com.ctrip.framework.apollo.core.dto.ServiceDTO;
import com.ctrip.framework.apollo.metaservice.service.DiscoveryService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services")
public class ServiceController {

  private final DiscoveryService discoveryService;

//  private static Function<InstanceInfo, ServiceDTO> instanceInfoToServiceDTOFunc = instance -> {
//    ServiceDTO service = new ServiceDTO();
//    service.setAppName(instance.getAppName());
//    service.setInstanceId(instance.getInstanceId());
//    service.setHomepageUrl(instance.getHomePageUrl());
//    return service;
//  };

  public ServiceController(final DiscoveryService discoveryService) {
    this.discoveryService = discoveryService;
  }


  @RequestMapping("/meta")
  public List<ServiceDTO> getMetaService() {
    List<ServiceInstance> instances = discoveryService.getMetaServiceInstances();
    List<ServiceDTO> result = instances.stream().map(new Function<ServiceInstance, ServiceDTO>() {
      @Override
      public ServiceDTO apply(ServiceInstance instance) {
        ServiceDTO service = new ServiceDTO();
        service.setAppName(instance.getServiceId());
        service.setInstanceId(instance.getHost()+"|"+instance.getScheme()+"|"+instance.getMetadata()+"|"+instance.getPort()+"&");
        service.setHomepageUrl(instance.getUri().toString());
        return service;
      }
    }).collect(Collectors.toList());
    return result;
  }

  @RequestMapping("/config")
  public List<ServiceDTO> getConfigService(
      @RequestParam(value = "appId", defaultValue = "") String appId,
      @RequestParam(value = "ip", required = false) String clientIp) {
    List<ServiceInstance> instances = discoveryService.getConfigServiceInstances();
    List<ServiceDTO> result = instances.stream().map(new Function<ServiceInstance, ServiceDTO>() {
      @Override
      public ServiceDTO apply(ServiceInstance instance) {
        ServiceDTO service = new ServiceDTO();
        service.setAppName(instance.getServiceId());
        service.setInstanceId("config"+"|"+instance.getHost()+"|"+instance.getScheme()+"|"+instance.getMetadata()+"|"+instance.getPort()+"&");
        service.setHomepageUrl(instance.getUri().toString());
        return service;
      }
    }).collect(Collectors.toList());
    return result;
  }

  @RequestMapping("/admin")
  public List<ServiceDTO> getAdminService() {
    List<ServiceInstance> instances = discoveryService.getAdminServiceInstances();
    List<ServiceDTO> result = instances.stream().map(new Function<ServiceInstance, ServiceDTO>() {
      @Override
      public ServiceDTO apply(ServiceInstance instance) {
        ServiceDTO service = new ServiceDTO();
        service.setAppName(instance.getServiceId());
        service.setInstanceId("admin"+"|"+instance.getHost()+"|"+instance.getScheme()+"|"+instance.getMetadata()+"|"+instance.getPort()+"&");
        service.setHomepageUrl(instance.getUri().toString());
        return service;
      }
    }).collect(Collectors.toList());
    return result;
  }
}
