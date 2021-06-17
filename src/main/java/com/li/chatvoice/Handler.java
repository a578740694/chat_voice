package com.li.chatvoice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.li.chatvoice.ai.AliSmChat;
import com.li.chatvoice.ai.NewJFrame;
import com.li.chatvoice.ai.QykChat;
import com.li.chatvoice.ai.RespValue;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.net.URLEncoder;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @Author lisulong
 * @Date 2020/11/10 16:26
 * @Description Handler
 * ************************************
 */
public class Handler implements Runnable {

    private final static String CHAT_URL_1 = "https://ai.sm.cn/quark/1/ai?format=json&uc_param_str=dnntnwvepffrgibijbprsvdsdicheicpniwiutmiud&session_id=1e774d90-7065-315d-988d-22b012ff17ea&q=";
    private final static String CHAT_URL_2 = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";
    private final static String VOICE_URL1 = "https://api.vvhan.com/api/song?";
    private final static String VOICE_URL2 = "https://tts.baidu.com/text2audio?cuid=baike&lan=ZH&ctp=1&pdt=301&vol=9&rate=32&";

    private final Integer type;

    private final String text;

    private final JTextArea display;

    private final JPanel panel;

    public Handler(Integer type, String text, JTextArea display, JPanel panel) {
        this.type = type;
        this.text = text;
        this.display = display;
        this.panel = panel;
    }

    @Override
    public void run() {
        try {
            switch (type){
                case 0: //文字转语音
                    HttpClientUtil.httpGetVoice(VOICE_URL2 + "per=4&tex=" + text);
                    break;
                case 1: //闲聊模式
                case 2: //闲聊+语音
                case 3: //智能语音互聊
                    chat(type);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "糟糕.. 出问题了哦！  ╮(๑•́ ₃•̀๑)╭", "错误", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void chat(int type) throws Exception{
        String regEx="[\n`@#$%^&*()+=|{}':;'\\[\\].<>/@#￥%……&*（）——+|{}【】‘；：”“’ ]";
        String str = text;
        do {
            String myName = NewJFrame.getInstance().getMyName();
            String youName = NewJFrame.getInstance().getYouName();

            str = getChatResp1(str.replaceAll(regEx, ""));

            display.append("\n" + youName + ": " + str);

            if(type == 2 || type == 3){
                HttpClientUtil.httpGetVoice(VOICE_URL2 + "per=4&tex=" + str.replaceAll(regEx, ""));
            }

            if(type == 3){
                str = getChatResp2(str.replaceAll(regEx, ""));

                display.append("\n" +  myName + ": " + str);

                HttpClientUtil.httpGetVoice(VOICE_URL2 + "per=1&tex=" + str.replaceAll(regEx, ""));
            }

        }while (type == 3);
    }

    private String getChatResp1(String str) throws Exception{
        String json = HttpClientUtil.httpGet(CHAT_URL_1 + str);
        AliSmChat aliSm = JSON.parseObject(json, AliSmChat.class);
        if(aliSm.getStatus() != 0){
            throw new RuntimeException("请求失败");
        }

        RespValue respValue = aliSm.getData().get(0).getValue();
        if(StringUtils.isNotBlank(respValue.getAnswer())){
            str = respValue.getAnswer();
        }else {
            try {
                str = respValue.getList().get(0).getDesc();
            }catch (Exception e){
                str = aliSm.getData().get(0).getGuide();
            }
        }

        return str;
    }

    private String getChatResp2(String str) throws Exception{
        String json = HttpClientUtil.httpGet(CHAT_URL_2 + str);
        QykChat qykChat = JSON.parseObject(json, QykChat.class);
        if(qykChat.getResult() == null || qykChat.getResult() != 0){
            throw new RuntimeException("请求失败");
        }

        return qykChat.getContent();
    }
}
