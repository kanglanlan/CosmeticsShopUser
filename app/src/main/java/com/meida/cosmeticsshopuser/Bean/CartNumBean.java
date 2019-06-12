package com.meida.cosmeticsshopuser.Bean;

/**
 * Created by Administrator on 2019/1/10 0010.
 */

public class CartNumBean extends BaseBean{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        private int count ;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
