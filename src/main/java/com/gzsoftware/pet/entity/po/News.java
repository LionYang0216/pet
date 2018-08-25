package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class News extends BaseEntity{
	
    private Integer id;

    private Integer picFileId;
    
    private Integer newsTypeId;
    
	private String titleCn;

    private String titleEn;

    private String descriptionCn;

    private String descriptionEn;

    private Integer lastUpdateAdminId;

    private Date lastUpdateTime;

    private Integer priority;

    private Short isEnable;

    private Integer visitCnt;

    private Integer upCnt;
    
    private Admin admin;
    
    private NewsType newsType;
    
    private UploadFile picFile;
     
    public NewsType getNewsType() {
		return newsType;
	}

	public void setNewsType(NewsType newsType) {
		this.newsType = newsType;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	
	
	public Integer getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewTypeId(Integer newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

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

	public String getTitleCn() {
		return titleCn;
	}

	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public Integer getUpCnt() {
		return upCnt;
	}

	public void setUpCnt(Integer upCnt) {
		this.upCnt = upCnt;
	}

    
}