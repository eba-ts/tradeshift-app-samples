package com.tradeshift.thirdparty.samples.springboot.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PropertySources.class, loader = AnnotationConfigContextLoader.class)
public class PropertySourcesTest {

    @Autowired
    PropertySources propertySources;

    @Test
    public void getTradeshiftAPIDomainName() throws Exception {
        assertNotNull(propertySources.getTradeshiftAPIDomainName());
    }

    @Test
    public void getClientID() throws Exception {
        assertNotNull(propertySources.getClientID());
    }

    @Test
    public void getClientSecret() throws Exception {
        assertNotNull(propertySources.getClientSecret());
    }

    @Test
    public void getRedirectUri() throws Exception {
        assertNotNull(propertySources.getRedirectUri());
    }

}