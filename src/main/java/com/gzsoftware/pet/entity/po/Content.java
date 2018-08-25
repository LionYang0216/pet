package com.gzsoftware.pet.entity.po;

import java.util.Date;

public class Content extends BaseEntity{
	
    private Integer id;

    private String code;
    
    private String contentUrl;
    
	private String nameCn;

    private String nameEn;

    private String descriptionCn;

    private String descriptionEn;

    private Integer lastUpdateAdminId;

    private Date lastUpdateTime;

    
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

	public String getCode() {
		String codeStr = "";
		if(this.code.equals("HOME_BRAND_AREA")){
			codeStr = "首页合作品牌区域";
		}
		if(this.code.equals("BRAND_PAGE")){
			codeStr = "合作品牌页";
		}
		if(this.code.equals("COMP_PAGE")){
			codeStr = "公司介绍页";
		}
		if(this.code.equals("JOB_PAGE")){
			codeStr = "招聘专栏页";
		}
		if(this.code.equals("CONTACT_PAGE")){
			codeStr = "联系我们";
		}
		if(this.code.equals("LAW_PAGE")){
			codeStr = "私隐与政策";
		}
		
		return codeStr;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

    
}