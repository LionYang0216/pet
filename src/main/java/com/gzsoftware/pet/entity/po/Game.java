package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class Game extends BaseEntity{
	
    private Integer id;

    private Integer picFileId;
       
    private Integer docFileId;
    
	private String nameCn;

    private String nameEn;

    private String descriptionCn;

    private String descriptionEn;

    private Integer lastUpdateAdminId;

    private Date lastUpdateTime;

    private Integer priority;

    private Short isEnable;

    private Integer visitCnt;

    private Integer favCnt;
    
    private Admin admin;
     
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

	public Integer getDocFileId() {
		return docFileId;
	}

	public void setDocFileId(Integer docFileId) {
		this.docFileId = docFileId;
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

    public String getDescriptionCn() {
        return descriptionCn;
    }

    public void setDescriptionCn(String descriptionCn) {
        this.descriptionCn = descriptionCn == null ? null : descriptionCn.trim();
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn == null ? null : descriptionEn.trim();
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

    public Integer getFavCnt() {
        return favCnt;
    }

    public void setFavCnt(Integer favCnt) {
        this.favCnt = favCnt;
    }
}