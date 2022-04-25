package com.example.register.Utils;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * 繼承 HashMap 
 * 
 * 特點：
 * 		1. put key ，key 全轉小寫
 * 		2. get 時 key不分大小寫 都可取到
 *
 */
public class MyHashMap extends LinkedCaseInsensitiveMap<Object> {

	@Override
	public Object put(String key, Object value) {
		return super.put(key.toLowerCase(), value==null ? "" :value);
	}
}
