CREATE TABLE `id_segment` (
  `biz_tag` varchar(50) NOT NULL COMMENT '业务标识',
  `max_id` bigint(20) DEFAULT NULL COMMENT '分配的id号段的最大值',
  `p_step` bigint(20) DEFAULT NULL COMMENT '步长',
  `max_sequence` bigint(20) DEFAULT '999999999',
  `last_update_time` datetime DEFAULT NULL,
  `current_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`biz_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='号段存储表';


insert into api.id_segment(biz_tag,max_id,p_step)values('order',0,20);

update api.id_segment set last_update_time=now(),current_update_time=now() where biz_tag='order';