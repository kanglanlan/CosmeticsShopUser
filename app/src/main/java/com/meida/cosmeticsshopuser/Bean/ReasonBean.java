package com.meida.cosmeticsshopuser.Bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/30 0030.
 */

public class ReasonBean extends BaseBean{

    private List<Reason> data;

    public List<Reason> getData() {
        return data;
    }

    public void setData(List<Reason> data) {
        this.data = data;
    }

    public class Reason{

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
