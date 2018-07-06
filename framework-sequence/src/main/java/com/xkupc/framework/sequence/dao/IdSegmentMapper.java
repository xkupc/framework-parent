package com.xkupc.framework.sequence.dao;

import com.xkupc.framework.sequence.model.IdSegment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xk
 * @create 2018-07-06 15:51
 * @description
 **/
@Mapper
public interface IdSegmentMapper {
    IdSegment queryByBizTag(@Param("bizTag") String bizTag);

    int insertSelective(IdSegment record);

    int updateByByBizTag(IdSegment record);
}
