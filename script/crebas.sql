/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/3/9 14:13:20                            */
/*==============================================================*/


drop table if exists ADMIN;

drop table if exists ADMIN_ROLE;

drop table if exists CONTENT;

drop table if exists GAME;

drop table if exists LINK;

drop table if exists MARQUEE;

drop table if exists NEWS;

drop table if exists NEWS_TYPE;

drop table if exists NODE;

drop table if exists PROD;

drop table if exists PROD_COST;

drop table if exists PROD_PIC;

drop table if exists PROD_TYPE_ARE;

drop table if exists PROD_TYPE_BIZ;

drop table if exists PROD_TYPE_CON;

drop table if exists PROD_TYPE_HIG;

drop table if exists PROD_TYPE_JAR;

drop table if exists PROD_TYPE_NEK;

drop table if exists PROD_TYPE_PHY;

drop table if exists PROD_TYPE_PIC;

drop table if exists PROD_TYPE_SOU;

drop table if exists PROD_TYPE_WID;

drop table if exists ROLE;

drop table if exists ROLE_NODE;

drop table if exists SHOP;

drop table if exists UPLOAD_FILE;

drop table if exists USER;

drop table if exists USER_BALANCE_LOG;

drop table if exists USER_DRAW;

drop table if exists USER_PROD_FAV;

drop table if exists USER_SHOP_FAV;

drop table if exists USER_WARE_FAV;

drop table if exists WARE;

drop table if exists WARE_TYPE_MCH;

drop table if exists WARE_TYPE_PRD;

drop table if exists WARE_TYPE_SRC;

drop table if exists WARE_TYPE_STE;

/*==============================================================*/
/* Table: ADMIN                                                 */
/*==============================================================*/
create table ADMIN
(
   ID                   int not null auto_increment,
   ACCOUNT              varchar(50) not null comment '登录帐号名',
   PASSWORD             varchar(32) comment '密码',
   ADMIN_NAME           varchar(50) comment '管理员姓名',
   HEAD_FILE_ID         int comment '头像文件ID',
   UPDATE_TIME          timestamp comment '更新时间',
   LAST_LOGIN_TIME      timestamp comment '最后登录时间',
   LAST_LOGIN_IP        varchar(50) comment '最后登录IP',
   LOGIN_COUNT          int default 0 comment '总共登录次数',
   IS_ENABLED           smallint not null default 0 comment '是否禁用 0为禁 1为开',
   primary key (ID)
);

alter table ADMIN comment '管理员表';

/*==============================================================*/
/* Table: ADMIN_ROLE                                            */
/*==============================================================*/
create table ADMIN_ROLE
(
   ID                   int not null auto_increment,
   ROLE_ID              int not null comment '角色ID',
   ADMIN_ID             int not null comment '管理员ID',
   primary key (ID)
);

alter table ADMIN_ROLE comment '管理员角色关系表';

/*==============================================================*/
/* Table: CONTENT                                               */
/*==============================================================*/
create table CONTENT
(
   ID                   int not null auto_increment,
   CODE                 varchar(20) comment '用于标记文章的标识，比如：公司介绍=companyIntro, 这样前台人员可以通过这个约定的编码，找到这条记录',
   NAME_CN              varchar(200) comment '类型名称',
   NAME_EN              varchar(200),
   CONTENT_URL          varchar(200) comment '该内容链接的URL（非必须）',
   DESCRIPTION_CN       varchar(8000) comment '文章内容（用RTF等维护）',
   DESCRIPTION_EN       varchar(8000) comment '文章内容（用RTF等维护）',
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   primary key (ID)
);

alter table CONTENT comment '前台某些静态文章（或局部文章）的内容表，文章举例：首页-合作伙伴  公司介绍 这一类特定的文章，这样就产生一条记录';

/*==============================================================*/
/* Table: GAME                                                  */
/*==============================================================*/
create table GAME
(
   ID                   int not null auto_increment,
   PIC_FILE_ID          int comment '图片路径：物理或网络路径',
   DOC_FILE_ID          int comment '参赛要填写的申请表格文件（给用户下载）',
   NAME_CN              varchar(200) comment '类型名称',
   NAME_EN              varchar(200),
   DESCRIPTION_CN       varchar(8000) comment '描述',
   DESCRIPTION_EN       varchar(8000),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint comment '是否上架',
   VISIT_CNT            int comment '访问数量： 前台点击+1',
   FAV_CNT              int comment '收藏数量: 没收藏多1人 +1',
   primary key (ID)
);

alter table GAME comment '赛事';

/*==============================================================*/
/* Table: LINK                                                  */
/*==============================================================*/
create table LINK
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(200) comment '类型名称',
   NAME_EN              varchar(200),
   URL                  varchar(200) comment '该内容链接的URL（非必须）',
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   primary key (ID)
);

alter table LINK comment '友情链接';

/*==============================================================*/
/* Table: MARQUEE                                               */
/*==============================================================*/
create table MARQUEE
(
   ID                   int not null auto_increment,
   CODE                 varchar(20) comment '用于标记烫板一页的归类标识，比如：首页顶部的烫板=home, 这样前台人员可以通过这个约定的编码，找到这堆记录（比如有5张图片的烫板，这里就有5条记录）',
   PIC_FILE_ID          int comment 'UPLOAD FILE的链接，图片',
   NAME_CN              varchar(200) comment '类型名称',
   NAME_EN              varchar(200),
   URL                  varchar(300) comment '烫板点击链接的URL',
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint comment '是否上架',
   VISIT_CNT            int comment '访问数量： 前台点击+1',
   primary key (ID)
);

alter table MARQUEE comment '前台某些滚动烫板的位置，比如首页顶部的滚动烫板，这是一个列表，比如首页有5张图片滚动，每个点击链接到某个位置，则就有5条';

/*==============================================================*/
/* Table: NEWS                                                  */
/*==============================================================*/
create table NEWS
(
   ID                   int not null auto_increment,
   TITLE_CN             varchar(400) comment '中文标题',
   TITLE_EN             varchar(400) comment '英文标题',
   PIC_FILE_ID          int,
   DESCRIPTION_CN       varchar(8000) comment '文章内容（用RTF等维护）',
   DESCRIPTION_EN       varchar(8000) comment '文章内容（用RTF等维护）',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint comment '是否上架',
   VISIT_CNT            int comment '访问数量： 前台点击+1',
   UP_CNT               int comment '点赞数量：点赞+1',
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   NEWS_TYPE_ID         int,
   primary key (ID)
);

alter table NEWS comment '行业动态';

/*==============================================================*/
/* Table: NEWS_TYPE                                             */
/*==============================================================*/
create table NEWS_TYPE
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table NEWS_TYPE comment '行业动态类型';

/*==============================================================*/
/* Table: NODE                                                  */
/*==============================================================*/
create table NODE
(
   ID                   int not null auto_increment comment 'ID',
   PARENT_ID            int comment '父功能id',
   NAME                 varchar(100) comment '菜单名称',
   ICON                 varchar(100) comment '图标或图标CSS',
   URL                  varchar(200) comment '菜单链接',
   DESCRIPTION          varchar(250) comment '描述',
   PRIORITY             int not null comment '功能菜单排列顺序',
   IS_MENU              smallint not null comment '是否为菜单 0为关 1为开',
   primary key (ID)
);

alter table NODE comment '功能模块菜单点表';

/*==============================================================*/
/* Table: PROD                                                  */
/*==============================================================*/
create table PROD
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(200) comment '类型名称',
   NAME_EN              varchar(200),
   REF_CODE             varchar(100) comment '关联编码',
   REF_NAME             varchar(100) comment '关联显示的名称',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint comment '是否上架',
   IS_NEW               smallint(0) comment '是否新产品',
   USER_ID              int comment '用户ID（设计师作品时）',
   LAST_UPDATE_ADMIN_ID int comment '管理员ID （管理员维护产品）',
   VISIT_CNT            int comment '访问数量： 前台点击+1',
   UP_CNT               int comment '点赞数量：点赞+1',
   FAV_CNT              int comment '收藏数量: 没收藏多1人 +1',
   DESCRIPTION_CN       varchar(4000) comment '描述',
   DESCRIPTION_EN       varchar(4000),
   PROD_TYPE_PHY_ID     int comment '物理类型',
   PROD_TYPE_PIC_ID     int comment '图库类型',
   PROD_TYPE_BIZ_ID     int comment '业务类型',
   PROD_TYPE_JAR_ID     int comment '灌装类型',
   PROD_TYPE_CON_ID     int comment '容量类型',
   PROD_TYPE_ARE_ID     int comment '区域类型',
   PROD_TYPE_HIG_ID     int comment '高度类型',
   PROD_TYPE_WID_ID     int comment '宽度类型',
   PROD_TYPE_NEK_ID     int comment '瓶口类型',
   PROD_TYPE_SOU_ID     int comment '来源类型',
   PROD_COST_ID         int,
   primary key (ID)
);

alter table PROD comment '产品信息表';

/*==============================================================*/
/* Table: PROD_COST                                             */
/*==============================================================*/
create table PROD_COST
(
   ID                   int not null auto_increment,
   COST                 decimal comment '查看详细时的费用',
   NAME                 varchar(500) comment '名称描述：比如免费',
   primary key (ID)
);

alter table PROD_COST comment '产品明细查看费用表 ( 费用设置常量表 )： 里面的记录比如: 1.免费 2.光身瓶库收费1元 3. 概念图库收费4元 ';

/*==============================================================*/
/* Table: PROD_PIC                                              */
/*==============================================================*/
create table PROD_PIC
(
   ID                   int not null auto_increment,
   PROD_ID              int comment '产品ID',
   PIC_FILE_ID          int comment '图片路径：物理或网络路径',
   IS_MAJOR             smallint comment '是否置顶图片，默认为否，如果为是，则作为列表区的主要图片',
   NAME_CN              varchar(500) comment '图片描述',
   NAME_EN              varchar(500),
   primary key (ID)
);

alter table PROD_PIC comment '产品大图表：每个产品可以有1~5张大图，前台轮播';

/*==============================================================*/
/* Table: PROD_TYPE_ARE                                         */
/*==============================================================*/
create table PROD_TYPE_ARE
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_ARE comment '产品地区类型表：亚洲，非洲，大洋洲，欧洲等';

/*==============================================================*/
/* Table: PROD_TYPE_BIZ                                         */
/*==============================================================*/
create table PROD_TYPE_BIZ
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_BIZ comment '产品业务类型表：饮料，日化，调味品，食品等';

/*==============================================================*/
/* Table: PROD_TYPE_CON                                         */
/*==============================================================*/
create table PROD_TYPE_CON
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_CON comment '产品容量类型表：500M以下，500-1000M等';

/*==============================================================*/
/* Table: PROD_TYPE_HIG                                         */
/*==============================================================*/
create table PROD_TYPE_HIG
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_HIG comment '产品高度类型表：150MM以下，150~200MM等';

/*==============================================================*/
/* Table: PROD_TYPE_JAR                                         */
/*==============================================================*/
create table PROD_TYPE_JAR
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_JAR comment '产品灌装类型表：冷罐装，热罐装，无菌灌装';

/*==============================================================*/
/* Table: PROD_TYPE_NEK                                         */
/*==============================================================*/
create table PROD_TYPE_NEK
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_NEK comment '产品瓶口类型表：30MM以下，30~60MM等';

/*==============================================================*/
/* Table: PROD_TYPE_PHY                                         */
/*==============================================================*/
create table PROD_TYPE_PHY
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_PHY comment '产品物理类型表：瓶样，瓶盖';

/*==============================================================*/
/* Table: PROD_TYPE_PIC                                         */
/*==============================================================*/
create table PROD_TYPE_PIC
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_PIC comment '产品图样类型表：成品图库，概念图库，光身图库，设计师作品';

/*==============================================================*/
/* Table: PROD_TYPE_SOU                                         */
/*==============================================================*/
create table PROD_TYPE_SOU
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_SOU comment '产品来源类型表（仅设计师作品专用）：原创作品，采集作品';

/*==============================================================*/
/* Table: PROD_TYPE_WID                                         */
/*==============================================================*/
create table PROD_TYPE_WID
(
   ID                   int not null auto_increment,
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table PROD_TYPE_WID comment '产品宽度类型表：30MM以下，30~60MM等';

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE
(
   ID                   int not null auto_increment comment 'ID',
   NAME                 varchar(50) comment '角色名字',
   DESCRIPTION          varchar(250) comment '描述',
   primary key (ID)
);

alter table ROLE comment '角色表';

/*==============================================================*/
/* Table: ROLE_NODE                                             */
/*==============================================================*/
create table ROLE_NODE
(
   ID                   int not null auto_increment comment 'ID',
   ROLE_ID              int not null comment '角色ID',
   NODE_ID              int not null comment '功能ID',
   primary key (ID)
);

alter table ROLE_NODE comment '角色功能关系表';

/*==============================================================*/
/* Table: SHOP                                                  */
/*==============================================================*/
create table SHOP
(
   ID                   int not null auto_increment comment 'ID',
   USER_ID              int not null comment '归属用户ID',
   NAME_CN              varchar(100) comment '商铺名字',
   NAME_EN              varchar(100),
   LOGO_FILE_ID         int,
   LEVEL                int comment '商家等级',
   STATUS               smallint not null default 0 comment '审批状态： 0为未审批（申请状态）  1为已审批可开通  2为下架禁用该商家',
   LOCATION             varchar(1000) comment '商家地址',
   OPER_MODE            varchar(200) comment '经营模式',
   TEL                  varchar(100) comment '联系电话',
   URL                  varchar(200) comment '企业网站',
   REG_FILE_ID          int,
   REG_TIME             timestamp comment '登记时间',
   VISIT_CNT            int comment '访问量',
   FAV_CNT              int comment '收藏量',
   INTRO_ENT            varchar(3000) comment '商铺企业介绍',
   INTRO_LOG            varchar(3000) comment '物流发货介绍',
   INTRO_SRV            varchar(3000) comment '售后服务介绍',
   REJECT_REASON        varchar(500) comment '拒绝原因',
   UPDATE_TIME          timestamp comment '更新时间',
   primary key (ID)
);

alter table SHOP comment '商家表';

/*==============================================================*/
/* Table: UPLOAD_FILE                                           */
/*==============================================================*/
create table UPLOAD_FILE
(
   ID                   int not null auto_increment,
   FILE_PATH            varchar(500) comment '图片路径：物理或网络路径',
   FILE_NAME            varchar(500) comment '原始文件名',
   IS_USED              smallint comment '是否在使用（0没有使用，1在使用）',
   primary key (ID)
);

alter table UPLOAD_FILE comment '放所有上传文件 FILE_PATH是你保存在【upload】目录下的相对路径，FILE_NAME是你上传这个文件的原始文';

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   ID                   int not null comment 'ID',
   ACCOUNT              varchar(50) not null comment '登录帐号名',
   PASSWORD             varchar(32) comment '密码',
   USER_NAME            varchar(50) comment '用户姓名',
   HEAD_FILE_ID         int comment '头像图片文件ID',
   UPDATE_TIME          timestamp comment '更新时间',
   REG_TIME             timestamp comment '注册时间',
   LAST_LOGIN_TIME      timestamp comment '最后登录时间',
   LAST_LOGIN_IP        varchar(50) comment '最后登录IP',
   LOGIN_COUNT          int default 0 comment '总共登录次数',
   IS_ENABLED           smallint not null default 0 comment '是否禁用 0为禁 1为开',
   SEX                  smallint comment '性别：1男，2女',
   POS                  varchar(32) comment '职业',
   LOCATION             varchar(300) comment '所在地',
   SIGN                 varchar(32) comment '签名',
   INTRO                varchar(1000),
   EMAIL                varchar(100),
   TEL                  varchar(32) comment '电话',
   WECHAT               varchar(32) comment '微信',
   QQ                   varchar(32) comment 'QQ',
   LABELS               varchar(500) comment '个人标签',
   BALANCE_TOTAL        decimal comment '财富余额',
   primary key (ID)
);

alter table USER comment '前台用户表';

/*==============================================================*/
/* Table: USER_BALANCE_LOG                                      */
/*==============================================================*/
create table USER_BALANCE_LOG
(
   ID                   int not null auto_increment,
   USER_ID              int,
   CHANGE_AMOUNT        decimal comment '改变的金额',
   CHANGE_TYPE          smallint comment '改变类型： 1. 为查看某些瓶库信息做成的消费 (简称：查看瓶库消费) 2. 为线下提现  6.为微信充值 7.为支付宝充值  8.为线下充值
            ',
   CHANGE_TIME          timestamp comment '业务发生时间',
   PROD_ID              int comment '如果是查看瓶库发生的费用，则对应是产品ID',
   CHARGE_ORDER_NUMBER  varchar(100) comment '微信或者支付宝充值时的第三方流水号（单号）',
   DESCRIPTION          varchar(1000) comment '附加信息（备用字段）',
   primary key (ID)
);

alter table USER_BALANCE_LOG comment '用户收支日志表';

/*==============================================================*/
/* Table: USER_DRAW                                             */
/*==============================================================*/
create table USER_DRAW
(
   ID                   int not null auto_increment,
   USER_ID              int comment '提现用户ID',
   DRAW_AMOUNT          int comment '提现金额',
   STAUTS               int comment '状态：0=申请，1=通过，2=拒绝',
   USER_REMARK          varchar(1000) comment '用户备注：提现到账银行帐号等信息',
   ADMIN_REMARK         varchar(1000) comment '管理员备注',
   USER_REQUEST_TIME    timestamp comment '会员申请提现日期',
   LAST_UPDATE_TIME     timestamp comment '管理员更新时间',
   LAST_UPDATE_ADMIN_ID int comment '处理的管理员ID ',
   primary key (ID)
);

alter table USER_DRAW comment '会员提现申请表';

/*==============================================================*/
/* Table: USER_PROD_FAV                                         */
/*==============================================================*/
create table USER_PROD_FAV
(
   ID                   int not null auto_increment,
   USER_ID              int,
   PROD_ID              int,
   primary key (ID)
);

alter table USER_PROD_FAV comment '用户产品收藏关系表';

/*==============================================================*/
/* Table: USER_SHOP_FAV                                         */
/*==============================================================*/
create table USER_SHOP_FAV
(
   ID                   int not null auto_increment,
   USER_ID              int comment '用户ID',
   SHOP_ID              int comment '商铺ID',
   primary key (ID)
);

alter table USER_SHOP_FAV comment '用户店铺收藏关系表';

/*==============================================================*/
/* Table: USER_WARE_FAV                                         */
/*==============================================================*/
create table USER_WARE_FAV
(
   ID                   int not null auto_increment,
   USER_ID              int comment '用户ID',
   WARE_ID              int comment '商品ID',
   primary key (ID)
);

alter table USER_WARE_FAV comment '用户商品收藏关系表';

/*==============================================================*/
/* Table: WARE                                                  */
/*==============================================================*/
create table WARE
(
   ID                   int not null auto_increment,
   SHOP_ID              int comment '管理员ID （管理员维护产品）',
   PIC_FILE_ID          int comment '硬件图片',
   PRICE                decimal comment '价格',
   NAME_CN              varchar(200) comment '类型名称',
   NAME_EN              varchar(200),
   DESCRIPTION_EN       varchar(4000),
   DESCRIPTION_CN       varchar(4000) comment '描述',
   LOCATION_CN          varchar(200) comment '产地',
   LOCATION_EN          varchar(200) comment '产地',
   EXPRESS_CN           varchar(200) comment '运费说明',
   EXPRESS_EN           varchar(200) comment '运费说明',
   AFTER_SERVICE_CN     varchar(400) comment '售后',
   AFTER_SERVICE_EN     varchar(400) comment '售后',
   ADAPT_MACHINE_CN     varchar(200) comment '适用型号',
   ADAPT_MACHINE_EN     varchar(200) comment '适用型号',
   PRODUCE_ABILITY_CN   varchar(200) comment '生产能力',
   PRODUCE_ABILITY_EN   varchar(200) comment '生产能力',
   BRAND_CN             varchar(200) comment '品牌',
   BRAND_EN             varchar(200) comment '品牌',
   MODAL_CN             varchar(200) comment '型号',
   MODAL_EN             varchar(200) comment '型号',
   RUNNER_CN            varchar(200) comment '流道',
   RUNNER_EN            varchar(200) comment '流道',
   PRODUCE_PRODUCT_CN   varchar(200) comment '产品',
   PRODUCE_PRODUCT_EN   varchar(200) comment '产品',
   MAKE_MODE_CN         varchar(200) comment '塑造模式',
   MAKE_MODE_EN         varchar(200) comment '塑造模式',
   PRODUCE_SOURCE_CN    varchar(200) comment '生产材料',
   PRODUCE_SOURCE_EN    varchar(200) comment '生产材料',
   LAST_UPDATE_USER_ID  varchar(32) comment '更新用户',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint comment '是否上架',
   IS_NEW               smallint(0) comment '是否新产品',
   VISIT_CNT            int comment '访问数量： 前台点击+1',
   UP_CNT               int comment '点赞数量：点赞+1',
   FAV_CNT              int comment '收藏数量: 没收藏多1人 +1',
   WARE_TYPE_SRC_ID     int,
   WARE_TYPE_MCH_ID     int,
   WARE_TYPE_STE_ID     int,
   WARE_TYPE_PRD_ID     int,
   primary key (ID)
);

alter table WARE comment '商品信息表';

/*==============================================================*/
/* Table: WARE_TYPE_MCH                                         */
/*==============================================================*/
create table WARE_TYPE_MCH
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table WARE_TYPE_MCH comment '商品机器类型表：吹瓶机，注塑机...';

/*==============================================================*/
/* Table: WARE_TYPE_PRD                                         */
/*==============================================================*/
create table WARE_TYPE_PRD
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table WARE_TYPE_PRD comment '商品制品表: 瓶坯, 瓶子, 瓶盖, 标签, 其他';

/*==============================================================*/
/* Table: WARE_TYPE_SRC                                         */
/*==============================================================*/
create table WARE_TYPE_SRC
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table WARE_TYPE_SRC comment '商品原材料类型表：PET,PP,PE...';

/*==============================================================*/
/* Table: WARE_TYPE_STE                                         */
/*==============================================================*/
create table WARE_TYPE_STE
(
   ID                   int not null auto_increment comment 'ID',
   NAME_CN              varchar(50) comment '类型名称',
   NAME_EN              varchar(50),
   LAST_UPDATE_ADMIN_ID int comment '更新管理员',
   LAST_UPDATE_TIME     timestamp comment '更新时间',
   PRIORITY             int comment '排序',
   IS_ENABLE            smallint,
   primary key (ID)
);

alter table WARE_TYPE_STE comment '商品步法类型表：瓶子募集(吹瓶) ， 瓶呸模具，';

