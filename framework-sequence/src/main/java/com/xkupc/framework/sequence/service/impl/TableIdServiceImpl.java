package com.xkupc.framework.sequence.service.impl;


import com.xkupc.framework.sequence.model.IdSegment;
import com.xkupc.framework.sequence.service.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xk
 * @create 2018-07-05 11:13
 * @description
 **/
public class TableIdServiceImpl implements IdService {
    private static Logger log = LoggerFactory.getLogger(TableIdServiceImpl.class);

    // 创建线程池
    private ExecutorService taskExecutor;

    private JdbcTemplate jdbcTemplate;

    public void setTaskExecutor(ExecutorService taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    // 这两段用来存储每次拉升之后的最大值
    private volatile IdSegment[] segment = new IdSegment[2];
    private volatile boolean sw;
    //是否异步加载Segment
    private boolean asynLoadingSegment;
    //业务标签
    private String bizTag;
    //当前id值
    private AtomicLong currentId;

    private static ReentrantLock lock = new ReentrantLock();

    FutureTask<Boolean> asynLoadSegmentTask = null;

    public TableIdServiceImpl(String bizTag, boolean asynLoadingSegment, JdbcTemplate jdbcTemplate) {
        this.setBizTag(bizTag);
        this.setAsynLoadingSegment(asynLoadingSegment);
        this.setJdbcTemplate(jdbcTemplate);
        init();
    }

    public Long getId() {
        if (asynLoadingSegment) {
            return asynGetId();
        } else {
            return synGetId();
        }
    }

    public void init() {
        if (this.bizTag == null) {
            throw new RuntimeException("bizTag must be not null");
        }
        if (this.jdbcTemplate == null) {
            throw new RuntimeException("jdbcTemplate must be not null");
        }
        if (taskExecutor == null) {
            taskExecutor = Executors.newSingleThreadExecutor();
        }
        setSw(false);
        initIdSegment(index(), bizTag);
        currentId = new AtomicLong(segment[index()].getMinId()); // 初始id
        log.info("init run success...");
    }

    /**
     * 异步切换
     *
     * @return
     */
    private Long asynGetId() {
        if (segment[index()].getMiddleId().compareTo(currentId.longValue()) <= 0) {
            try {
                lock.lock();
                if (segment[index()].getMiddleId().equals(currentId.longValue())) {
                    // 前一段使用了50%
                    asynLoadSegmentTask = new FutureTask<Boolean>(new Callable<Boolean>() {
                        public Boolean call() throws Exception {
                            final int currentIndex = reIndex();
                            segment[currentIndex] = doUpdateNextSegment(bizTag);
                            return true;
                        }
                    });
                    taskExecutor.submit(asynLoadSegmentTask);
                }
                if (segment[index()].getMaxId().equals(currentId.longValue())) {
                    boolean loadingResult;
                    try {
                        loadingResult = asynLoadSegmentTask.get();
                        if (loadingResult) {
                            setSw(!isSw()); // 切换
                            currentId = new AtomicLong(segment[index()].getMinId()); // 进行切换
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 强制同步切换
                        final int currentIndex = reIndex();
                        segment[currentIndex] = doUpdateNextSegment(bizTag);
                        setSw(!isSw()); // 切换
                        currentId = new AtomicLong(segment[index()].getMinId()); // 进行切换
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        // 强制同步切换
                        final int currentIndex = reIndex();
                        segment[currentIndex] = doUpdateNextSegment(bizTag);
                        setSw(!isSw()); // 切换
                        currentId = new AtomicLong(segment[index()].getMinId()); // 进行切换
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return currentId.incrementAndGet();
    }

    private int reIndex() {
        if (isSw()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 同步切换
     *
     * @return
     */
    private Long synGetId() {
        if (segment[index()].getMiddleId().compareTo(currentId.longValue()) <= 0) {
            try {
                lock.lock();
                if (segment[index()].getMiddleId().equals(currentId.longValue())) {
                    // 使用50%进行加载
                    final int currentIndex = reIndex();
                    segment[currentIndex] = doUpdateNextSegment(bizTag);
                }
                if (segment[index()].getMaxId().equals(currentId.longValue())) {
                    setSw(!isSw()); // 切换
                    currentId = new AtomicLong(segment[index()].getMinId()); // 进行切换
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.lock();
            }
        }
        return currentId.incrementAndGet();
    }

    /**
     * 初始化segment
     *
     * @param index
     */
    private void initIdSegment(int index, String bizTag) {
        try {
            segment[index] = doUpdateNextSegment(bizTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isSw() {
        return sw;
    }

    private void setSw(boolean sw) {
        this.sw = sw;
    }

    private int index() {
        if (isSw()) {
            return 1;
        } else {
            return 0;
        }
    }

    private IdSegment doUpdateNextSegment(String bizTag) {
        try {
            return updateId(bizTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private IdSegment updateId(String bizTag) throws Exception {

        String querySql = "select P_STEP ,MAX_ID ,LAST_UPDATE_TIME,CURRENT_UPDATE_TIME,MAX_SEQUENCE from ID_SEGMENT where BIZ_TAG=?";
        String updateSql = "update ID_SEGMENT set MAX_ID=?,LAST_UPDATE_TIME=?,CURRENT_UPDATE_TIME=SYSDATE where BIZ_TAG=? and MAX_ID=?";

        final IdSegment currentSegment = new IdSegment();
        this.jdbcTemplate.query(querySql, new String[]{bizTag}, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Long step = null;
                Long currentMaxId = null;
                step = rs.getLong("P_STEP");
                currentMaxId = rs.getLong("MAX_ID");
                Long max = rs.getLong("MAX_SEQUENCE");
                Date lastUpdateTime = new Date();
                if (rs.getTimestamp("LAST_UPDATE_TIME") != null) {
                    lastUpdateTime = (Date) rs.getTimestamp("LAST_UPDATE_TIME");
                }

                Date currentUpdateTime = new Date();
                if (rs.getTimestamp("CURRENT_UPDATE_TIME") != null) {
                    currentUpdateTime = (Date) rs.getTimestamp("CURRENT_UPDATE_TIME");
                }
                currentSegment.setStep(step);
                currentSegment.setMaxId(currentMaxId);
                currentSegment.setLastUpdateTime(lastUpdateTime);
                currentSegment.setCurrentUpdateTime(currentUpdateTime);
                currentSegment.setMax(max);
            }
        });
        Long newMaxId = currentSegment.getMaxId() + currentSegment.getStep();
        newMaxId = newMaxId > currentSegment.getMax() ? 1 : newMaxId;
        int row = this.jdbcTemplate.update(updateSql,
                new Object[]{newMaxId, currentSegment.getCurrentUpdateTime(), bizTag, currentSegment.getMaxId()});
        if (row == 1) {
            IdSegment newSegment = new IdSegment();
            newSegment.setStep(currentSegment.getStep());
            newSegment.setMaxId(newMaxId);
            return newSegment;
        } else {
            return updateId(bizTag); // 递归，直至更新成功
        }

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setBizTag(String bizTag) {
        this.bizTag = bizTag;
    }


    public void setAsynLoadingSegment(boolean asynLoadingSegment) {
        this.asynLoadingSegment = asynLoadingSegment;
    }

}
