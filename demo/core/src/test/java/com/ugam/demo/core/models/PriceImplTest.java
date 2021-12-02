package com.ugam.demo.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class PriceImplTest {

    private final AemContext aemContext=new AemContext(ResourceResolverType.JCR_MOCK);

    @BeforeEach
    void setUp() {
         aemContext.load().json("/price.json","/content");
    }

    @Test
    void getPriceType() {
        Resource json = aemContext.currentResource("/content/price");
        Price Price = json.adaptTo(Price.class);
        assertEquals("Economy", Price.getPriceType());
    }


    @Test
    void getPriceTitle() {
        Resource json = aemContext.currentResource("/content/price");
        Price Price = json.adaptTo(Price.class);
        assertEquals("For the individuals", Price.getPriceTitle());
    }

    @Test
    void getPriceNumber() {
        Resource json = aemContext.currentResource("/content/price");
        Price Price = json.adaptTo(Price.class);
        assertEquals("01", Price.getPriceNumber());
    }

    @Test
    void getPriceText1() {
        Resource json = aemContext.currentResource("/content/price");
        Price Price = json.adaptTo(Price.class);
        assertEquals("Secure Online Transfer", Price.getPriceText1());
    }

    @Test
    void getPriceText2() {
        Resource json = aemContext.currentResource("/content/price");
        Price Price = json.adaptTo(Price.class);
        assertEquals("Unlimited Styles for interface", Price.getPriceText2());
    }

    @Test
    void getPriceText3() {
        Resource json = aemContext.currentResource("/content/price");
        Price Price = json.adaptTo(Price.class);
        assertEquals("Reliable Customer Service", Price.getPriceText3());
    }
}