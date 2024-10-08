# 数据库初始化

-- 创建库
create database if not exists aiteacher;

 -- 切换库
 use aiteacher;

 -- 用户表
 create table if not exists user
 (
     id           bigint auto_increment comment 'id' primary key,
     userAccount  varchar(256)                           not null comment '账号',
     userPassword varchar(512)                           not null comment '密码',
     unionId      varchar(256)                           null comment '微信开放平台id',
     mpOpenId     varchar(256)                           null comment '公众号openId',
     userName     varchar(256)                           null comment '用户昵称',
     userAvatar   varchar(1024)                          null comment '用户头像',
     userProfile  varchar(512)                           null comment '用户简介',
     userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
     createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
     updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
     isDelete     tinyint      default 0                 not null comment '是否删除',
     index idx_unionId (unionId)
 ) comment '用户' collate = utf8mb4_unicode_ci;

INSERT INTO user (userAccount, userPassword, userName, userRole)
VALUES ('u5', 'a2854e8d5c945e3097cfa4d5f1ae2060', 'Example User', 'admin');


-- 文件表
create table if not exists file
(
    id           bigint auto_increment comment 'id' primary key,
    fileName     varchar(256)                           not null comment '文件名',
    userId       bigint                                 not null comment '创建用户id',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    fileSize     varchar(256)                           not null comment '文件大小',
    courseId      bigint                                 not null comment '课程id',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '文件' collate = utf8mb4_unicode_ci;



-- 课程表
create table if not exists course
(
    id           bigint auto_increment comment 'id' primary key,
    state        tinyint      default 0                 not null comment '题型',
    courseName           varchar(256)                           not null comment '课程名称',
    teacherId            bigint                                 not null comment '任课老师ID',
    courseDescription    varchar(256)                           null comment '课程简介',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '课程' collate = utf8mb4_unicode_ci;


-- 题目表
create table if not exists question
(
    id           bigint auto_increment comment 'id' primary key,
    courseId             bigint                                 not null comment '对应课程ID',
    Stem           varchar(256)                           not null comment '题干',
    state        tinyint      default 0                 not null comment '题型',
    byAi        tinyint      default 0                 not null comment 'ai出题',
    options           text                          not null comment '选项',
    teacherId            bigint                                 not null comment '出题老师ID',
    answer    varchar(256)                           null comment '答案',
    parse    varchar(256)                           null comment '解析',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '课程' collate = utf8mb4_unicode_ci;

-- 知识点表Knowledge points
create table if not exists knowledge
(
    id         bigint auto_increment comment 'id' primary key,
    Stem           varchar(256)                           not null comment '知识点名',
    teacherId            bigint                                 not null comment '创建老师ID',
    courseId   bigint                             not null comment '课程 id',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '知识点表';

-- 题目知识点对应表
create table if not exists question_knowledge
(
    id         bigint auto_increment comment 'id' primary key,
    questionId bigint                             not null comment '题目 id',
    knowledgeId   bigint                             not null comment '课程 id',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '题目知识点对应表';

-- 选课对应表
create table if not exists course_user
(
    id         bigint auto_increment comment 'id' primary key,
    courseId bigint                             not null comment '课程 id',
    userId   bigint                             not null comment '用户 id',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '选课对应表';


-- 试卷表
create table if not exists shijuan
(
    id          bigint auto_increment comment 'id' primary key,
    title       varchar(512)                       null comment '标题',
    questions   text                               null comment '题目（json 数组）',
    userId      bigint                             not null comment '创建用户 id',
    sumGrade    int          default 100           not null comment '总分',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    deadlineTime  datetime                                  comment '截止时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '试卷' collate = utf8mb4_unicode_ci;

-- shijuan提交表
create table if not exists shijuan_submit
(
    id         bigint auto_increment comment 'id' primary key,
    answer     text         comment '用户答案（json 数组）',
    status     int      default 0                 not null comment '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
    shijuanId  bigint   default 0                          not null comment '试卷 id',
    userId     bigint   default 0                          not null comment '用户 id',
    grade      int      default 0                         not null comment '成绩',
    sumGrade   int          default 100           not null comment '总分',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    userTime  datetime                                  comment '使用时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_shijuanId (shijuanId),
    index idx_userId (userId)
) comment 'shijuan提交';