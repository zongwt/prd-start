package com.prd.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    /**
     * 判断一个字符串是否有值，空格也不算有值
     * @author cuijy
     * @param str
     * @return
     */
    public static boolean availableStr(String str){
        return (str!=null && !"".equals(str) && !"null".equals(str));
    }

    public static boolean isBlank(Object str){
        if(str == null) {
            return false;
        } else {
            return true;
        }
     }

     public static JSONArray coverToJson(List<Map<String, Object>> data){
        JSONArray res = new JSONArray();
        for(Map<String, Object> map : data){
            JSONObject json = new JSONObject();
            List<String> keys = new ArrayList<String>(map.keySet());
            for(String k : keys){
                json.put(k, map.get(k));
            }
            res.add(json);
        }
        return res;
     }
}
