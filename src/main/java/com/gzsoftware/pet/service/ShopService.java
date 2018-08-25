package com.gzsoftware.pet.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gzsoftware.pet.dao.ShopDao;
import com.gzsoftware.pet.entity.po.Shop;
import com.gzsoftware.pet.entity.po.Ware;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;

@Service("shopService")
public class ShopService extends BaseService {

	private static Log log = LogFactory.getLog(ShopService.class);

	@SuppressWarnings("restriction")
	@Resource
	private ShopDao shopDao;

	@Resource
	private WareService wareService;

	@Resource
	private UploadFileService uploadFileService;

	@Resource
	private UserShopFavService userShopFavService;

	public Shop getShop(int id) {
		return shopDao.getShop(id);
	}

	public Shop getShopByUser(int userId) {
		return shopDao.getShopByUser(userId);
	}

	public int addShop(Shop shop) {
		int effcnt = shopDao.addShop(shop);
		if (shop.getLogoFileId() != null) {
			uploadFileService.updateFileToUsed(shop.getLogoFileId());
		}
		if (shop.getRegFileId() != null) {
			uploadFileService.updateFileToUsed(shop.getRegFileId());
		}
		return effcnt;
	}

	public int deleteShop(int id) {
		Shop shop = shopDao.getShop(id);
		if (shop.getRegFileId() != null) {
			uploadFileService.updateFileToUnUsed(shop.getRegFileId());
		}
		if (shop.getLogoFileId() != null) {
			uploadFileService.updateFileToUnUsed(shop.getLogoFileId());
		}
		deleteShopWare(id);// 删除店铺下商品
		userShopFavService.deleteShopFavByShopId(id);// 删除商铺的收藏记录
		return shopDao.deleteShop(id);
	}

	private void deleteShopWare(Integer shopId) {
		List<Ware> list = wareService.getShopWareList(shopId);
		if (list == null || list.isEmpty())
			return;
		for (Ware ware : list) {
			wareService.deleteWare(ware.getId());
		}

	}

	public int updateShop(Shop shop) {
		if (shop.getLogoFileId() != null) {
			uploadFileService.updateFileToUsed(shop.getLogoFileId());
		}
		if (shop.getRegFileId() != null) {
			uploadFileService.updateFileToUsed(shop.getRegFileId());
		}
		return shopDao.updateShop(shop);
	}

	public Integer countAll(DataTablesRequest dtRequest) {
		return shopDao.countAll(dtRequest);
	}

	public List<Shop> getShopList(DataTablesRequest dtRequest) {
		return shopDao.getShopList(dtRequest);
	}

	public List<Shop> getShopSelect() {
		return shopDao.getShopSelect();
	}

	public int addShopVisitCnt(int id) {
		return shopDao.addShopVisitCnt(id);
	}

	public int addShopFavCnt(int id) {
		return shopDao.addShopFavCnt(id);
	}

	public int reduceShopFavCnt(int id) {
		Shop shop = shopDao.getShop(id);
		if (shop.getFavCnt() <= 1) {
			shop.setFavCnt(0);
		} else {
			shop.setFavCnt(shop.getFavCnt() - 1);
		}
		return shopDao.reduceShopFavCnt(shop);
	}

	public List<Shop> getShopApplyList(Integer length) {
		return shopDao.getShopApplyList(length);
	}
}
