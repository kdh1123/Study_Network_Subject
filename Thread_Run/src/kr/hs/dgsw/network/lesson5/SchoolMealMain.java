package kr.hs.dgsw.network.lesson5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URL;

public class SchoolMealMain {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal=Calendar.getInstance();

        BufferedReader br=null;

        // open neis 급식 정보 가져오기
        String newUrls = "https://open.neis.go.kr/hub/mealServiceDietInfo?"		// 기본 주소
                + "ATPT_OFCDC_SC_CODE=D10&"										// 대구광역시 코드
                + "SD_SCHUL_CODE=7240454&"										// 대구소프트웨어마이스터고 코드
                + "TYPE=json&"													// 출력 타입
                + "MLSV_YMD=" + sdf.format(cal.getTime());						// 가져올 식단 날짜(오늘)

        URL url = null;
        StringBuffer sb=new StringBuffer();

        try {
            url=new URL(newUrls);
            System.out.println(newUrls);

            // InputStreamReader: InputStream(byte)을 Reader(char)로 변경
            br=new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
            String msg="";
            while((msg=br.readLine())!=null) {
                sb.append(msg);
            }
        } catch(Exception e) {

        }

        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());

            JSONArray jsonArray = (JSONArray) jsonObject.get("mealServiceDietInfo");

            // 급식 정보
            JSONObject row = (JSONObject) jsonArray.get(1);
            JSONArray row_array = (JSONArray) row.get("row");

            for (int i = 0; i < row_array.size(); i++) {
                JSONObject out = (JSONObject) row_array.get(i);
                System.out.println(out.get("MMEAL_SC_NM"));
                for (String value : ((String) out.get("DDISH_NM")).split("<br/>")) {
                    System.out.println("  - " + value);
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}