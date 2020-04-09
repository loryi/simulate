package com.loryi.simulate.controller;

import com.alibaba.fastjson.JSONObject;
import com.loryi.simulate.util.SimulateHelperConst;
import com.loryi.simulate.vo.JsonResult;
import com.loryi.simulate.vo.SimulateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

/**
 * @author: loryi
 * @description: 统一入口模拟接口
 * @create: 2020/04/08 08:41
 **/
@RestController
@Api(tags = "完成版模拟接口v0.9")
public class SimulateController {

    private static final String SESSION_ATTRIBUTE_PATH = "path";

    /**
     * 模拟接口 默认匹配所有的请求类型，所以用RequestMapping,不指定请求方式
     * 实际处理请求接口
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/simulate")
    @ApiIgnore
    public String simulate(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (!StringUtils.isEmpty(session.getAttribute(SESSION_ATTRIBUTE_PATH))) {
            //后续 验证请求参数是否匹配，不匹配返回定义的错误数据，没有定义则返回默认的错误信息
            Map path = (Map) session.getAttribute((String) session.getAttribute(SESSION_ATTRIBUTE_PATH));
            return JSONObject.toJSONString(path.get("responseData"));
        } else {
            return "{\"code\":\"1\",\"message\":\"请求错误\"}";
        }
    }

    @PostMapping("/simulate/add")
    @ApiOperation(value = "模拟接口添加接口", notes = "模拟接口添加接口", httpMethod = "POST")
    public JsonResult addSimulate(@RequestBody SimulateVO vo) {
        if (null != vo) {
            //请求路径不能为空，请求描述不能为空,请求路径不能为空；
            String path = vo.getPath();
            String desc = vo.getDesc();
            String method = vo.getMethod();
            //验证请求方式的正规性
            boolean flag = StringUtils.isEmpty(path) ? false : (StringUtils.isEmpty(desc) ? false : !StringUtils.isEmpty(method));
            if (flag) {
                //全部有值
                //匹配规则进行数据生成
                Operation operation = getDefaultOperation(vo);
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(operation));
                if(!StringUtils.isEmpty(vo.getResult())){
                    jsonObject.put("responseData", JSONObject.parseObject(vo.getResult()));
                }
                JSONObject newPath = new JSONObject();
                newPath.put(method.toLowerCase(), jsonObject);
                SimulateHelperConst.urlJson.put(path, newPath);
                return JsonResult.success("模拟接口添加成功");
            }
            return JsonResult.failure("请求参数缺失必要参数");
        }
        return JsonResult.failure("请求接口失败");
    }


    private Operation getDefaultOperation(SimulateVO vo) {
        Operation operation = new Operation();
        //1.添加默认数据
        //response
        operation.setResponses((Map) JSONObject.parse("{ \"200\": { \"description\": \"OK\", \"schema\": { \"type\": \"string\" }},\"201\": { \"description\": \"Created\" },\"401\": { \"description\": \"Unauthorized\" },\"403\": { \"description\": \"Forbidden\" },\"404\": { \"description\": \"Not Found\" } }"));
        //deprecated
        operation.setDeprecated(false);
        //consumes "application/json"
        operation.setConsumes(Arrays.asList("application/json"));
        //produces "*/*"
        operation.setProduces(Arrays.asList("*/*"));
        //tags
        operation.setTags(Arrays.asList(this.getClass().getAnnotation(Api.class).tags()));
        //2.添加外部传入数据
        //summary
        operation.setSummary(vo.getDesc());
        //operationId
        operation.setOperationId(vo.getDesc());
        //params
        if(!StringUtils.isEmpty(vo.getParams())){
            operation.setParameters(JSONObject.parseArray(vo.getParams(), Parameter.class));
        }
        return operation;
    }

}
