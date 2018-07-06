package com.xkupc.framework.sequence.factory;

import com.xkupc.framework.sequence.exception.SequenceException;
import com.xkupc.framework.sequence.service.IdService;
import com.xkupc.framework.sequence.service.impl.TableIdServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author xk
 * @create 2018-07-05 14:20
 * @description id生成工厂
 **/
@Component
public class IdServiceFactory {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static int DEFAULT_LENGTH = 9;
    private TableIdServiceImpl idService;

    /**
     * 根据业务id获取id序列
     * 左补齐默认长度
     *
     * @param bizTag
     * @return
     */
    public String getId(String bizTag) {
        Long id = getIdByBizTag(bizTag);
        return StringUtils.leftPad(String.valueOf(id), DEFAULT_LENGTH, "0");
    }

    /**
     * 根据业务id获取id序列
     * 指定id长度
     *
     * @param bizTag
     * @param length
     * @return
     */
    public String getIdByBizTag(String bizTag, int length) {
        Long id = getIdByBizTag(bizTag);
        return StringUtils.leftPad(String.valueOf(id), length, "0");
    }


    private Long getIdByBizTag(String bizTag) {
        if (StringUtils.isEmpty(bizTag)) {
            throw new SequenceException("参数异常");
        }
        if (null == idService) {
            synchronized (IdService.class) {
                if (null == idService) {
                    idService = new TableIdServiceImpl(bizTag, false, jdbcTemplate);
                }
            }
        }
        return idService.getId();
    }
}
