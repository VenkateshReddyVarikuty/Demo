package com.ugam.demo.core.config;
import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;
@Configuration(label="",description = "")
public @interface UgamCAconfig {

    @Property(label = "siteCountry",
            description = "Country Name")
    String siteCountry() default "us";
    @Property(label = "siteAdmin",
            description = "Site Admin")
    String siteAdmin() default "ugam";
    @Property(label = "siteSection",
            description = "Site Section")
    String siteSection() default "Site Section";

}