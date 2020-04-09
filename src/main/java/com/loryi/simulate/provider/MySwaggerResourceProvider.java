package com.loryi.simulate.provider;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ClassSupport;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;

/**
 * @author: loryi
 * @description: swagger资源提供
 * @create: 2020/04/08 15:51
 **/
@Primary
@Component
public class MySwaggerResourceProvider implements SwaggerResourcesProvider {

    private final String swagger1Url;
    private final String swagger2Url;
    @VisibleForTesting
    boolean swagger1Available;
    @VisibleForTesting
    boolean swagger2Available;
    private final DocumentationCache documentationCache;

    @Autowired
    public MySwaggerResourceProvider(Environment environment, DocumentationCache documentationCache) {
        this.swagger1Url = environment.getProperty("springfox.documentation.swagger.v1.path", "/api-docs");
        this.swagger2Url = environment.getProperty("springfox.documentation.swagger.path", "/v2/api-docs");
        this.swagger1Available = ClassSupport.classByName("springfox.documentation.swagger1.web.Swagger1Controller").isPresent();
        this.swagger2Available = ClassSupport.classByName("springfox.documentation.swagger2.web.Swagger2Controller").isPresent();
        this.documentationCache = documentationCache;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList();
        Iterator var2 = this.documentationCache.all().entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, Documentation> entry = (Map.Entry)var2.next();
            String swaggerGroup = (String)entry.getKey();
            SwaggerResource swaggerResource;
            if (this.swagger1Available) {
                swaggerResource = this.resource(swaggerGroup, this.swagger1Url);
                swaggerResource.setSwaggerVersion("1.2");
                resources.add(swaggerResource);
            }

            if (this.swagger2Available) {
                swaggerResource = this.resource(swaggerGroup, this.swagger2Url);
                swaggerResource.setSwaggerVersion("2.0");
                resources.add(swaggerResource);
            }
        }

        Collections.sort(resources);
        return resources;
    }

    private SwaggerResource resource(String swaggerGroup, String baseUrl) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(swaggerGroup);
        swaggerResource.setUrl(this.swaggerLocation(baseUrl, swaggerGroup));
        return swaggerResource;
    }

    private String swaggerLocation(String swaggerUrl, String swaggerGroup) {
        String base = (String) Optional.of(swaggerUrl).get();
        return "default".equals(swaggerGroup) ? base : base + "?group=" + swaggerGroup;
    }

}
