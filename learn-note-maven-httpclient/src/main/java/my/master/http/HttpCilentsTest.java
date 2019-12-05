package my.master.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.master.tool.UnicodeUtils;
import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class HttpCilentsTest {

	private static Logger logger = LoggerFactory.getLogger(HttpCilentsTest.class);

	//百度网盘接口测试
	private static void test1() throws Exception {
		String url = "https://pan.baidu.com/pcloud/friend/getfanslist";

		Map header = new HashMap();
		header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		header.put("Accept-Encoding", "*;q=0");
		header.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		header.put("Cookie", "BAIDUID=AD54F2360679981347F82CAA33B1052E:FG=1; BIDUPSID=AD54F2360679981347F82CAA33B1052E; PSTM=1536633965; PANWEB=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_f5f83a6d8b15775a02760dc5f490bc47=1568613181; BDUSS=EJNSC1qVlNXfmVmTmFmUGNsYXlRZ3BUbFFLai1VT0RofmdwTDJWZFJBbUw0YVpkRVFBQUFBJCQAAAAAAAAAAAEAAADxFkAmsKLLyTEzMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAItUf12LVH9dc; STOKEN=204dfa302602e743f993f6f28c76abd31c18b036aa914d69b8d832047013bd22; SCRC=509e15908c6b82f3fa4a21c5d57cf619; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1568032438,1568610320,1568612522,1568642926; BDCLND=UbjSd5LvhbJ9UcJUG7LqUt7aj5jaIFOm; BDSFRCVID=m6IOJeC626onCY3wAMBu-HAB_ACAPsTTH6aI7H-noAKnJFa6QGIGEG0P_f8g0KubIvYaogKKLgOTHULF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJ-HoD--JC_3jt-k-46B24uDqxbXq-uqWmOZ0l8KtDQoDlOXDnJNXfD0qt7C3qRC026TQMbmWIQHOD3SqtToLfPHy4jKtqoxWmv4KKJxXbLWeIJo5t5z5lF_hUJiBM7MBan7-lRIXKohJh7FM4tW3J0ZyxomtfQxtN4eaDcJ-J8XhC-GDTrP; H_PS_PSSID=1425_21084_29523_29521_29721_29567_29221_26350; delPer=0; PSINO=1; ZD_ENTRY=baidu");
		header.put("Host", "pan.baidu.com");
		header.put("Upgrade-Insecure-Requests", "1");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		Map param = new HashMap();
		param.put("query_uk", "371376031");
		param.put("limit", "24");
		param.put("start", "0");
		param.put("channel", "chunlei");
		param.put("clienttype", "0");
		param.put("web", "1");
		param.put("logid", "MTQ3NDAzNjQwNzg3OTAuNzM1MzMxMDUyMDczMjYxNA==");
		String doGet = HttpClientUtils.doGet(url, param, header,5000,1000,5000);
		System.out.println(doGet);
		System.out.println(UnicodeUtils.decodeUnicode(doGet));
//		{
//			"errno":0,
//			"request_id":6050859632110054201,
//			"total_count":1,
//			"fans_list":[
//	             {
//	            	 "type":1,
//	            	 "fans_uname":"随**风雨",
//	            	 "avatar_url":"https:\/\/ss0.bdstatic.com\/7Ls0a8Sm1A5BphGlnYG\/sys\/portrait\/item\/netdisk.1.1e7ad6fa.ptNlT55npZYY58nSDoEpOg.jpg",
//	            	 "intro":"",
//	            	 "user_type":0,
//	            	 "is_vip":0,
//	            	 "follow_count":0,
//	            	 "fans_count":0,
//	            	 "follow_time":1497414684,
//	            	 "pubshare_count":0,
//	            	 "fans_uk":2937837205,
//	            	 "album_count":0,
//	            	 "fans_suk":"JGMm-EDIeH64ep78OyOB3A"
//	         }]}
	}
	//百度网盘接口测试
	private static void test2() throws Exception {
		String url = "http://yun.baidu.com/pcloud/friend/getfollowlist";

		Map header = new HashMap();
		header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		header.put("Accept-Encoding", "*;q=0");
		header.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		header.put("Connection", "keep-alive");
		header.put("Cookie", "BAIDUID=AD54F2360679981347F82CAA33B1052E:FG=1; BIDUPSID=AD54F2360679981347F82CAA33B1052E; PSTM=1536633965; PANWEB=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_f5f83a6d8b15775a02760dc5f490bc47=1568613181; BDUSS=EJNSC1qVlNXfmVmTmFmUGNsYXlRZ3BUbFFLai1VT0RofmdwTDJWZFJBbUw0YVpkRVFBQUFBJCQAAAAAAAAAAAEAAADxFkAmsKLLyTEzMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAItUf12LVH9dc; STOKEN=204dfa302602e743f993f6f28c76abd31c18b036aa914d69b8d832047013bd22; SCRC=509e15908c6b82f3fa4a21c5d57cf619; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1568032438,1568610320,1568612522,1568642926; BDCLND=UbjSd5LvhbJ9UcJUG7LqUt7aj5jaIFOm; BDSFRCVID=m6IOJeC626onCY3wAMBu-HAB_ACAPsTTH6aI7H-noAKnJFa6QGIGEG0P_f8g0KubIvYaogKKLgOTHULF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJ-HoD--JC_3jt-k-46B24uDqxbXq-uqWmOZ0l8KtDQoDlOXDnJNXfD0qt7C3qRC026TQMbmWIQHOD3SqtToLfPHy4jKtqoxWmv4KKJxXbLWeIJo5t5z5lF_hUJiBM7MBan7-lRIXKohJh7FM4tW3J0ZyxomtfQxtN4eaDcJ-J8XhC-GDTrP; H_PS_PSSID=1425_21084_29523_29521_29721_29567_29221_26350; delPer=0; PSINO=1; ZD_ENTRY=baidu");
		header.put("Host", "pan.baidu.com");
		header.put("Upgrade-Insecure-Requests", "1");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		Map param = new HashMap();
		param.put("query_uk", "371376031");
		param.put("limit", "24");
		param.put("start", "0");
		param.put("bdstoken", "e6f1efec456b92778e70c55ba5d81c3d");
		param.put("channel", "chunlei");
		param.put("clienttype", "0");
		param.put("web", "1");
		param.put("logid", "MTQ3NDA3NDg5NzU4NDAuMzQxNDQyMDY2MjA5NDA4NjU=");
		String doGet = HttpClientUtils.doGet(url, param, header,5000,1000,5000);
		System.out.println(doGet);
		System.out.println(UnicodeUtils.decodeUnicode(doGet));

//		{
//			"errno":0,
//			"request_id":6052453564786641709,
//			"total_count":18,
//			"follow_list":[
//				{
//					"type":0,
//					"follow_uname":"上弦***06",
//					"avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.1436c842.OpoWek9Tj-0bK1nztLEDiQ.jpg",
//					"intro":"2014考研加油~数学资料收藏分享",
//					"user_type":0,
//					"is_vip":0,
//					"follow_count":0,
//					"fans_count":0,
//					"follow_time":1559269591,
//					"pubshare_count":0,
//					"follow_uk":3744964652,
//					"album_count":0,
//					"follow_suk":"FIGLNREU_4RNBXFyvrb2cw"
//				},{"type":0,"follow_uname":"宫东***英语","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.f7c0c993.raeY3vLOKTjePVv-8UZASw.jpg","intro":"","user_type":4,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1559269591,"pubshare_count":0,"follow_uk":3645112425,"album_count":0,"follow_suk":"7FEQ_sUnwSsbwZWX_ZCdyQ"
//				},{"type":0,"follow_uname":"曲师***伙伴","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.1df599fc.Gfe_6Fenp_7WsM9ieV84zw.jpg","intro":"最新的考研备考资料全都在这了~~","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1559269591,"pubshare_count":0,"follow_uk":607919367,"album_count":0,"follow_suk":"0KYqojhDqz6yAKBl0ptCqQ"
//				},{"type":0,"follow_uname":"金*书系","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.7e4e4fd1.k89jCe0D_KJD3vJb8nFXTQ.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1559269426,"pubshare_count":0,"follow_uk":1731031088,"album_count":0,"follow_suk":"RT2PsdUsDE0L-_v4fwsi3A"
//				},{"type":0,"follow_uname":"梦**险9","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.96243d5c.PAeHwpYN5pU1Q4KxQD-rSQ.jpg","intro":"微信公众号 棒棒糖009","user_type":4,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1523643399,"pubshare_count":0,"follow_uk":4085034389,"album_count":0,"follow_suk":"KSfhzHNVr34aqrAhu0UtEg"
//				},{"type":0,"follow_uname":"浅浅**lf","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.1d4ea870.e_0lmWdIXEdtwYCcPQmaTw.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1519663595,"pubshare_count":0,"follow_uk":1177411841,"album_count":0,"follow_suk":"RZOBGAbWR4d49CcycluYDw"
//				},{"type":0,"follow_uname":"ri***uan","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.398e4fff.c5Y5aVf2qivBSSdK9xT66A.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1519126740,"pubshare_count":0,"follow_uk":3842148612,"album_count":0,"follow_suk":"GCZD9j0vhM19Aofz6ULkLw"
//				},{"type":0,"follow_uname":"稻**千代","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.b75d316.Y_SACnvxUAwf1xOo90VKsQ.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1509041094,"pubshare_count":0,"follow_uk":1812332180,"album_count":0,"follow_suk":"80TfNF5z70IKzdam6BSFQA"
//				},{"type":0,"follow_uname":"洪*聪","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.ef7c278e.TtjPLjptTnBoqofLzcm0mg.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1507046236,"pubshare_count":0,"follow_uk":724431032,"album_count":0,"follow_suk":"h-mGuVhjH3rNTbDP-Isejg"
//				},{"type":0,"follow_uname":"胡*海丶","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.c2367429.7hdg_3gG8gxik2FffdMUHA.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1481127560,"pubshare_count":0,"follow_uk":422927267,"album_count":0,"follow_suk":"RoNHhpidioFhFYhau6FDLg"
//				},{"type":0,"follow_uname":"传智播****ast","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.c973a399.cE6LyF1y_eefo0i37rK4wg.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1479839633,"pubshare_count":0,"follow_uk":3560277524,"album_count":0,"follow_suk":"V_MScQnAioCYNuo1mQXVBQ"
//				},{"type":0,"follow_uname":"教*集","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.4b2e15d9.xeKW9hYu7WXPcW7yXn7gpQ.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1478623993,"pubshare_count":0,"follow_uk":3660825403,"album_count":0,"follow_suk":"64z20VFOQJYwYDGDLuz1iA"
//				},{"type":0,"follow_uname":"L简***幸福","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.24952838.xGwTWN9gfRKcj5hNW8my-A.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1471183393,"pubshare_count":0,"follow_uk":393691859,"album_count":0,"follow_suk":"DrgSJOUt_wtH4HRvLV4G_A"
//				},{"type":0,"follow_uname":"克*这人","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.1bf52cfe._YxlK-IxmwublMaoc2BbTg.jpg","intro":"好资源大分享！！！觉得好的朋友顶赞，哄起来！","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1459180032,"pubshare_count":0,"follow_uk":908626094,"album_count":0,"follow_suk":"koCWlnJ2QiGKrh3Y0_qHdw"
//				},{"type":0,"follow_uname":"七凉***悲伤","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.5a958b.1rv2tqoecAU50wVeW7ZbWw.jpg","intro":"WX公众号：[云搜索]每天推送好电影！","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1452965808,"pubshare_count":0,"follow_uk":104524070,"album_count":0,"follow_suk":"A-3SMs_r6RtMOiBWb7fDgA"
//				},{"type":0,"follow_uname":"gn****oug","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.7c00e537.oAP6QckfOrEKosHXxKV11A.jpg","intro":"所有共享资源均来自互联网，本人只负责收集、整理、共享供大家学习使用，均不承担任何法律责任，如有侵权等其它行为请联系我删除","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1448352469,"pubshare_count":0,"follow_uk":2685623792,"album_count":0,"follow_suk":"g0Lf-OZmabVeP6K4joOGBw"
//				},{"type":0,"follow_uname":"SR**US","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.1ab952f2.-UXfgL0mfz1DgnIoyHeKyQ.jpg","intro":"源码巴士：移动应用与游戏开发者门户","user_type":4,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1446210312,"pubshare_count":0,"follow_uk":3846167034,"album_count":0,"follow_suk":"HjqCYSEFCWHhFephSp0cQA"
//				},{"type":0,"follow_uname":"xh**zh","avatar_url":"http:\/\/himg.bdimg.com\/sys\/portrait\/item\/netdisk.1.449116a4.rrZ72cny96lSGlrtdA5UwQ.jpg","intro":"","user_type":0,"is_vip":0,"follow_count":0,"fans_count":0,"follow_time":1441964835,"pubshare_count":0,"follow_uk":556283747,"album_count":0,"follow_suk":"ARSHdBGPDTqoF0JCV8c7hQ"}]}

	}
	//百度网盘接口测试
	private static void test3() throws Exception {
		String url = "http://pan.baidu.com/wap/share/home";

		Map header = new HashMap();
		header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		header.put("Accept-Encoding", "*;q=0");
		header.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		header.put("Connection", "keep-alive");
		header.put("Cookie", "BAIDUID=AD54F2360679981347F82CAA33B1052E:FG=1; BIDUPSID=AD54F2360679981347F82CAA33B1052E; PSTM=1536633965; PANWEB=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_f5f83a6d8b15775a02760dc5f490bc47=1568613181; BDUSS=EJNSC1qVlNXfmVmTmFmUGNsYXlRZ3BUbFFLai1VT0RofmdwTDJWZFJBbUw0YVpkRVFBQUFBJCQAAAAAAAAAAAEAAADxFkAmsKLLyTEzMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAItUf12LVH9dc; STOKEN=204dfa302602e743f993f6f28c76abd31c18b036aa914d69b8d832047013bd22; SCRC=509e15908c6b82f3fa4a21c5d57cf619; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1568032438,1568610320,1568612522,1568642926; BDCLND=UbjSd5LvhbJ9UcJUG7LqUt7aj5jaIFOm; BDSFRCVID=m6IOJeC626onCY3wAMBu-HAB_ACAPsTTH6aI7H-noAKnJFa6QGIGEG0P_f8g0KubIvYaogKKLgOTHULF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJ-HoD--JC_3jt-k-46B24uDqxbXq-uqWmOZ0l8KtDQoDlOXDnJNXfD0qt7C3qRC026TQMbmWIQHOD3SqtToLfPHy4jKtqoxWmv4KKJxXbLWeIJo5t5z5lF_hUJiBM7MBan7-lRIXKohJh7FM4tW3J0ZyxomtfQxtN4eaDcJ-J8XhC-GDTrP; H_PS_PSSID=1425_21084_29523_29521_29721_29567_29221_26350; delPer=0; PSINO=1; ZD_ENTRY=baidu");
		header.put("Host", "pan.baidu.com");
		header.put("Upgrade-Insecure-Requests", "1");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		Map param = new HashMap();
		param.put("query_uk", "371376031");
		param.put("limit", "24");
		param.put("start", "0");
		param.put("bdstoken", "e6f1efec456b92778e70c55ba5d81c3d");
		param.put("channel", "chunlei");
		param.put("clienttype", "0");
		param.put("web", "1");
		param.put("logid", "MTQ3NDA3NDg5NzU4NDAuMzQxNDQyMDY2MjA5NDA4NjU=");
		String doGet = HttpClientUtils.doGet(url, param, header,5000,1000,5000);
		System.out.println(doGet);
		System.out.println(UnicodeUtils.decodeUnicode(doGet));
	}
	//百度网盘接口测试
	private static void test4() throws Exception {
		String url = "https://nuexini.gq/bdp.php";

		Map header = new HashMap();
		header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		header.put("Accept-Encoding", "*;q=0");
		header.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		header.put("Connection", "keep-alive");
//		header.put("Cookie","BAIDUID=AD54F2360679981347F82CAA33B1052E:FG=1; BIDUPSID=AD54F2360679981347F82CAA33B1052E; PSTM=1536633965; PANWEB=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_f5f83a6d8b15775a02760dc5f490bc47=1568613181; BDUSS=EJNSC1qVlNXfmVmTmFmUGNsYXlRZ3BUbFFLai1VT0RofmdwTDJWZFJBbUw0YVpkRVFBQUFBJCQAAAAAAAAAAAEAAADxFkAmsKLLyTEzMwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAItUf12LVH9dc; STOKEN=204dfa302602e743f993f6f28c76abd31c18b036aa914d69b8d832047013bd22; SCRC=509e15908c6b82f3fa4a21c5d57cf619; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1568032438,1568610320,1568612522,1568642926; BDCLND=UbjSd5LvhbJ9UcJUG7LqUt7aj5jaIFOm; BDSFRCVID=m6IOJeC626onCY3wAMBu-HAB_ACAPsTTH6aI7H-noAKnJFa6QGIGEG0P_f8g0KubIvYaogKKLgOTHULF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJ-HoD--JC_3jt-k-46B24uDqxbXq-uqWmOZ0l8KtDQoDlOXDnJNXfD0qt7C3qRC026TQMbmWIQHOD3SqtToLfPHy4jKtqoxWmv4KKJxXbLWeIJo5t5z5lF_hUJiBM7MBan7-lRIXKohJh7FM4tW3J0ZyxomtfQxtN4eaDcJ-J8XhC-GDTrP; H_PS_PSSID=1425_21084_29523_29521_29721_29567_29221_26350; delPer=0; PSINO=1; ZD_ENTRY=baidu");
		header.put("Host", "pan.baidu.com");
		header.put("Upgrade-Insecure-Requests", "1");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		Map param = new HashMap();
		param.put("url", "https://pan.baidu.com/s/1gII4U163Sk4Q9AfYFsCfZw");
		String doGet = HttpClientUtils.doGet(url, param, header,5000,1000,5000);
		System.out.println(doGet);
		System.out.println(UnicodeUtils.decodeUnicode(doGet));
	}

//	Flow-Level: 3
//	Logid: 6048722777966499796
//	X-Powered-By: BaiduCloud
//	Yld: 284115249563555796
//	Yme: ZIGW+Sw8QE0aaCsBTnb+qnFMu+UcTxzwrwpNwiKDzOq4Sw8yYGcL4Lk3RDY=
	public static void main(String[] args) throws Exception {
//		test1();//获取用户粉丝
//		test2();//获取用户订阅
//		test3();//手机版分享
//		test4();
//		https://pan.baidu.com/s/1gII4U163Sk4Q9AfYFsCfZw 
//		https://pan.baidu.com/s/1JtY42Nwf9rn-TlpBIdr72g
//		https://pan.baidu.com/s/1zXt7wR1MrFWllIXSDTuNaw
		
		JSONObject param = new JSONObject();
		Long id = 2L;
	    String name = "b";
	    Integer age = 11;
		param.put("id", id);
		param.put("name", name);
		param.put("age", age);
		
		String json = param.toString();
		String doPost = HttpClientUtils.doPostJson("http://localhost:8080/users/",json,null,100000,1000,100000);
//		String doPost = Http.doPost("http://localhost:8080/users/",param );
		System.out.println(doPost);
	}
}
