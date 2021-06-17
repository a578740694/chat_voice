package com.li.chatvoice.ai;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ************************************
 * create by Intellij IDEA
 *
 * @Author lisulong
 * @Date 2020/11/9 15:13
 * @Description AliSm
 * ************************************
 */
@Getter
@Setter
public class AliSmChat {

    private Integer status;

    private List<RespData> data;

    private Ext ext;
}