package com.loryi.simulate.controller;

import com.alibaba.fastjson.JSONObject;
import com.loryi.simulate.util.CustomizingJsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author: loryi
 * @description: 模拟用户接口
 * @create: 2020/04/03 11:15
 **/
@RestController
@RequestMapping("/")
@Api(tags = "模拟接口")
@ApiIgnore
public class UserApiController {


    /**
     * 模拟根据ticket获取用户信息接口
     *
     * @param ticket
     * @return
     */
    @GetMapping("/rest/info/getSsoUserByTicket")
    @ApiOperation(value = "模拟根据ticket获取用户信息接口", httpMethod = "GET", notes = "模拟根据ticket获取用户信息接口")
    public String getSsoUserByTicket(@ApiParam(name = "ticket", value = "ticket") @RequestParam(value = "ticket") String ticket) {
        //调用获取模拟数据工具类获取数据
        JSONObject jsonObj = CustomizingJsonUtil.getUserJsonObj(CustomizingJsonUtil.BY_TICKET, ticket);
        return jsonObj.toJSONString();
    }

    /**
     * 模拟根据session获取用户信息接口
     *
     * @param sessionId
     * @return
     */
    @GetMapping("/rest/info/getSsoUserBySessionId")
    @ApiOperation(value = "模拟根据session获取用户信息接口", httpMethod = "GET", notes = "模拟根据session获取用户信息接口")
    public String getSsoUserBySessionId(@ApiParam(name = "sessionId", value = "sessionId") @RequestParam(value = "sessionId") String sessionId) {
        //调用获取模拟数据工具类获取数据
        JSONObject jsonObj = CustomizingJsonUtil.getUserJsonObj(CustomizingJsonUtil.BY_SESSIONID, sessionId);
        return jsonObj.toJSONString();
    }

    /**
     * 模拟材料库材料入录
     *
     * @return
     */
    @PostMapping("/repservices/v1/license/main/materials/addMaterial")
    @ApiOperation(value = "模拟材料库材料入录", httpMethod = "POST", notes = "模拟材料库材料入录")
    public String addMaterial(@RequestBody Map map) {
        JSONObject jsonObject = new JSONObject();
        JSONObject message = new JSONObject();
        message.put("message", "成功录入材料信息");
        message.put("messageNO", "MD-CLXX-0003");
        jsonObject.put("data", "50f41d51-065b-475a-8847-2f967ca197c1");
        jsonObject.put("message", message);
        System.out.println("模拟材料库材料入录");
        return jsonObject.toJSONString();
    }

    /**
     * 模拟材料库
     * 通过持有者证件号获取材料
     *
     * @param map
     * @return
     */
    @PostMapping("/repservices/v1/license/main/materials/getMaterialByCertificateNo")
    @ApiOperation(value = "模拟材料库通过持有者证件号获取材料", httpMethod = "POST", notes = "模拟材料库通过持有者证件号获取材料")
    public String getMaterialByCertificateNo(Map map) {
        System.out.println("模拟材料库通过持有者证件号获取材料");
        return "{\"data\": [{\"attachments\": [{\"fileName\": \"搞钱鹏3.pdf\",\"fileType\": \"pdf\",\"lastModify\": \"2019-10-04 10:45:18\",\"objectId\": \"group1/M00/00/00/wKjhCl2V3Z6AL70IAAAAA0BlMA0076.pdf\",\"sequence\": \"333\"}],\"certificateNameCode\": \"33\",\"certificateNo\": \"ss\",\"certificateType\": \"33\",\"holderName\": \"33\",\"holderType\": \"33\",\"lastModify\": 1570157114264,\"licenseNo\": \"3d1842d6-b2ab-47aa-9cb7-049ccb8e6637\"}],\"message\": {\"message\": \"成功查询材料信息\",\"messageNO\": \"MD-CLXX-0004\"}}";
    }

    /**
     * 模拟材料库
     * 材料变更
     *
     * @param map
     * @return
     */
    @PostMapping("/repservices/v1/license/main/materials/createMaterial")
    @ApiOperation(value = "模拟材料库材料变更", httpMethod = "POST", notes = "模拟材料库材料变更")
    public String createMaterial(Map map) {
        System.out.println("模拟材料库材料变更");
        return "{\"data\": \"f265f5ea-ace3-496f-926d-fcb25653d04a\",\"message\": {\"message\": \"成功更新材料信息\",\"messageNO\": \"MD-CLXX-0002\"}}";
    }

    /**
     * 模拟材料库
     * 通过材料名称查询
     *
     * @param map
     * @return
     */
    @PostMapping("/repservices/v1/license/main/materials/getMaterialByCertificateName")
    @ApiOperation(value = "模拟材料库通过材料名称查询", httpMethod = "POST", notes = "模拟材料库通过材料名称查询")
    public String getMaterialByCertificateName(Map map) {
        System.out.println("模拟材料库通过材料名称查询");
        return "{\"data\": [{\"certificateName\": \"ss\",\"certificateNameCode\": \"1\",\"issuingOrganization\": \"1\",\"issuingOrganizationCode\": \"1\"}],\"message\": {\"message\": \"成功获取目录列表\",\"messageNO\": \"MD-MLXX-0000\"}}";
    }

    /**
     * 模拟材料库
     * 获取材料附件
     *
     * @param map
     * @return
     */
    @PostMapping("/repservices/v1/license/main/materials/getLicenseFileByLicenseNo")
    @ApiOperation(value = "模拟材料库获取材料附件", httpMethod = "POST", notes = "模拟材料库获取材料附件")
    public String getLicenseFileByLicenseNo(Map map) {
        System.out.println("模拟材料库获取材料附件");
        return "{\"returnData\": {\"data\":[{\"fileName\":\"搞钱鹏6.jpg\",\"fileType\":\"jpg\",\"sequence\":\"666\"}],\"message\":{\"message\":\"成功查询材料信息\",\"messageNO\":\"MD-CLXX-0004\"}},\"attachment\": [{\"fileName\": \"搞钱鹏6.jpg\",\"fileType\": \"jpg\",\"sequence\": \"666\",\"base64FileContent\": \"bbb\"}]}";
    }

    /**
     * 模拟材料库
     * 增加材料
     *
     * @param map
     * @return
     */
    @PostMapping("/repservices/v1/license/main/materials/addExtMaterial")
    @ApiOperation(value = "模拟材料库增加材料", httpMethod = "POST", notes = "模拟材料库增加材料")
    public String addExtMaterial(Map map) {
        System.out.println("模拟材料库增加材料");
        return "{\"data\": \"50f41d51-065b-475a-8847-2f967ca197c1\",\"message\": {\"message\": \"成功录入材料信息\",\"messageNO\": \"MD-CLXX-0003\"}}";
    }

    /**
     * 模拟中软推送数据接口
     *
     * @param map
     * @return
     */
    @PostMapping("/api/ythxtWHandleInfoApi/syncData")
    public String syncData(Map map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        return jsonObject.toJSONString();
    }

}
