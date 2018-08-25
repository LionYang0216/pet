package com.gzsoftware.pet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.controller.BackController;
import com.gzsoftware.pet.dao.UploadFileDao;
import com.gzsoftware.pet.entity.po.UploadFile;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;


@Service("uploadFileService")
public class UploadFileService extends BaseService{

	private static Log log = LogFactory.getLog(UploadFileService.class);
	
	@SuppressWarnings("restriction")
	@Resource
	private UploadFileDao uploadFileDao;

	public UploadFile getUploadFile(int id) {
		return uploadFileDao.getUploadFile(id);
	}
	
	public int addUploadFile(UploadFile uploadFile) {
		return uploadFileDao.addUploadFile(uploadFile);
	}

	public int deleteUploadFile(int id) {
		return uploadFileDao.deleteUploadFile(id);
	}
	
	public int updateUploadFile(UploadFile uploadFile) {
	   return uploadFileDao.updateUploadFile(uploadFile);
	}
  
    public int updateFileToUnUsed(int id){
	    return uploadFileDao.updateFileToUnUsed(id);
    }	 
    
    public int updateFileToUsed(int id){
    	  return uploadFileDao.updateFileToUsed(id);
    }

	public List<UploadFile> getUploadFileAllNotUse() {
		return uploadFileDao.getUploadFileAllNotUse();
	}

    
    
}
