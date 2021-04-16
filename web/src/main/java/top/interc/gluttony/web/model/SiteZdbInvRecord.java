package top.interc.gluttony.web.model;

import java.util.Date;

public class SiteZdbInvRecord {
    private Integer id;

    private String invTime;

    private String invCompany;

    private String invTurn;

    private String invMoney;

    private String invCompanyUrl;

    private String descriptionUrl;

    private Date createDate;

    private Date udpateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvTime() {
        return invTime;
    }

    public void setInvTime(String invTime) {
        this.invTime = invTime == null ? null : invTime.trim();
    }

    public String getInvCompany() {
        return invCompany;
    }

    public void setInvCompany(String invCompany) {
        this.invCompany = invCompany == null ? null : invCompany.trim();
    }

    public String getInvTurn() {
        return invTurn;
    }

    public void setInvTurn(String invTurn) {
        this.invTurn = invTurn == null ? null : invTurn.trim();
    }

    public String getInvMoney() {
        return invMoney;
    }

    public void setInvMoney(String invMoney) {
        this.invMoney = invMoney == null ? null : invMoney.trim();
    }

    public String getInvCompanyUrl() {
        return invCompanyUrl;
    }

    public void setInvCompanyUrl(String invCompanyUrl) {
        this.invCompanyUrl = invCompanyUrl == null ? null : invCompanyUrl.trim();
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl == null ? null : descriptionUrl.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUdpateDate() {
        return udpateDate;
    }

    public void setUdpateDate(Date udpateDate) {
        this.udpateDate = udpateDate;
    }
}