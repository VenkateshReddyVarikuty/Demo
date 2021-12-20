package com.ugam.demo.core.services;

public interface OSGIConfig {
    public String getServiceName();
    public int getServiceID();
    public boolean isService();
    public String[] getCountries() ;
    public String getRunModes();
}
