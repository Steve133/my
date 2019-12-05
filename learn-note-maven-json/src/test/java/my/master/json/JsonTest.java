package my.master.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTest {

	public static void main(String[] args) {
		String a ="[{\"menuParent\":0,\"menuName\":\"管理员管理\",\"id\":1},{\"menuParent\":1,\"menuName\":\"管理员列表\",\"id\":2},{\"menuParent\":1,\"menuName\":\"角色管理\",\"id\":3}]";
		
		JSONArray fromObject = JSONArray.fromObject(a);
		System.out.println("解析前的json");
		System.out.println(fromObject);
		
		JSONObject show = new JSONObject();
		for (Object object : fromObject) {
			JSONObject fromObject2 = JSONObject.fromObject(object);
			Object id = fromObject2.get("id");
			Object menuName = fromObject2.get("menuName");
			Object menuParent = fromObject2.get("menuParent");
			if(menuParent.toString().equals("0")) {
				show.put("id", id);
				show.put("menuName", menuName);
				show.put("menuParent", menuParent);
			}
		}
		
		JSONArray child = new JSONArray();
		for (Object object : fromObject) {
			JSONObject fromObject2 = JSONObject.fromObject(object);
			Object menuParent = fromObject2.get("menuParent");
			if(!menuParent.toString().equals("0")&&show.getString("id").equals(menuParent.toString())) {
				child.add(fromObject2);
			}
		}
		show.put("child", child);
		System.out.println("解析后的json");
		System.out.println(show);
		
	}
}
