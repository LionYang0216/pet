package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class Marquee extends BaseEntity{
	
    private Integer id;

    private Integer picFileId;
    
    private String code;
       
	private String nameCn;

    private String nameEn;
    
    private String url;
    
    private Integer lastUpdateAdminId;

    private Date lastUpdateTime;

    private Integer priority;

    private Short isEnable;

    private Integer visitCnt;

	private Admin admin;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


     
    public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	private UploadFile picFile;

    public UploadFile getPicFile() {
    	
		return picFile;
	}

	public void setPicFile(UploadFile picFile) {
		this.picFile = picFile;
	}
   
	private UploadFile docFile;

	public UploadFile getDocFile() {
		return docFile;
	}

	public void setDocFile(UploadFile docFile) {
		this.docFile = docFile;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  

    public Integer getPicFileId() {
		return picFileId;
	}

	public void setPicFileId(Integer picFileId) {
		this.picFileId = picFileId;
	}

	public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public Integer getLastUpdateAdminId() {
        return lastUpdateAdminId;
    }

    public void setLastUpdateAdminId(Integer lastUpdateAdminId) {
        this.lastUpdateAdminId = lastUpdateAdminId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Short getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Short isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getVisitCnt() {
        return visitCnt;
    }

    public void setVisitCnt(Integer visitCnt) {
        this.visitCnt = visitCnt;
    }

}