package com.gzsoftware.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ProdDao;
import com.gzsoftware.pet.entity.po.Prod;
import com.gzsoftware.pet.entity.po.ProdPic;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("prodService")
public class ProdService extends BaseService {

	@Resource
	private ProdDao prodDao;
	@Resource
	private ProdPicService prodPicService;
	@Resource
	private UploadFileService uploadFileService;
	@Autowired
	private UserProdFavService prodFavService;

	public Integer countAll(DataTablesRequest dtRequest) {
		return prodDao.countAll(dtRequest);
	}

	public List<Prod> getProdList(DataTablesRequest dtRequest) {
		return prodDao.getProdList(dtRequest);
	}

	public Prod getProd(Integer id) {
		Prod prod = prodDao.getProd(id);
		List<ProdPic> prodPicList = prodPicService.getProdPicList(id);
		prod.setProdPicList(prodPicList);
		return prod;
	}

	public int addProd(Prod record) {
		int eff = prodDao.addProd(record);
		addProdPicList(record.getProdPicList(), record.getId());
		return eff;
	}

	public int updateProd(Prod record) {
		int efft = prodDao.updateProd(record);
		List<ProdPic> oldList = prodPicService.getProdPicList(record.getId());
		removeOldProdPicList(oldList);
		addProdPicList(record.getProdPicList(), record.getId());
		return efft;
	}

	public int deleteProd(Integer id) {
		List<ProdPic> oldList = prodPicService.getProdPicList(id);
		removeOldProdPicList(oldList);
		prodFavService.deleteProdFavByProdId(id);//删除产品收藏的记录
		return prodDao.deleteProd(id);
	}

	public int updateProdForProdTypeDelete(String typeIdName, Integer typeValue) {
		Map map = new HashMap();
		map.put(typeIdName, typeValue);
		return prodDao.updateProdForProdTypeDelete(map);
	}

	public int addProdVisitCnt(int id) {
		return prodDao.addProdVisitCnt(id);
	}

	public int addProdUpCnt(int id) {
		return prodDao.addProdUpCnt(id);
	}

	public int addProdFavCnt(int id) {
		return prodDao.addProdFavCnt(id);
	}
	
	public int reduceProdFavCnt(int id) {
		Prod prod=prodDao.getProd(id);
		if(prod.getFavCnt()<=1){
			prod.setFavCnt(0);
		}else{
			prod.setFavCnt(prod.getFavCnt()-1);
		}
		return prodDao.reduceProdFavCnt(prod);
	}

	public List<Prod> getRefProdList(String refCode) {
		return prodDao.getRefProdList(refCode);
	}
	
	public List<Map> getTopVisitProdList(Integer length){
		return prodDao.getTopVisitProdList(length);
	}

	private void removeOldProdPicList(List<ProdPic> list) {
		if (list != null) {
			list.remove(null);
			for (ProdPic pic : list) {
				prodPicService.deleteProdPic(pic.getId());
				uploadFileService.updateFileToUnUsed(pic.getPicFileId());
			}
		}
	}

	private void addProdPicList(List<ProdPic> list, int prodId) {
		if (list != null) {
			list.remove(null);
			for (ProdPic pic : list) {
				if (pic.getPicFileId() != null) {
					pic.setProdId(prodId);
					prodPicService.addProdPic(pic);
					uploadFileService.updateFileToUsed(pic.getPicFileId());
				}
			}
		}
	}

	public List<Prod> getProdConList(Integer length) {
		return prodDao.getProdConList(length);
	}
}
