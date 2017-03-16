create table t_pushkey(
	ID int primary key not null auto_increment,
	PROV_CODE varchar(6) comment '省编码',
	PUSH_KEY varchar(10) comment '密钥',
	DEADLINE_DATE datetime comment '截止时间',
	CREATE_DATE datetime comment '数据创建时间',
	STATE char(1) comment '1：密钥生成成功，2密钥推送成功，3密钥推送失败，4密钥失效',
	OP_DATE datetime comment '数据操作时间',
	REMARK varchar(100) comment '备注信息'
);
create table t_rsa_keys(
	ID int primary key not null auto_increment,
	PROV_CODE varchar(6) comment '省编码',
	PRIVATE_KEY varchar(2000) comment '私钥',
	PUBLIC_KEY varchar(2000) comment '公钥',
	CREATE_DATE datetime comment '数据创建时间',
	STATE char(1) comment '1表示有效'
);
create table t_sign_info
(
  REQUEST_SOURCE varchar(10),
  OP_CODE      varchar(30),
  TRANSACTION_ID varchar(50),
  BILL_ID         varchar(11),
  REQUEST_TYPE   varchar(10),
  BUSI_TYPE       varchar(5),
  TIME_TAG       datetime,
  STATE_DESC     varchar(20),
  STATE          char(1),
  CREATE_DATE    datetime,
  SIGNATURE      varchar(1000)
);
create table t_web_cache_data
(
  CACHE_TYPE      varchar(100) not null comment '缓存类型',
  CACHE_KEY       varchar(100) not null comment '缓存key',
  CACHE_VALUE     varchar(2550) comment '缓存value',
  CACHE_SOURE     varchar(100) comment '缓存来源',
  CACHE_IS_LIVELY CHAR(1) default '0' comment '0非活跃，1活跃',
  STATE          CHAR(1) comment '状态(1,正常  E,删除)',
  REMARK          varchar(100) comment '备注'
);