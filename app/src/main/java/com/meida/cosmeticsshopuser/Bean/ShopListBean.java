package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8 0008.
 */

public class ShopListBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean extends BaseListBean{
        private List<ShopItemBean> data;

        public List<ShopItemBean> getData() {
            return data;
        }

        public void setData(List<ShopItemBean> data) {
            this.data = data;
        }
    }

}
