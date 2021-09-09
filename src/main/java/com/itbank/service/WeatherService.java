package com.itbank.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {
	
	private final String serviceKey = ""; //https://www.data.go.kr/ 에 서비스키를 넣어주면 된다.
	private final String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"; // 현재 2.0버전으로 업데이트 되어 사용불가 합니다 ㅠㅠ
	
	public String getWeatherJSON() throws Exception {
		String base_date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		int time = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
		String base_time = String.format("%02d00", time / 3 * 3 - 1); //base_time에 입력 가능한 시간을 정해진값에 맞춰줌
		
		String query = "?";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("serviceKey",	serviceKey);
		param.put("numOfRows", "9");
		param.put("pageNo", "1");
		param.put("dataType", "JSON");
		param.put("base_date", base_date);	// 8 자리 숫자: 2021 06 08
		param.put("base_time", base_time);	// 4 자리 숫자:	0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300
		param.put("nx", "99");
		param.put("ny", "76");
		
		for(String key : param.keySet()) {
			String value = param.get(key);
			query += key + "=" + value + "&";
		}
		
		URL requestURL = new URL(url + query); // 주소 + 쿼리스트링 형식의 GET 요청
		HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		
		Scanner sc = null;
		String json = "";
		if(conn.getResponseCode() == 200) {
			sc = new Scanner(conn.getInputStream());
			while(sc.hasNextLine()) {
				json += sc.nextLine();
			}
			sc.close();
			conn.disconnect();
			return json;
		}
		return null;
	}

}
