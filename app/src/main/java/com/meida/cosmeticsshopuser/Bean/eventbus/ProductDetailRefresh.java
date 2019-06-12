package com.meida.cosmeticsshopuser.Bean.eventbus;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class ProductDetailRefresh {

    private boolean showTab;

    public boolean isShowTab() {
        return showTab;
    }

    public void setShowTab(boolean showTab) {
        this.showTab = showTab;
    }


    public ProductDetailRefresh(boolean showTab) {
        this.showTab = showTab;
    }


}
