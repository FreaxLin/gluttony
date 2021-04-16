package top.interc.gluttony.web.model;

public class SiteZdbInvGroup {
    private Integer id;

    private Integer invRecordId;

    private String invGroupName;

    private String invGroupShowUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvRecordId() {
        return invRecordId;
    }

    public void setInvRecordId(Integer invRecordId) {
        this.invRecordId = invRecordId;
    }

    public String getInvGroupName() {
        return invGroupName;
    }

    public void setInvGroupName(String invGroupName) {
        this.invGroupName = invGroupName == null ? null : invGroupName.trim();
    }

    public String getInvGroupShowUrl() {
        return invGroupShowUrl;
    }

    public void setInvGroupShowUrl(String invGroupShowUrl) {
        this.invGroupShowUrl = invGroupShowUrl == null ? null : invGroupShowUrl.trim();
    }
}