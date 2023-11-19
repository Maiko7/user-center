package com.maiko.usercenterdemo.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 访客表
 * @TableName visitor
 */
@TableName(value ="visitor")
@Data
public class Visitor implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 被访者id
     */
    private Long toId;

    /**
     * 来访者id
     */
    private Long fromId;

    /**
     * 来访者头像
     */
    private String fromUrl;

    /**
     * 来访者留言
     */
    private String message;

    /**
     * 来访次数
     */
    private Integer amount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 是否为隐身访问
     */
    private Integer stealth;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}