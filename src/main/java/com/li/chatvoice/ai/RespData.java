package com.li.chatvoice.ai;

import lombok.Getter;
import lombok.Setter;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @Author lisulong
 * @Date 2020/11/9 17:21
 * @Description RespData
 * ************************************
 */
@Getter
@Setter
public class RespData {

    private String type;

    private String skill_name;

    private String intent_name;

    private String tid;

    private String guide;

    private RespValue value;
}
