package com.ugam.demo.core.services;

import com.ugam.demo.core.config.UgamOSGIConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = OSGIConfig.class,immediate = true)
@Designate(ocd = UgamOSGIConfig.class )
public class OSGIConfigImpl implements OSGIConfig {

    private String serviceName;
    private int serviceID;
    private boolean isService;
    private String[] countries;
    private String runModes;

    @Activate
    protected void activate(UgamOSGIConfig ugamOSGIConfig){
        serviceName=ugamOSGIConfig.serviceName();
        serviceID=ugamOSGIConfig.serviceID();
        isService=ugamOSGIConfig.isService();
        countries=ugamOSGIConfig.serviceCountry();
        runModes=ugamOSGIConfig.serviceRunMode();
    }


    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public int getServiceID() {
        return serviceID;
    }

    @Override
    public boolean isService() {
        return isService;
    }

    @Override
    public String[] getCountries() {
        return countries;
    }

    @Override
    public String getRunModes() {
        return runModes;
    }
}
