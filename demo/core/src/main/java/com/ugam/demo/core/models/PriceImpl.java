package com.ugam.demo.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class,
        adapters = Price.class,
        resourceType = PriceImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PriceImpl implements Price{

    final protected static String RESOURCE_TYPE="ugams/components/content/price";

    @Inject
    String type;

    @Inject
    String num;

    @Inject
    String title;

    @Inject
    String text1;

    @Inject
    String text2;

    @Inject
    String text3;

    @Override
    public String getPriceType() {
        return type;
    }

    @Override
    public String getPriceTitle() {
        return title;
    }

    @Override
    public String getPriceNumber() {
        return num;
    }

    @Override
    public String getPriceText1() {
        return text1;
    }

    @Override
    public String getPriceText2() {
        return text2;
    }

    @Override
    public String getPriceText3() {
        return text3;
    }
}
