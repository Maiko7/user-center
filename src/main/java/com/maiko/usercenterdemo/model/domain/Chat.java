package com.maiko.usercenterdemo.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息表
 * @TableName chat
 */
@TableName(value ="chat")
@Data
public class Chat implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 队伍id(群聊)
     */
    private Long teamId;

    /**
     * 接收者id(私聊)
     */
    private Long toId;

    /**
     * 发送者id
     */
    private Long fromId;

    /**
     * 发送者昵称
     */
    private String fromName;

    /**
     * 发送者头像
     */
    private String fromUrl;

    /**
     * 内容
     */
    private String text;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 接收状态 0:未读 1:已读(私聊)
     */
    private Integer status;

    /**
     * 消息类型 0:队伍创建 1:队伍解散 2:成员加入 3:成员退出 4:队伍过期(系统消息)
     */
    private Integer type;

    /**
     * 消息类 0:私聊 1:群聊
     */
    private Integer scope;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}