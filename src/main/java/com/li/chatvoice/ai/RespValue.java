package com.li.chatvoice.ai;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @Author lisulong
 * @Date 2020/11/9 17:21
 * @Description RespValue
 * ************************************
 */
@Getter
@Setter
public class RespValue {

    private List<DataList> list;

    private List<DataHeader> header;

    private List<DataScreen> screen;

    private List<DataMotion> motion;

    private String tts;

    private String answer;

    private String type;
}
