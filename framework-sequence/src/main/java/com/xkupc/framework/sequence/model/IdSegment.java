package com.xkupc.framework.sequence.model;

import java.util.Date;

/**
 * @author xk
 * @create 2018-07-05 11:15
 * @description
 **/
public class IdSegment {

    private Long minId;

    private Long max;

    private Long maxId;
    //每次增加步长
    private Long step;
    //切换值
    private Long middleId;

    private Date lastUpdateTime;
    private Date currentUpdateTime;

    public IdSegment() {
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCurrentUpdateTime() {
        return currentUpdateTime;
    }

    public void setCurrentUpdateTime(Date currentUpdateTime) {
        this.currentUpdateTime = currentUpdateTime;
    }

    public Long getMiddleId() {

        if (this.middleId == null) {
            this.middleId = this.maxId - (long) Math.round(step / 2);
        }
        return middleId;
    }

    public Long getMinId() {
        if (this.minId == null) {
            if (this.maxId != null && this.step != null) {
                this.minId = this.maxId - this.step;
            } else {
                throw new RuntimeException("maxid or step is null");
            }
        }
        return minId;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
