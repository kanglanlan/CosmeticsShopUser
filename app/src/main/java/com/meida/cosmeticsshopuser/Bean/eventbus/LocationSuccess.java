package com.meida.cosmeticsshopuser.Bean.eventbus;

import com.amap.api.location.AMapLocation;
import com.meida.cosmeticsshopuser.nohttp.Params;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class LocationSuccess {

    public int locateType = 1;/*  1  定位成功  2  选择别的城市   -1  定位失败*/

    public int getLocateType() {
        return locateType;
    }

    public void setLocateType(int locateType) {
        Params.locateType = locateType;
        this.locateType = locateType;
    }

    private AMapLocation aMapLocation;

    public LocationSuccess(AMapLocation aMapLocation,int type) {
        this.aMapLocation = aMapLocation;
        this.locateType = type;
        Params.locateType = type;
    }

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }



}
