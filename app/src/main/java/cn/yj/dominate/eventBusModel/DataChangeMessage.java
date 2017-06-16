package cn.yj.dominate.eventBusModel;

/**
 * Created by yangjie on 2017/6/16.
 */

public class DataChangeMessage {
    private boolean isShowSystemData;

    public DataChangeMessage(boolean isShowSystemData) {
        this.isShowSystemData = isShowSystemData;
    }

    public boolean isShowSystemData() {
        return isShowSystemData;
    }

    public void setShowSystemData(boolean showSystemData) {
        isShowSystemData = showSystemData;
    }
}
