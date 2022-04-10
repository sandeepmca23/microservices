package com.reliance.gstn.ws.caller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileSystemAccessControlUtils {

	public enum Keys {
		catalogs, schemas, tables, role, group, user, catalog
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public static void httpRequest3() {
		Map<String, Map<String, List<Map<String, Object>>>> retmap = new LinkedHashMap<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> obj = mapper.readValue(new File("C:/json/rules.json"), Map.class);
			for (Map.Entry<String, Object> map : obj.entrySet()) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) map.getValue();
				retmap.put(map.getKey(), updateMap(map.getKey(), list));
			}
			String response = mapper.writeValueAsString(retmap);
			System.out.println(response);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static Map<String, List<Map<String, Object>>> updateMap(String key, List<Map<String, Object>> list) {
		Map<String, List<Map<String, Object>>> retmap = new LinkedHashMap<>();
		for (Map<String, Object> ret : list) {
			String keys = null;
			if (key.equals(Keys.catalogs.name())) {
				keys = getKey(ret);
			} else if (key.equals(Keys.schemas.name())) {
				keys = getKey(ret);
			} else if (key.equals(Keys.tables.name())) {
				keys = getKey(ret);
			}
			if (key.equals(Keys.tables.name()) || key.equals(Keys.schemas.name()) || key.equals(Keys.catalogs.name()))
				updatemap(ret, retmap, keys);
		}
		return retmap;
	}

	public static String getKey(Map<String, Object> ret) {
		String keys = null;
		if (ret.containsKey(Keys.role.name())) {
			keys = Keys.role.name();
		} else if (ret.containsKey(Keys.group.name())) {
			keys = Keys.group.name();
		} else if (ret.containsKey(Keys.user.name())) {
			keys = Keys.user.name();
		} else {
			keys = Keys.catalog.name();
		}
		return keys;
	}

	public static void updatemap(Map<String, Object> ret, Map<String, List<Map<String, Object>>> retmap, String key) {
		if (retmap.containsKey(key)) {
			retmap.get(key).add(ret);
		} else {
			List<Map<String, Object>> list1 = new ArrayList<>();
			list1.add(ret);
			retmap.put(key, list1);
		}
	}

	public static void main(String[] args) {
		httpRequest3();
	}

}
