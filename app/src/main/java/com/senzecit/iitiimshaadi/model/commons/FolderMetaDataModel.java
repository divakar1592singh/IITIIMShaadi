package com.senzecit.iitiimshaadi.model.commons;

/**
 * Created by senzec on 9/2/18.
 */

public class FolderMetaDataModel {

    public FolderMetaDataModel(Integer metaId, String metaValue) {
        this.metaId = metaId;
        this.metaValue = metaValue;
    }

    public Integer getMetaId() {
        return metaId;
    }

    public void setMetaId(Integer metaId) {
        this.metaId = metaId;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    Integer metaId;
    String metaValue;
}
