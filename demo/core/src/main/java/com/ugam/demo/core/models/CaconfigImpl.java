package com.ugam.demo.core.models;

import com.ugam.demo.core.config.UgamCAconfig;
import com.ugam.demo.core.models.Caconfig;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = Caconfig.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CaconfigImpl implements Caconfig {
    private static final Logger LOG = LoggerFactory.getLogger(CaconfigImpl.class);

    @SlingObject
    ResourceResolver resourceResolver;

    @ScriptVariable
    Page currentPage;

    private String siteCountry;
    private String siteAdmin;
    private String siteSection;

    @Override
    public String getSiteCountry() {
        return siteCountry;
    }

    @Override
    public String getSiteAdmin() {
        return siteAdmin;
    }

    @Override
    public String getSiteSection() {
        return siteSection;
    }

    @PostConstruct
    public void postConstruct() {
        UgamCAconfig caConfig = getContextAwareConfig(currentPage.getPath(), resourceResolver);
        siteCountry = caConfig.siteCountry();
        siteAdmin = caConfig.siteAdmin();
        siteSection = caConfig.siteSection();
    }

    public UgamCAconfig getContextAwareConfig(String currentPage, ResourceResolver resourceResolver) {
        String currentPath = StringUtils.isNoneBlank(currentPage) ? currentPage : StringUtils.EMPTY;
        Resource contentResource = resourceResolver.getResource(currentPath);
        if (contentResource != null) {
            ConfigurationBuilder configurationBuilder = contentResource.adaptTo(ConfigurationBuilder.class);
            if (configurationBuilder != null) {
                return configurationBuilder.as(UgamCAconfig.class);
            }
        }
        return null;
    }
}