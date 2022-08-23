package com.lt.boot_tianqi.controller;

import com.lt.boot_tianqi.pojo.Weather;
import com.lt.boot_tianqi.utils.CaiHongPiUtils;
import com.lt.boot_tianqi.utils.JiNianRiUtils;
import com.lt.boot_tianqi.utils.WeatherUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Map;

/**
 * @author cVzhanshi
 * @create 2022-08-04 21:09
 * 推送类
 */
public class Pusher {

    public static void main(String[] args) {
        push();
    }
    private static String appId = "wx1e1f4d40df380613";
    private static String secret = "fcd21e7c9934777a38be897127c2a523";



    public static void push(){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("o--5y6h1CeIqgHKwyaTy5qgpfwr4")  //微信号
                .templateId("zrSf_oWmhzzgqJEv_q7FbH5kuyeOstrvlB6srUZrYuc") //模板id
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        Weather weather = WeatherUtils.getWeather();
        Map<String, String> map = CaiHongPiUtils.getEnsentence();
        templateMessage.addData(new WxMpTemplateData("riqi",weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("tianqi",weather.getText_now(),"#00FFFF")); //天气
        templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "","#173177"));  //最低气温
        templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D")); //当前温度
        templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" )); //最高气温
        templateMessage.addData(new WxMpTemplateData("windclass",weather.getWind_class()+ "","#42B857" )); //风级大小
        templateMessage.addData(new WxMpTemplateData("winddir",weather.getWind_dir()+ "","#B95EA3" )); //风向
        templateMessage.addData(new WxMpTemplateData("caihongpi",CaiHongPiUtils.getCaiHongPi(),"#FF69B4"));  //彩虹屁
        templateMessage.addData(new WxMpTemplateData("lianai",JiNianRiUtils.getLianAi()+"","#FF1493"));  //恋爱时间
        templateMessage.addData(new WxMpTemplateData("shengri1", JiNianRiUtils.getBirthday_Jo()+"","#FFA500")); //距离生日
        templateMessage.addData(new WxMpTemplateData("shengri2",JiNianRiUtils.getBirthday_Hui()+"","#FFA500")); //距离我的生日
        templateMessage.addData(new WxMpTemplateData("en",map.get("en") +"","#C71585")); //英文
        templateMessage.addData(new WxMpTemplateData("zh",map.get("zh") +"","#C71585"));  //中文
        String beizhu = "❤";
        if(JiNianRiUtils.getLianAi() % 365 == 0){
            beizhu = "今天是恋爱" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！宝贝我爱你!";
        }
        if(JiNianRiUtils.getBirthday_Jo()  == 0){
            beizhu = "今天是宝贝生日，生日快乐呀！";
        }
        if(JiNianRiUtils.getBirthday_Hui()  == 0){
            beizhu = "今天是老公的生日，快去祝他生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

