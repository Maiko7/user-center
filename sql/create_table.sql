# 数据库初始化
-- 创建库
create database if not exists project;

-- 切换库
use project;

# 用户表
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    planetCode   varchar(512)                       null comment '星球编号'
)
    comment '用户';

# 导入示例用户
INSERT INTO project.user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode) VALUES ('鱼皮', 'yupi', 'https://himg.bdimg.com/sys/portraitn/item/public.1.e137c1ac.yS1WqOXfSWEasOYJ2-0pvQ', null, 'b0dd3697a192885d7c055db46155b26a', null, null, 0, '2023-08-06 14:14:22', '2023-08-06 14:39:37', 0, 1, '1');



-- 队伍表
create table if not exists team
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(8)                           null comment '队伍名称',
    description varchar(64)                          null comment '描述',
    avatarUrl   varchar(64)                          null comment '队伍头像---',
    maxNum      tinyint(2) default 1                 null comment '最大人数',
    userId      bigint                               null comment '队长id',
    password    varchar(32)                          null comment '密码',
    expireTime  datetime                             null comment '过期时间',
    createTime  datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    status      tinyint(1) default 0                 null comment '0:公开，1:私有，2:加密',
    isDelete    tinyint(1) default 0                 null comment '是否删除'
    )
    comment '队伍表';


-- 用户队伍关系表
create table if not exists user_team
(
    id         bigint auto_increment comment 'id' primary key,
    userId     bigint                               null comment '用户id',
    teamId     bigint                               null comment '队伍id',
    createTime datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    ties       tinyint(1) default 0                 null comment '0:未加入/已退出 1:已加入 2:队长/创建者',
    isDelete   tinyint(1) default 0                 not null comment '是否删除'
    )
    comment '用户队伍关系表';

-- 访客表
create table if not exists visitor
(
    id         bigint auto_increment comment 'id' primary key,
    toId       bigint                               null comment '被访者id',
    fromId     bigint                               null comment '来访者id',
    fromUrl    varchar(64)                          null comment '来访者头像',
    message    varchar(128)                         null comment '来访者留言',
    amount     int        default 1                 null comment '来访次数',
    createTime datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    stealth    tinyint(1) default 0                 null comment '是否为隐身访问',
    isDelete   tinyint(1) default 0                 not null comment '是否删除'
    )
    comment '访客表';

-- 好友表
create table if not exists friends
(
    id         bigint auto_increment comment 'id' primary key,
    userId     bigint                               null comment '用户id',
    friendId   bigint                               null comment '好友id',
    note       varchar(8)                           null comment '好友备注',
    friendUrl  varchar(64)                          null comment '好友头像',
    createTime datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint(1) default 0                 not null comment '是否删除'
    )
    comment '好友表';

-- 消息表
create table if not exists chat
(
    id         bigint auto_increment primary key,
    teamId     bigint                               null comment '队伍id(群聊)',
    toId       bigint                               null comment '接收者id(私聊)',
    fromId     bigint                               null comment '发送者id',
    fromName   varchar(8)                           null comment '发送者昵称',
    fromUrl    varchar(64)                          null comment '发送者头像',
    text       varchar(128)                         null comment '内容',
    createTime datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    status     tinyint(1) default 0                 null comment '接收状态 0:未读 1:已读(私聊)',
    type       tinyint(1) default 0                 null comment '消息类型 0:队伍创建 1:队伍解散 2:成员加入 3:成员退出 4:队伍过期(系统消息)',
    scope      tinyint(1) default 1                 null comment '消息类 0:私聊 1:群聊',
    isDelete   tinyint(1) default 0                 null comment '是否删除'
    )
    comment '消息表';

