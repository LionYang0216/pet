package com.gzsoftware.pet.dao;


import java.util.List;

import com.gzsoftware.pet.entity.po.UploadFile;

public interface UploadFileDao {

	public UploadFile getUploadFile(int id);

	public int addUploadFile(UploadFile uploadFile);

	public int updateUploadFile (UploadFile uploadFile);
	
	public int deleteUploadFile(int id);

	public int updateFileToUnUsed(int id);

	public int updateFileToUsed(int id);

	public List<UploadFile> getUploadFileAllNotUse();

}