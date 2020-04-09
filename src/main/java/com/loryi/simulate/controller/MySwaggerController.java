package com.loryi.simulate.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.loryi.simulate.util.SimulateHelperConst;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.PropertySourcedMapping;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger.common.HostNameProvider;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: loryi
 * @description: swagger-ui.html 页面显示接口数据接口
 * @create: 2020/04/08 11:27
 **/
@Primary
@ApiIgnore
@Controller
public class MySwaggerController {

    private final ServiceModelToSwagger2Mapper mapper;
    private final DocumentationCache documentationCache;
    private final JsonSerializer jsonSerializer;
    private final String hostNameOverride;

    @Autowired
    public MySwaggerController(Environment environment, DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer) {
        this.mapper = mapper;
        this.jsonSerializer = jsonSerializer;
        this.documentationCache = documentationCache;
        //获取swagger2的host设置，可能会根据swagger版本不同，路径不同
        this.hostNameOverride = environment.getProperty("springfox.documentation.swagger.v2.host", "DEFAULT");
    }

    @RequestMapping(
            value = {"/swagger/api-docs"},
            method = {RequestMethod.GET},
            produces = {"application/json", "application/hal+json"}
    )
    @PropertySourcedMapping(
            value = "${springfox.documentation.swagger.v2.path}",
            propertyKey = "springfox.documentation.swagger.v2.path"
    )
    @ResponseBody
    public ResponseEntity<Json> getSwaggerJsonData(@RequestParam(value = "design", required = false) String design, HttpServletRequest servletRequest) {
        String groupName = "default";
        Documentation documentation = this.documentationCache.documentationByGroup(groupName);
        if (documentation == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            Swagger swagger = this.mapper.mapDocumentation(documentation);
            UriComponents uriComponents = HostNameProvider.componentsFrom(servletRequest, swagger.getBasePath());
            swagger.basePath(Strings.isNullOrEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());
            if (Strings.isNullOrEmpty(swagger.getHost())) {
                swagger.host(this.hostName(uriComponents));
            }
            //处理swagger-ui.html显示的Tags的内容
//            List<Tag> tags = swagger.getTags();
            Map<String, Path> paths = swagger.getPaths();
            paths.putAll(JSONObject.parseObject(SimulateHelperConst.urlJson.toJSONString(), Map.class));
            swagger.setPaths(paths);
            return new ResponseEntity(this.jsonSerializer.toJson(swagger), HttpStatus.OK);
        }
    }

    private String hostName(UriComponents uriComponents) {
        if ("DEFAULT".equals(this.hostNameOverride)) {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            return port > -1 ? String.format("%s:%d", host, port) : host;
        } else {
            return this.hostNameOverride;
        }
    }
}
