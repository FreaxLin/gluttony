package top.interc.gluttony.web.model;

import java.util.Date;

public class RsCompany {
    private Integer id;

    private String company;

    private String code;

    private Date companyCreateTime;

    private String trade;

    private String descrption;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getCompanyCreateTime() {
        return companyCreateTime;
    }

    public void setCompanyCreateTime(Date companyCreateTime) {
        this.companyCreateTime = companyCreateTime;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade == null ? null : trade.trim();
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption == null ? null : descrption.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}