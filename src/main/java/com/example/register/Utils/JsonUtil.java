package com.example.register.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import net.bytebuddy.description.method.MethodDescription.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * 處理 Json
 * 需要json-lib-2.4-jdk13.jar,commons-logging-1.1.1.jar,ezmorph-1.0.6.jar,commons-beanutils-1.8.3.jar,commons-collections-3.2.1.jar
 *
 */
public class JsonUtil {
	
	private static Gson gson = new Gson();
	/*
	 * 萬用的jsonstr轉物件 
	 */
	public static Object jsonStrToObject( String jsonStr ) {
		Object retVal = null;;
		
		HashMap ht = new HashMap();
		List ls = new ArrayList();
		
		if( jsonStr.startsWith("[") ) { 		
			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			for(int i=0;i<jsonArray.size();i++){
				if( jsonArray.get(i).getClass().getName().equals("net.sf.json.JSONObject") ) {
					ls.add(jsonStrToObject(jsonArray.get(i).toString())); 
				}else{
					ls.add(jsonArray.get(i)); 
				}
			}		
			retVal = ls;
		}else{
			JSONObject jsonObject =   JSONObject.fromObject(jsonStr);
			Iterator<String> keyIter  =  jsonObject.keys();
			String key = "";
			Object value;
			while ( keyIter.hasNext()) {
				 key = (String)keyIter.next();
				 value = jsonObject.get(key);
				 if( jsonObject.get(key).getClass().getName().equals("net.sf.json.JSONArray") ) {
					ht.put(key, jsonStrToObject(value.toString()));
				 }else if( jsonObject.get(key).getClass().getName().equals("net.sf.json.JSONObject") ) {
					ht.put(key, jsonStrToObject(value.toString())); 
				 }else{
				 	ht.put(key, value);
				 }
			 }
			retVal = ht;
		}
		
		return retVal;
	}
	
	/**
	 * 取得 json 陣列
	 * <pre>
	 * 例子：
	 *  String jsonStr = "[{'address':'wengchi address','age':0,'name':'王文奇','rmk':''},{'address':'wengchi address','age':1,'name':'王文奇1','rmk':''}]";
	 *  JSONArray jsonArray = JsonUtil.getJsonArray(jsonStr);
	 *  	for(int i=0;i<jsonArray.size();i++){
	*		    JSONObject j = jsonArray.getJSONObject(i);
	*		    System.out.println(j.getString("name"));
	*		    System.out.println(j.getInt("age"));
	*		    System.out.println(j.getString("address"));
	*	   }
	 * </pre>
	 * @param jsonStr json字串
	 * @return
	 */
	public static JSONArray getJsonArray( String jsonStr ){
		  return JSONArray.fromObject(jsonStr);
	}
	
	/**
	 * json 轉成 java 物件陣列
	 * <pre>
	 * 範例：
	 * String jsonStr = "[{'address':'中山北路走九遍','age':34,'name':'王文奇','rmk':''},{'address':'wengchi address','age':12,'name':'王文奇1','rmk':''}]";
	 * List stus = JsonUtil.getJsonArray(jsonStr,StudentBean.class);
	 * for(int i=0;i<stus.size();i++){
	*		StudentBean bean = (StudentBean)stus.get(i);
	*		System.out.println(bean.getName());
	*		System.out.println(bean.getAge());
	*		System.out.println(bean.getAddress());
	*	}
	*</pre>
	 * @param jsonStr
	 * @param pojoClass beanClass
	 * @return List pojoClass陣列
	 */
	public static List getJsonArray( String jsonStr,Class pojoClass ){
			List l = new ArrayList();
			JSONArray jsonArray = getJsonArray(jsonStr);
			for(int i=0;i<jsonArray.size();i++){
				JSONObject j = jsonArray.getJSONObject(i);
				l.add( JSONObject.toBean(j, pojoClass) );
			}
			return l;
	}
	
	

	/*
	 * 將JSON字串轉乘LIST-MAP
	 */
	public static List jsonStrToListMap( String jsonStr ){
			List l = new ArrayList();
			JSONArray jsonArray = getJsonArray(jsonStr);
			for(int i=0;i<jsonArray.size();i++){
				JSONObject j = jsonArray.getJSONObject(i);
				l.add( jsonStrToMap(j.toString()) );
			}
			return l;
	}
	
	/***
	 * 取得單一 JSONObject 物件
	 * <pre>
	 * String jsonStr = "{'name':'王文奇','age':16}";
	 * JSONObject j = JsonUtil.getJSONObject(jsonStr);
	 * System.out.println((String)j.get("name"));
	 * System.out.println(j.getInt("age"));
	 * </pre>
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject getJSONObject(String jsonStr){
		return   JSONObject.fromObject(jsonStr);
	}
	
	/**
	 * 將 json 字串轉成 map 物件
	 * <pre>
	 * String jsonStr = "{'name':'王文奇','age':16}";
	 * Map m =  JsonUtil.jsonStrToMap(jsonStr);
	 * System.out.println(m.get("name"));
	 * System.out.println(m.get("age"));
	 * </pre>
	 * @param jsonStr
	 * @return 
	 * @return
	 */
	public static HashMap<String, Object> jsonStrToMap(String jsonStr){
		HashMap<String, Object> retVal = new HashMap<String, Object>();
		JSONObject jsonObject =   JSONObject.fromObject(jsonStr);
		Iterator<String> keyIter  =  jsonObject.keys();
		String key = "";
		Object value;
		while ( keyIter.hasNext()) {
			 key = (String)keyIter.next();
			 value = jsonObject.get(key);
			 
			 retVal.put(key, value);
		 }
		return retVal;
	}
	
	/**
	 * 將 json 字串 陣列 轉成 hash list
	 * @param jsonStrList
	 * @return
	 */
	public static List<HashMap> jsonStrArrayToMapList(String[] jsonStrList){
		 List<HashMap> retVal = new  ArrayList<HashMap>();
		 for(String str: jsonStrList ){
			 retVal.add(jsonStrToMap( str ));
		 }
		 return retVal;
	}
	
	public static List<MyHashMap> jsonStrArrayToMyMapList(String[] jsonStrList){
		 List<MyHashMap> retVal = new  ArrayList<MyHashMap>();
		 for(String str: jsonStrList ){
			 retVal.add(jsonStrToMyHashMap( str ));
		 }
		 return retVal;
	}
	
	public static <T extends Map> T jsonStrToMyHashMap(String jsonStr){
		MyHashMap retVal = new MyHashMap();
		JSONObject jsonObject =   JSONObject.fromObject(jsonStr);
		Iterator keyIter  =  jsonObject.keys();
		String key = "";
		Object value;
		while ( keyIter.hasNext()) {
			 key = (String)keyIter.next();
			 value = jsonObject.get(key);
			 retVal.put(key, value);
		 }
		return (T)retVal;
	}
	
	
	/***
	 * 取得單一JAVA 物件
	 * <pre>
	 * String jsonStr = "{'name':'王文奇','age':16}";
	 * StudentBean j = (StudentBean)JsonUtil.getObject(jsonStr,StudentBean.class);
	 * System.out.println((String)j.getName());
	 * System.out.println(j.getAge());
	 * </pre>
	 * @param jsonStr
	 * @return
	 */
	public static Object getObject(String jsonStr,Class pojoClass){
		return JSONObject.toBean(JSONObject.fromObject(jsonStr),pojoClass );
	}
	
	
	/***
	 * 將字串轉成  Bean 物件
	 * <pre>
	 * String jsonStr = "{'name':'王文奇','age':16}";
	 * StudentBean j = JsonUtil.toBean(jsonStr,StudentBean.class);
	 * System.out.println(j.getName());
	 * System.out.println(j.getAge());
	 * 
	 * 
	 * HashMap ddd = JsonUtil.toBean(jsonStr, HashMap.class);
	 * System.out.println(ddd.get("name"));
	 * 
	 * </pre>
	 * @param jsonStr
	 * @param type 類別
	 * @return
	 */
	public static <T> T toBean(String jsonStr,Class<T> type){
		//Type type1 = new TypeToken<AAA>(){}.getType();
		return gson.fromJson(jsonStr, type);
	}
	
	/**
	 * 將 map 裡面的值轉成 json 字串
	 * <pre>
	*		Map<String, Object> map = new HashMap<String, Object>();
	*		map.put("name","wengchi");
	*		map.put("age","16");
	*		System.out.println(JsonUtil.toJsonStr(map));
	 * </pre>
	 * @param map
	 * @return
	 */
	public static String toJsonStr(Object map){
		return JSONSerializer.toJSON(map).toString();
	}
	

	
	/**
	 * 將 List 裡面的值轉成 json 字串
	 * <pre>
	 * List<StudentBean> list = new ArrayList<StudentBean>();	
	*		for (int i=0;i<2;i++){
	*			StudentBean bean = new StudentBean();
	*			bean.setName("wengchi "+ i);
	*			bean.setAddress("wengchi address");
	*			bean.setAge(i);
	*			list.add(bean);	
	*		}
	* System.out.println(JsonUtil.toJsonStr(list));
	*
	 * </pre>
	 * @param list
	 * @return
	 */
	public static String toJsonStr(List list){
		return JSONSerializer.toJSON(list).toString();
	}
	
}