package com.meida.cosmeticsshopuser.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/1/2 0002.
 */

public class PublishFindTypeBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{

        private List<FindTypeBean.TypeBean> data;

        public List<FindTypeBean.TypeBean> getData() {
            return data;
        }

        public void setData(List<FindTypeBean.TypeBean> data) {
            this.data = data;
        }
    }






}
