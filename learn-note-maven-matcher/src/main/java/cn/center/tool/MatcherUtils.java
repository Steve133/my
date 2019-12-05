package cn.center.tool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatcherUtils {
	private static Logger logger = LoggerFactory.getLogger(MatcherUtils.class);
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//find函数测试
//		CharSequence input = "dfse324g4g4hg6";
//		String regex = "[\\d+]";
//		List<String> find = find(regex, input);
//		System.out.println(find);
		
		
		//腾讯漫画 解密测试
//    	CharSequence DATA = "<script>\r\n" + 
//    			"    var DATA        = 'cfdbbceyJjb21pbbaYyI6eyJpZCI6NjIxMDU4LCJ0aXRsZSI6Ilx1NjIxMVx1NjYyZlx1NTkyN1x1Nzk1ZVx1NGVkOSIsImNvbGxlY3QiOiIxOTc1MzcyIiwiaXNKYXBhbkNvbWljIjpmYWxzZSwiaXNMaWdodE5vdmVsIjpmYWbxzZSwiaXNMaWdodENvbWljIjpmYWxzZSwiaXeNGaW5pc2giOmZhbHNlLCJpc1JvYXN0YWJsZSI6dHJ1ZSwiZUlkIjoiS2xCUFRrcEVWMUZWQmdRZkFnY0hCdzBCSEVJeSJ9LCJjaGFwdGVyIjp7ImNpZCI6MiwiY1RpdGxlIjoiXHU3YjJjXHU0ZTAwXHU4YmRkXHUwMGI3XHU0ZTBkXHU2Y2JiXHU0ZTRiXHU3NWM3IiwiY1NlcSI6IjIiLCJ2aXBTdGF0dXMiOjEsInByZXZDaWQiOjEsIm5leHRDaWQiOjMsImJsYW5rRmlyc3QiOjEsImNhblJlYWQiOnRydWV9LCJwaWN0dXJlIjpbeyJwaWQiOiI1MDQzIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTMzLCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE4XzExNTA1ODU1NTVjNjJjNDdlZTc1YzQ3ZDc5OWZhMzY5XzUwNDMuanBnXC8wIn0seyJwaWQiOiI1MDQ0Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE4XzUzZTUzZGE4OTc3NTBjNDAzNjQ1MTQ0N2QxMzYzMmJiXzUwNDQuanBnXC8wIn0seyJwaWQiOiI1MDQ1Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE5XzQ2MTQ0MWZhMDI3OWE0NmUzNDk2MzQ5ZmRhMWFjYjZlXzUwNDUuanBnXC8wIn0seyJwaWQiOiI1MDQ2Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE5X2ExOGJkZDQyNjI5MDIyYzc3NTU4YTEzYzkxNWU5NGNkXzUwNDYuanBnXC8wIn0seyJwaWQiOiI1MDQ3Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE5X2JiMGM4NTFiMGVkMGU4OGYxMjk2MTVlMGUzYTllZTUxXzUwNDcuanBnXC8wIn0seyJwaWQiOiI1MDQ4Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE5X2ZiN2NiOGEyMTEzNzFjYTNkNjU1MjYyYjBkMmE4M2NkXzUwNDguanBnXC8wIn0seyJwaWQiOiI1MDQ5Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE5XzY2ZmNhN2VmZmQ0MTA5NDk5YjhlYjQyMDBmNDIyYjQxXzUwNDkuanBnXC8wIn0seyJwaWQiOiI1MDUwIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzE5X2Y3ZWViZTJhOTgxNThkNTZjNTYwNjljMzY2NTFkNmE5XzUwNTAuanBnXC8wIn0seyJwaWQiOiIxNDg2OCIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8wN18xNV80N182NmFmY2Y3MzZmOTg4ZjAxMTE2NDI1MjMwNmNhZTlkYV8xNDg2OC5qcGdcLzAifSx7InBpZCI6IjUwNTIiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfMTYxM2FmYWE3NmM4NjU5NDNhNjRjMjI3Njc5NGQxZGVfNTA1Mi5qcGdcLzAifSx7InBpZCI6IjUwNTMiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfYTE4ZjliNjUzOGZkYjk1Y2ZmMjFhY2ExMzQ2ZDA3MDVfNTA1My5qcGdcLzAifSx7InBpZCI6IjUwNTQiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfM2VhZDJiMGVlMzhkNDU0ZjJjZDg0ZWUwODFiZDc5OGFfNTA1NC5qcGdcLzAifSx7InBpZCI6IjUwNTUiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfMDYyNzM3OWY2ZGExYzZhN2E3MjNmYjIzMTU1NjMwMjZfNTA1NS5qcGdcLzAifSx7InBpZCI6IjUwNTYiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfNGIzMzQyNGU5ZGNmZDE0NWNhMDk3NmM3ZWNjMzdmMzNfNTA1Ni5qcGdcLzAifSx7InBpZCI6IjUwNTciLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfZjkzNTA5YTk2ZWY2MjM0YzgzN2I2N2Q3M2ZkNWVlYzVfNTA1Ny5qcGdcLzAifSx7InBpZCI6IjUwNTgiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfM2Q4MWY2NDI1MDdkNTFlZmRiMzQyZDQxMGJmZDUzYjhfNTA1OC5qcGdcLzAifSx7InBpZCI6IjUwNTkiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMTlfZWY0MjRiNTExMjBmZGVlMDJhNjBiYTRhZmFlMDhlZDZfNTA1OS5qcGdcLzAifSx7InBpZCI6IjUwNjAiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMjBfMTZkY2IzMzAyNmQyNjE3NDFjYWFiNjMyYWQ2ZWYyMzNfNTA2MC5qcGdcLzAifSx7InBpZCI6IjUwNjEiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMjBfN2U5NzQ4ZjUzMjc2MGQwZjAxNzcyMzE1NWM1ZWIwZWJfNTA2MS5qcGdcLzAifSx7InBpZCI6IjUwNjIiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMTdfMDBfMjBfY2I2NGEyZGRlYTA4YmUwZDJjZTVhNzYwMTkxOTgyNzdfNTA2Mi5qcGdcLzAifSx7InBpZCI6IjE0ODY5Iiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzA3XzE1XzQ3X2RjZjBkZTY5NTI5MDdmZDdmMDg0N2U5NGVhMzIyNTc1XzE0ODY5LmpwZ1wvMCJ9LHsicGlkIjoiNTA2NCIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8xN18wMF8yMF80NGJhMzFhMzE2NDQzZjNhMTFhZDBmMWVkMjdmNTU0Nl81MDY0LmpwZ1wvMCJ9LHsicGlkIjoiNTA2NSIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8xN18wMF8yMF8xMGIwYjEyYjRlMTMxZmE2MTc3NzgyYzgwYzdmNzMxYV81MDY1LmpwZ1wvMCJ9LHsicGlkIjoiNTA2NiIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8xN18wMF8yMF9hNDg2NTc5MzgxYjQ2MGZhNmIyMWM5NDZjZTI0NDFkOV81MDY2LmpwZ1wvMCJ9LHsicGlkIjoiNTA2NyIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8xN18wMF8yMF9jZTg0ZDZiZGE2YjAxNGVjYmNkNDE5MjkyZjdjZWNhZF81MDY3LmpwZ1wvMCJ9LHsicGlkIjoiNTA2OCIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8xN18wMF8yMF9kMjIyMjk5ZWQwYWQ4ZDI3NGVkMTRmMWVhMjVhNzE0NF81MDY4LmpwZ1wvMCJ9LHsicGlkIjoiMTQ4NzAiLCJ3aWR0aCI6ODAwLCJoZWlnaHQiOjExNzgsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMDdfMTVfNDhfYjhiMmY5NWMwMGU2ZTEyYjkxNjdlNTU3NGY4ZDM0MDZfMTQ4NzAuanBnXC8wIn0seyJwaWQiOiIxNDg3MSIsIndpZHRoIjo4MDAsImhlaWdodCI6MTE3OCwidXJsIjoiaHR0cHM6XC9cL21hbmh1YS5xcGljLmNuXC9tYW5odWFfZGV0YWlsXC8wXC8wN18xNV80OV8xZjBhZTgyYzNhZjI1ZDVkYTdiNWMwMjVjODAzOWJjZF8xNDg3MS5qcGdcLzAifSx7InBpZCI6IjE0ODczIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTc4LCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzA3XzE1XzQ5X2I1NDIxMjI4M2QyN2Q5OWFhZjg0ZjE5MWMyYzg2MWU3XzE0ODczLmpwZ1wvMCJ9LHsicGlkIjoiMTQ4NzIiLCJ3aWR0aCI6MTIwMCwiaGVpZ2h0Ijo4ODQsInVybCI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvbWFuaHVhX2RldGFpbFwvMFwvMDdfMTVfNDlfMDcyMWFmMjMwYzRjZTIxMjI5MTk3YzQxYWFmMzMzOGVfMTQ4NzIuanBnXC8wIn0seyJwaWQiOiI1MDcyIiwid2lkdGgiOjEyMDAsImhlaWdodCI6ODMwLCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIwXzYyNzRhYmQ4ZjhhNjYzZDM0NDEyODUyZmUzZTBkZDFmXzUwNzIuanBnXC8wIn0seyJwaWQiOiI1MDczIiwid2lkdGgiOjgwMCwiaGVpZ2h0IjoxMTMzLCJ1cmwiOiJodHRwczpcL1wvbWFuaHVhLnFwaWMuY25cL21hbmh1YV9kZXRhaWxcLzBcLzE3XzAwXzIwX2MyMTI1N2RhN2QzOTVjNGE5ZjY4YmViMzFkNmY3MTU0XzUwNzMuanBnXC8wIn1dLCJhZHMiOnsidG9wIjp7InRpdGxlIjoiXHU2NzljXHU3YzkyXHU1OTc2XHU0ZjE4WFx1NzJkMFx1NTk5Nlx1NWMwZlx1N2VhMlx1NWExOCIsInBpYyI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvb3BlcmF0aW9uXC8wXC8yMF8xOF81Ml9kNzc0M2FkMWIwMTcyYWI0Y2EzNGE1Mjk3YmU4N2JlN18xNTY4OTc2NzI4ODUwLmpwZ1wvMCIsInVybCI6Imh0dHBzOlwvXC9hYy5xcS5jb21cL0NvbWljVmlld1wvaW5kZXhcL2lkXC81MTgzMzNcL2NpZFwvNTIwIiwid2lkdGgiOiI2NTAiLCJoZWlnaHQiOiIxMTAifSwiYm90dG9tIjp7InRpdGxlIjoiXHU2NzljXHU3YzkyXHU1OTc2XHU0ZjE4WFx1NzJkMFx1NTk5Nlx1NWMwZlx1N2VhMlx1NWExOCIsInBpYyI6Imh0dHBzOlwvXC9tYW5odWEucXBpYy5jblwvb3BlcmF0aW9uXC8wXC8yMF8xOF81Ml9kNzc0M2FkMWIwMTcyYWI0Y2EzNGE1Mjk3YmU4N2JlN18xNTY4OTc2NzI4ODUwLmpwZ1wvMCIsInVybCI6Imh0dHBzOlwvXC9hYy5xcS5jb21cL0NvbWljVmlld1wvaW5kZXhcL2lkXC81MTgzMzNcL2NpZFwvNTIwIiwid2lkdGgiOiI2NTAiLCJoZWlnaHQiOiIxMTAifX0sImFydGlzdCI6eyJhdmF0YXIiOiJodHRwczpcL1wvdGhpcmRxcS5xbG9nby5jblwvZz9iPXNkayZrPXZadTMyUVl0cTZIcWpBbEZXQ1o1S2cmcz02NDAmdD0xNDgzMzU0MTAzIiwibmljayI6Ilx1NzZkYlx1NGUxNlx1NTM2MVx1NmYyYiIsInVpbkNyeXB0IjoiWldSWGJVc3JWMVV5YUZoTE5EVnRUSGRYWTJKeFp6MDkifX0=',\r\n" + 
//    			"        PRELOAD_NUM     = 2,\r\n" + 
//    			"        NOTICE_TIME     = 15,\r\n" + 
//    			"        ROAST_SIZE      = 1000,\r\n" + 
//    			"        ROAST_PRE       = 20,\r\n" + 
//    			"        ROAST_VIEW      = 20,\r\n" + 
//    			"        TUCAO_INTERVAL  = 8000,\r\n" + 
//    			"        DANMU_INTERVAL  = 2000,\r\n" + 
//    			"        DANMU_TIME      = 10000;\r\n" + 
//    			"</script>";
//		CharSequence findStr = MatcherUtils.findStr("\'[A-Za-z0-9\\/\\=]+(\'|)", DATA );
		
		
		//腾讯漫画 解密测试
//    	CharSequence nonce = "<script>\r\n" + 
//    			"    window[\"nonc\"+\"e\"] = \"a\" + (+eval(\"'123'.substring(2)\")).toString() + (+eval(\"'1'.charCodeAt() - 45\")).toString() + (+eval(\"0 * 0\")).toString() + \"f0d18495ed8\" + (+eval(\"!!1+!1+!!2+!!2+1\")).toString() + \"86e12f9a90ea9df3\";\r\n" + 
//    			"</script>";
//    	CharSequence findStr = MatcherUtils.findStr("=.*?\";", nonce);
//    	
//		logger.info(String.valueOf(findStr.toString().substring(1,findStr.length()-2)));
		
		
		
		//中文格式的空格
//		String s = "    “小凡，赶紧洗把脸，换身干净衣现好点！”    张铁柱今天打";
//		List<String> find = MatcherUtils.find("[\\u3000|\\u0020|\\u00A0]+[^\\u3000|\\u0020|\\u00A0]+", s);
//		for (String string : find) {
//			System.out.println(string);
//		}
		
		Pattern p=Pattern.compile("^[0-9\\.]+$"); 
		Matcher m=p.matcher(".14.202.80");
		while(m.find()) {
			System.out.println(m.group());
		}
	}
	
	/**
	 * 找到符合正则的字段，组合成新的字段返回
	 * @param regex
	 * @param input
	 * @return
	 */
	public static CharSequence findStr(String regex,CharSequence input){
		try {
			logger.info("");
			logger.info("============findStr================");
//			logger.info("正则语句："+regex);
//			logger.info("被正则语句："+input);
			Pattern p=Pattern.compile(regex); 
			Matcher m=p.matcher(input);
			
			StringBuilder sb = new StringBuilder();
			while(m.find()) {
				sb.append(m.group());
			}
//			logger.info("正则结果："+sb.toString());
			logger.info("============findStr================");
			logger.info("");
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return input;
	}
	
	/**
	 * list转String[]
	 * @param list
	 * @return
	 */
	public static String[] trans2Arr(List<String> list) {
		String[] strings = new String[list.size()];
		return list.toArray(strings);
	}
	
	/**
	 * 找到符合正则的字段
	 * @param regex 
	 * @param input
	 * @return 所有匹配上的字段数组
	 */
	public static List<String> find(String regex,CharSequence input){
		List<String> ret = new ArrayList<String>();
		try {
//			logger.info("正则语句："+regex);
//			logger.info("被正则语句："+input);
			Pattern p=Pattern.compile(regex); 
			Matcher m=p.matcher(input);
			while(m.find()) {
				ret.add(m.group());
			}
//			logger.info("正则结果："+ret);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ret;
	}
}
