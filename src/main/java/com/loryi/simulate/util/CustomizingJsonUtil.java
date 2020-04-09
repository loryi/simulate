package com.loryi.simulate.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 自定义解析json文件
 */
public class CustomizingJsonUtil {
    public static final String BY_TICKET = "ticket";
    public static final String BY_SESSIONID = "sessionId";
    public static final String BY_PERSONUUID = "personUuid";

    private static Logger logger = LoggerFactory.getLogger(CustomizingJsonUtil.class);
    private static JSONObject objFile = getJsonObjByJsonFile("/data/data.json");

    public static JSONObject getJsonObjByJsonFile(String path){
        JSONObject jsonObject = new JSONObject();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(CustomizingJsonUtil.class.getResourceAsStream(path),"UTF-8"));
            String line = null;
            StringBuilder stringBuilder  = new StringBuilder();
            while((line = bufferedReader.readLine())!= null){
                stringBuilder.append(System.lineSeparator()+line);
            }
            bufferedReader.close();
            jsonObject = JSONObject.parseObject(stringBuilder.toString());
        } catch (Exception e) {
            logger.error("json文件转换为json对象过程中发生错误！");
        }
        return jsonObject;
    }

    public static JSONObject getUserJsonObj(String type,String value){
        JSONObject result = new JSONObject();
        if(objFile.containsKey("users")){
            JSONArray usersArray = objFile.getJSONArray("users");
            for (int i = 0;i < usersArray.size();i++){
                JSONObject userObj = usersArray.getJSONObject(i);
                Object typeValue = userObj.get(type);
                if(typeValue != null && typeValue.equals(value)){
                    return userObj;
                }
            }

        }
        return result;
    }

    public static JSONObject getDictJsonObj(String parentCode ,String tableName){
        JSONObject result = new JSONObject();
        if(objFile.containsKey("dicts")){
            JSONArray dictArray = objFile.getJSONArray("dicts");
            try {
                for (int i = 0;i < dictArray.size();i++){
                    JSONObject dictObj = dictArray.getJSONObject(i);
                    String dictParentCode = (String)dictObj.get("parentCode");
                    String dictTableName = (String)dictObj.get("tableName");
                    if((dictParentCode != null && dictParentCode.equals(parentCode)) && (dictTableName != null && dictTableName.equals(tableName))){
                        return dictObj;
                    }
                }
            } catch (Exception e) {
                logger.error("处理json数据出错！");
            }

        }
        return result;
    }

}
