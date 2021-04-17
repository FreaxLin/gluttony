package top.interc.gluttony.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class RsListedCompanyChina {
    private Integer id;

    @JsonProperty("mc")
    private String chCompany;

    @JsonProperty("dm")
    private String code;

    @JsonProperty("jys")
    private String stockExchange;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChCompany() {
        return chCompany;
    }

    public void setChCompany(String chCompany) {
        this.chCompany = chCompany == null ? null : chCompany.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange == null ? null : stockExchange.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}