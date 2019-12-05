package cn.center.tool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.LoggerFactory;

import cn.center.tool.UnicodeUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//java调用js
//Java js语法严格 特别是js结尾必须要加;
public class JsInvokUtils {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(JsInvokUtils.class);
	
	public static void main(String[] args) {
//		String data = "ebedfdbbcyJjb21pYyI6eyJpZCI6NjfedIxMDU4LCJ0aXRsZSI6Ilx1NjIxMVx1NjYyZlx1NTkyN1x1Nzk1ZVx1NGVkOSIsImNvbGxlY3QiOiIxOTc1MzQzIiwiaXNKYXBhbkNvbWljIjpmYWxzZSwiaXNMaWdodE5vdmVsIjpmYWxzZSwiaXNMaaWdodENvbWljIjpmYWxzZSwiaXNGaW5pc2giOmZhbHNlLCJpc1JvYXN0YWJsZSI6dHJ1ZSwiZUlkIjoiS2xCUFRrcEZVRkJhQmdJZkFnY0hCdzBCSEVNeSJ9LCJjaGFwdGVyIjp7ImNpZCI6MywiY1RpdGxlIjoiXHU3YjJjXHU0ZThjXHU4YmRkXHUwMGI3XHU1ZTdmXHU5Njc1XHU1NDFiXHVmZjA4XHU0ZTBhXHVmZjA5IiwiY1NlcSI6IjMiLCJ2aXBTdGF0dXMiOjEsInByZXZDaWQiOjIsIm5leHRDaWQiOjUsImJsYW5rRmlyc3QiOjEsImNhblJlYWQiOnRydWV9LCJwaWN0dXJlIjpbeyJwaWQiOiI1MTAxIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTMzLCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIwX2I4NWMyZGNkNjNiNzE4YTcxZjRiZDIzYzdlMDkzZDQxXzUxMDEuanBnXC8wIn0seyJwaWQiOiI1MTAyIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIwXzI2MWFlNzc2NGE4Y2VhZjJiMmEyZjRjMTE4ZTdjYjViXzUxMDIuanBnXC8wIn0seyJwaWQiOiI1MTAzIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxX2U0MjczZGM1OWU0YTUzMDI5NWU3NDcyMjFmZjZjMDRjXzUxMDMuanBnXC8wIn0seyJwaWQiOiI1MTA0Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxXzZkOTQ4Y2RmMmMzZDAwOTQyZjZjMjM3N2Q5ZGI5Y2UzXzUxMDQuanBnXC8wIn0seyJwaWQiOiI1MTA1Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxXzRkNzY4MTY1Y2I3YmZlN2VjMDY2MTM0MmIxMDk3ZjgxXzUxMDUuanBnXC8wIn0seyJwaWQiOiI1MTA2Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxXzEyYzdmMWVmZjI2OTEzYmZlMDg3NDIzZTJjZWMzNDk1XzUxMDYuanBnXC8wIn0seyJwaWQiOiI1MTA3Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxX2VhNDM1NDQxZTQzNjFmNzlmZjQ0ZGQ2ZDE5NDE5MzczXzUxMDcuanBnXC8wIn0seyJwaWQiOiIxNDg4MiIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8wN18xNl8wNV9kNzcxYzkzODc1NzU1YjlmNjRkZDMxYzEzYmIwMWM1MV8xNDg4Mi5qcGdcLzAifSx7InBpZCI6IjE0ODc0Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzA3XzE1XzUwXzc5Y2Y1OWU3MTIzOTZlZjk1YmIyYzljNjRkZmM1YWQzXzE0ODc0LmpwZ1wvMCJ9LHsicGlkIjoiMTQ4NzUiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMDdfMTVfNTFfMzM1ODYzNzBmYWMwNTdlZjA2YjcyOWIyN2I0YWZhYzhfMTQ4NzUuanBnXC8wIn0seyJwaWQiOiI1MTExIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxX2Y0ZjJkODY5OTk3NmRmZGY3NzFhMmRhYTBjMDY3MWFjXzUxMTEuanBnXC8wIn0seyJwaWQiOiI1MTEyIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxXzBkMDlkNjllZGY2NjVmMDMxMzc1MTU4MDZhNGU0ODIzXzUxMTIuanBnXC8wIn0seyJwaWQiOiI1MTEzIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxX2YwYmI3MTAxYmRkNjhhMjhmNzEyNmU0Mjc4NDA1ZTA1XzUxMTMuanBnXC8wIn0seyJwaWQiOiI1MTE0Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxX2Y4MWE3ZTZjN2ZhNGM5OWVhMzhjZmUwOWJhMDAyZGI0XzUxMTQuanBnXC8wIn0seyJwaWQiOiI1MTE1Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxXzRmOWUwYWE2MzZjOTc5Y2NjZjNiMDc5MTBhMzdkZDUwXzUxMTUuanBnXC8wIn0seyJwaWQiOiI1MTE2Iiwid2lkdGgiOjEyMDAsImhlaWdodCI6ODMwLCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIxX2I5YjNmODYwZmJhYjQ0Nzc5ZWU1NzU2NmNlNDVlMjdjXzUxMTYuanBnXC8wIn1dLCJhZHMiOnsidG9wIjp7InRpdGxlIjoiXHU2NzljXHU3YzkyXHU1OTc2XHU0ZjE4WFx1NzJkMFx1NTk5Nlx1NWMwZlx1N2VhMlx1NWExOCIsInBpYyI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvb3BlcmF0aW9uXC8wXC8yMF8xOF81Ml9kNzc0M2FkMWIwMTcyYWI0Y2EzNGE1Mjk3YmU4N2JlN18xNTY4OTc2NzI4ODUwLmpwZ1wvMCIsInVybCI6Imh0dHBzOlwvXC9hYy5xcS5jb21cL0NvbWljVmlld1wvaW5kZXhcL2lkXC81MTgzMzNcL2NpZFwvNTIwIiwid2lkdGgiOiI2NTAiLCJoZWlnaHQiOiIxMTAifSwiYm90dG9tIjp7InRpdGxlIjoiXHU2NzljXHU3YzkyXHU1OTc2XHU0ZjE4WFx1NzJkMFx1NTk5Nlx1NWMwZlx1N2VhMlx1NWExOCIsInBpYyI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvb3BlcmF0aW9uXC8wXC8yMF8xOF81Ml9kNzc0M2FkMWIwMTcyYWI0Y2EzNGE1Mjk3YmU4N2JlN18xNTY4OTc2NzI4ODUwLmpwZ1wvMCIsInVybCI6Imh0dHBzOlwvXC9hYy5xcS5jb21cL0NvbWljVmlld1wvaW5kZXhcL2lkXC81MTgzMzNcL2NpZFwvNTIwIiwid2lkdGgiOiI2NTAiLCJoZWlnaHQiOiIxMTAifX0sImFydGlzdCI6eyJhdmF0YXIiOiJodHRwczpcL1wvdGhpcmRxcS5xbG9nby5jblwvZz9iPXNkayZrPXZadTMyUVl0cTZIcWpBbEZXQ1o1S2cmcz02NDAmdD0xNDgzMzU0MTAzIiwibmljayI6Ilx1NzZkYlx1NGUxNlx1NTM2MVx1NmYyYiIsInVpbkNyeXB0IjoiWldSWGJVc3JWMVV5YUZoTE5EVnRUSGRYWTJKeFp6MDkifX0=";
//		String nonce = "ee1bedf5d606562738a6bc7197fed7b8";
		String data = "eycaJjbb21peYyI6eyJpZCI6ddNjI0NTcwLCJ0aXRseZSI6Ilx1NGYyMFx1NmI2NiIsIemNvbGbdxlY3QiOeiIxMzUea4NzQ3IiwiaXNKYXBhbkNvbWljIjpmYWxzZSwiaXNMaWdodE5vdmVsIjpmYWxzZSwiaXNMaWdodENvbWljIjp0cnVlLCJpc0ZpbmlzaCI6ZmFsc2UsImlzUm9hc3RhYmxlIjp0cnVlLCJlSWQiOiJLbEJQVGtwRVVWVlFCUVlmQWdjQ0FnOEpIRWhaTkE9PSJ9LCJjaGFwdGVyIjp7ImNpZCI6ODYsImNUaXRsZSI6Ilx1N2IyY1x1NGUwM1x1NTM0MVx1NTE2Ylx1Njc2MVx1ZmYxYVx1NWYzYSIsImNTZXEiOiI3OCIsInZpcFN0YXR1cyI6MiwicHJldkNpZCI6ODUsIm5leHRDaWQiOjg3LCJibGFua0ZpcnN0IjoxLCJjYW5SZWFkIjpmYWxzZX0sInBpY3R1cmUiOlt7InBpZCI6Ijk1MTEiLCJ3aWR0aCI6OTAwLCJoZWlnaHQiOjY3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8yM18xOF8zMF9hNGFlOWNiNGUzMTUyYTc5YTE5MGY5MjM0NTU2ZjgyNl85NTExLmpwZ1wvMCJ9XSwiYWRzIjp7InRvcCI6IiIsImJvdHRvbSI6eyJ0aXRsZSI6Ilx1Njc5Y1x1N2M5Mlx1NTk3Nlx1NGYxOFhcdTcyZDBcdTU5OTZcdTVjMGZcdTdlYTJcdTVhMTgiLCJwaWMiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL29wZXJhdGlvblwvMFwvMjBfMThfNTJfZDc3NDNhZDFiMDE3MmFiNGNhMzRhNTI5N2JlODdiZTdfMTU2ODk3NjcyODg1MC5qcGdcLzAiLCJ1cmwiOiJodHRwczpcL1wvYWMucXEuY29tXC9Db21pY1ZpZXdcL2luZGV4XC9pZFwvNTE4MzMzXC9jaWRcLzUyMCIsIndpZHRoIjoiNjUwIiwiaGVpZ2h0IjoiMTEwIn19LCJhcnRpc3QiOnsiYXZhdGFyIjoiaHR0cHM6XC9cL3RoaXJkcXEucWxvZ28uY25cL2c/Yj1zZGsmaz16VkRKeU5jYmI0TGFVZGU0ZU5Fcm13JnM9NjQwJnQ9MTQ4MzMzNzc0NSIsIm5pY2siOiJcdTY2MWZcdTY2MWZcdTY2MWZcdTRlOTEiLCJ1aW5DcnlwdCI6IlNVSjZhbTVvTWpsaE1XUlJNMlUwTDIxVFoyTmhVVDA5In19";
		String nonce = "61e520e2ca070bd23dd87ea41e7b83e5";
		
		Map<String, Object> func = func(data, nonce);
		for (String s : func.keySet()) {
			System.out.println(s+" "+func.get(s));
		}
		
//		String trans = " \"d76dd3829e116d3c758ed5\" + (+eval(\"!!document.getElementsByTagName('html')\")).toString() + \"9dc005f\" + (+eval(\"9 * 0\")).toString() + \"9\"";
//		String trans = "= \"e7d\" + (+eval(\"!!1\")).toString() + (+eval(\"'1'.charCodeAt() - 45\")).toString() + \"b08cd8096313\" + (+eval(\"!!1\")).toString() + \"1ea2f929bd\" + (+eval(\"1 * (!1) + 1\")).toString() + (+eval(\"!!1*5\")).toString() + \"48\";";
//		String trans = "= \"912dd5970\" + (+eval(\"9 * 0\")).toString() + \"39ef96346\" + (+eval(\"!!document.getElementsByTagName('html')\")).toString() + \"ef7\" + (+eval(\"!!document.getElementsByTagName('html')\")).toString() + \"05f\" + (+eval(\"'1'.charCodeAt() - 45\")).toString() + \"c21b\";";
//		String trans = "= \"611e\" + (+eval(\"'1'.charCodeAt() - 45\")).toString() + \"8183b9af98c6\" + (+eval(\"1 - !document.children!document.children\")).toString() + \"743e5617\" + (+eval(\"'123'.substring(2)\")).toString() + \"c8\" + (+eval(\"1/2 + 5/2\")).toString() + \"66\";";

//		String trans2 = JsInvokUtils.trans(trans.toString().substring(1, trans.length() - 1));
//		System.out.println(trans2);
		
//		List<String> find = MatcherUtils.find("document.*?[)]+", trans.toString().substring(1, trans.length() - 1));
//		for (String documentStr : find) {
//			trans = trans.replace(documentStr, "1");
//		}
//		System.out.println(trans);
		
		
//		String nonce = "<script>\r\n" + 
//				"    window[\"no\"+\"nce\"] = \"9\" + (+eval(\"1 - !!document.children\")).toString() + \"80be7\" + (+eval(\"!!1+!1+!!2+!!2+1\")).toString() + \"4af80\" + (+eval(\"Math.round(.5) + ~~1.8\")).toString() + \"737d15eb7fa6\" + (+eval(\"5&5\")).toString() + (+eval(\"'123'.substring(2)\")).toString() + \"1f37\";\r\n" + 
//				"</script>";
//		CharSequence nonce_chars = MatcherUtils.findStr("=.*?\";", nonce);
//		if(nonce_chars.toString().contains("document")) {
//			if(nonce_chars.toString().contains("document.children")) {
//				nonce_chars = nonce_chars.toString().replaceAll("document.children", "1");//document.children	document.getElementsByTagName('html')
//			}
//			if(nonce_chars.toString().contains("document")) {
//				List<String> find = MatcherUtils.find("document.*?[)]+", nonce_chars);
//				for (String documentStr : find) {
//					nonce_chars = nonce_chars.toString().replace(documentStr, "1");
//				}
//			}
//		}
//		if(nonce_chars.toString().contains("window")) {
//			if(nonce_chars.toString().contains("window.Array")) {
//				nonce_chars = nonce_chars.toString().replaceAll("window.Array", "1");//window.Array
//			}
//			if(nonce_chars.toString().contains("window")) {
//				List<String> find = MatcherUtils.find("window.*?[)]+", nonce_chars);
//				for (String windowStr : find) {
//					nonce_chars = nonce_chars.toString().replace(windowStr, "1");
//				}
//			}
//		}
//
//		nonce = JsInvokUtils.trans(nonce_chars.toString().substring(1, nonce_chars.length() - 1));
		
	}
	
	
	
	
	/**
	 * js 复杂语句eval
	 * @param trans
	 * @return
	 */
	public static String trans(String trans){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		try{
			engine.eval("function trans(s){"
//					+ "var document = {};"
//					+ "document.children={};"
//					+ "var window = {};"
					+ "return eval(s);"
					+ "}"
					);
			if (engine instanceof Invocable) {
				Invocable in = (Invocable) engine;
				String invokeFunction = in.invokeFunction("trans",trans).toString();
				return invokeFunction;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 腾讯动漫 解析数据
	 * js 函数
	 * @param data
	 * @param nonce
	 * @return
	 */
	public static Map<String, Object> func(String data , String nonce){
		logger.info("解析数据data："+data);
		logger.info("解析数据nonce："+nonce);
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		try{
			engine.eval("function func(DATA,nonce){"
					+ "var T = DATA.split(''), N = nonce, len, locate, str;"
					+ "N = N.match(/\\d+[a-zA-Z]+/g);"
					+ "len = N.length;"
					+ "while (len--) {"
						+ "locate = parseInt(N[len]) & 255;"
						+ "str = N[len].replace(/\\d+/g, '');"
						+ "T.splice(locate, str.length)"
					+ "}"
					+ "T = T.join('');"
					+ ""
					+ "c=T;"
					+ "var keyStr = \"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=\";"
					+ "var a = '', b, d, h, f, g, e = 0;"
					+ "for (c = c.replace(/[^A-Za-z0-9\\+\\/\\=]/g, ''); e < c.length;)b = keyStr.indexOf(c.charAt(e++)), d = keyStr.indexOf(c.charAt(e++)), f = keyStr.indexOf(c.charAt(e++)), g = keyStr.indexOf(c.charAt(e++)), b = b << 2 | d >> 4, d = (d & 15) << 4 | f >> 2, h = (f & 3) << 6 | g, a += String.fromCharCode(b), 64 != f && (a += String.fromCharCode(d)), 64 != g && (a += String.fromCharCode(h));"
					+ ""
					+ "c=a;"
					+ "for (var a = '', b = 0, d = c1 = c2 = 0; b < c.length;)d = c.charCodeAt(b), 128 > d ? (a += String.fromCharCode(d), b++) : 191 < d && 224 > d ? (c2 = c.charCodeAt(b + 1), a += String.fromCharCode((d & 31) << 6 | c2 & 63), b += 2) : (c2 = c.charCodeAt(b + 1), c3 = c.charCodeAt(b + 2), a += String.fromCharCode((d & 15) << 12 | (c2 & 63) << 6 | c3 & 63), b += 3);"
					+ "return a;"
					+ ""
					+ "}");
			if (engine instanceof Invocable) {
				Invocable in = (Invocable) engine;
				String invokeFunction = in.invokeFunction("func",data,nonce).toString();
//				System.out.println(UnicodeUtils.decodeUnicode(invokeFunction));
				JSONObject json = JSONObject.fromObject(invokeFunction);
//				{
//					"comic":{
//						"id":621058,
//						"title":"我是大神仙",
//						"collect":"1975343",
//						"isJapanComic":false,
//						"isLightNovel":false,
//						"isLightComic":false,
//						"isFinish":false,
//						"isRoastable":true,
//						"eId":"KlBPTkpFUFBaBgIfAgcHBw0BHEMy"
//					},
//					"chapter":{
//						"cid":3,
//						"cTitle":"第二话·广陵君（上）",
//						"cSeq":"3",
//						"vipStatus":1,
//						"prevCid":2,
//						"nextCid":5,
//						"blankFirst":1,
//						"canRead":true
//					},
//					"picture":[
//				           {"pid":"5101","width":800,"height":1133,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_20_b85c2dcd63b718a71f4bd23c7e093d41_5101.jpg/0"},
//				           {"pid":"5102","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_20_261ae7764a8ceaf2b2a2f4c118e7cb5b_5102.jpg/0"},
//				           {"pid":"5103","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_e4273dc59e4a530295e747221ff6c04c_5103.jpg/0"},
//				           {"pid":"5104","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_6d948cdf2c3d00942f6c2377d9db9ce3_5104.jpg/0"},
//				           {"pid":"5105","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_4d768165cb7bfe7ec0661342b1097f81_5105.jpg/0"},
//				           {"pid":"5106","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_12c7f1eff26913bfe087423e2cec3495_5106.jpg/0"},
//				           {"pid":"5107","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_ea435441e4361f79ff44dd6d19419373_5107.jpg/0"},
//				           {"pid":"14882","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/07_16_05_d771c93875755b9f64dd31c13bb01c51_14882.jpg/0"},
//				           {"pid":"14874","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/07_15_50_79cf59e712396ef95bb2c9c64dfc5ad3_14874.jpg/0"},
//				           {"pid":"14875","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/07_15_51_33586370fac057ef06b729b27b4afac8_14875.jpg/0"},
//				           {"pid":"5111","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_f4f2d8699976dfdf771a2daa0c0671ac_5111.jpg/0"},
//				           {"pid":"5112","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_0d09d69edf665f03137515806a4e4823_5112.jpg/0"},
//				           {"pid":"5113","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_f0bb7101bdd68a28f7126e4278405e05_5113.jpg/0"},
//				           {"pid":"5114","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_f81a7e6c7fa4c99ea38cfe09ba002db4_5114.jpg/0"},
//				           {"pid":"5115","width":800,"height":1178,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_4f9e0aa636c979cccf3b07910a37dd50_5115.jpg/0"},
//				           {"pid":"5116","width":1200,"height":830,"url":"https://manhua.qpic.cn/manhua_detail/0/17_00_21_b9b3f860fbab44779ee57566ce45e27c_5116.jpg/0"}],
//					"ads":{
//						"top":{
//							"title":"果粒奶优X狐妖小红娘",
//							"pic":"https://manhua.qpic.cn/operation/0/20_18_52_d7743ad1b0172ab4ca34a5297be87be7_1568976728850.jpg/0",
//							"url":"https://ac.qq.com/ComicView/index/id/518333/cid/520","width":"650","height":"110"
//						},
//						"bottom":{
//							"title":"果粒奶优X狐妖小红娘","pic":"https://manhua.qpic.cn/operation/0/20_18_52_d7743ad1b0172ab4ca34a5297be87be7_1568976728850.jpg/0",
//							"url":"https://ac.qq.com/ComicView/index/id/518333/cid/520","width":"650","height":"110"
//						}
//					},
//					"artist":{
//						"avatar":"https://thirdqq.qlogo.cn/g?b=sdk&k=vZu32QYtq6HqjAlFWCZ5Kg&s=640&t=1483354103",
//						"nick":"盛世卡漫",
//						"uinCrypt":"ZWRXbUsrV1UyaFhLNDVtTHdXY2JxZz09"
//					}
//				}
				String title = json.getJSONObject("comic").getString("title");
				String cTitle = json.getJSONObject("chapter").getString("cTitle");
				JSONArray jsonArray = json.getJSONArray("picture");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("title", title);
				ret.put("cTitle", cTitle);
				logger.info(title+" "+cTitle);
				ret.put("jsonArray", jsonArray);
				return ret;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
		
	
	//js 函数
	public static void add(int i , int j){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		try{
			engine.eval("function add(a,b){" +
					"return a+b;" +
					"}");
			if (engine instanceof Invocable) {
				Invocable in = (Invocable) engine;
				System.out.println(in.invokeFunction("add",i,j));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//js 脚本调用
	public static void run(String jsFileName) throws ScriptException, FileNotFoundException{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		String jsName = "test.js";
		//读取js
		FileReader fileReader = new FileReader(jsName );
		//执行指定脚本  
		Object eval = engine.eval(fileReader);
	}
}
