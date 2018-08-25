/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.0.0
 */

package com.gzsoftware.pet.entity.po;

import java.util.Date;
import java.util.List;

/**
 * 产品信息表(PROD)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-17
 */
public class Prod extends BaseEntity {
	/** 版本号 */
	private static final long serialVersionUID = 5641278934186502481L;

	public int PROD_ENABLE_ACTIVE = 1; // 上架

	public int PROD_ENABLE_DOWN = 0; // 下架

	public int PROD_ENABLE_CON = 2; // 待确认
	/**  */
	private Integer id;

	/** 类型名称 */
	private String nameCn;

	/**  */
	private String nameEn;
	/** 关联编码 */
	private String refCode;
	/** 关联显示名 */
	private String refName;

	/** 更新时间 */
	private Date lastUpdateTime;

	/** 排序 */
	private Integer priority;

	/** 是否上架 */
	private Integer isEnable;

	/** 是否新产品 */
	private Integer isNew;

	/** 用户ID（设计师作品时） */
	private Integer userId;

	/** 访问数量： 前台点击+1 */
	private Integer visitCnt;

	/** 点赞数量：点赞+1 */
	private Integer upCnt;

	/** 收藏数量: 没收藏多1人 +1 */
	private Integer favCnt;

	/** 描述 */
	private String descriptionCn;

	/**  */
	private String descriptionEn;

	/** 物理类型 */
	private Integer prodTypePhyId;

	/** 图库类型 */
	private Integer prodTypePicId;

	/** 业务类型 */
	private Integer prodTypeBizId;

	/** 灌装类型 */
	private Integer prodTypeJarId;

	/** 容量类型 */
	private Integer prodTypeConId;

	/** 区域类型 */
	private Integer prodTypeAreId;

	/** 高度类型 */
	private Integer prodTypeHigId;

	/** 宽度类型 */
	private Integer prodTypeWidId;

	/** 瓶口类型 */
	private Integer prodTypeNekId;

	/** 来源类型 */
	private Integer prodTypeSouId;

	/**  */
	private Integer prodCostId;

	private Integer lastUpdateAdminId;

	private String extEnLayout;

	private String extCnLayout;

	private ProdTypePhy prodTypePhy;
	private ProdTypeWid prodTypeWid;
	private ProdTypeSou prodTypeSou;
	private ProdTypeCon prodTypeCon;
	private ProdTypeHig prodTypeHig;
	private ProdTypeNek prodTypeNek;
	private ProdTypeAre prodTypeAre;
	private ProdTypeBiz prodTypeBiz;
	private ProdTypePic prodTypePic;
	private ProdTypeJar prodTypeJar;
	private Admin admin;
	private ProdCost prodCost;
	private User user;
	private ProdPic majorPic;

	List<ProdPic> prodPicList;

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取类型名称
	 * 
	 * @return 类型名称
	 */
	public String getNameCn() {
		return this.nameCn;
	}

	/**
	 * 设置类型名称
	 * 
	 * @param nameCn
	 *            类型名称
	 */
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getNameEn() {
		return this.nameEn;
	}

	/**
	 * 设置
	 * 
	 * @param nameEn
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * 获取更新时间
	 * 
	 * @return 更新时间
	 */
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	/**
	 * 设置更新时间
	 * 
	 * @param lastUpdateTime
	 *            更新时间
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public Integer getPriority() {
		return this.priority;
	}

	/**
	 * 设置排序
	 * 
	 * @param priority
	 *            排序
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * 获取是否上架
	 * 
	 * @return 是否上架
	 */
	public Integer getIsEnable() {
		return this.isEnable;
	}

	/**
	 * 设置是否上架
	 * 
	 * @param isEnable
	 *            是否上架
	 */
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	/**
	 * 获取是否新产品
	 * 
	 * @return 是否新产品
	 */
	public Integer getIsNew() {
		return this.isNew;
	}

	/**
	 * 设置是否新产品
	 * 
	 * @param isNew
	 *            是否新产品
	 */
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	/**
	 * 获取用户ID（设计师作品时）
	 * 
	 * @return 用户ID（设计师作品时）
	 */
	public Integer getUserId() {
		return this.userId;
	}

	/**
	 * 设置用户ID（设计师作品时）
	 * 
	 * @param userId
	 *            用户ID（设计师作品时）
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 获取访问数量： 前台点击+1
	 * 
	 * @return 访问数量
	 */
	public Integer getVisitCnt() {
		return this.visitCnt;
	}

	/**
	 * 设置访问数量： 前台点击+1
	 * 
	 * @param visitCnt
	 *            访问数量
	 */
	public void setVisitCnt(Integer visitCnt) {
		this.visitCnt = visitCnt;
	}

	/**
	 * 获取点赞数量：点赞+1
	 * 
	 * @return 点赞数量
	 */
	public Integer getUpCnt() {
		return this.upCnt;
	}

	/**
	 * 设置点赞数量：点赞+1
	 * 
	 * @param upCnt
	 *            点赞数量
	 */
	public void setUpCnt(Integer upCnt) {
		this.upCnt = upCnt;
	}

	/**
	 * 获取收藏数量: 没收藏多1人 +1
	 * 
	 * @return 收藏数量
	 */
	public Integer getFavCnt() {
		return this.favCnt;
	}

	/**
	 * 设置收藏数量: 没收藏多1人 +1
	 * 
	 * @param favCnt
	 *            收藏数量
	 */
	public void setFavCnt(Integer favCnt) {
		this.favCnt = favCnt;
	}

	/**
	 * 获取描述
	 * 
	 * @return 描述
	 */
	public String getDescriptionCn() {
		return this.descriptionCn;
	}

	/**
	 * 设置描述
	 * 
	 * @param descriptionCn
	 *            描述
	 */
	public void setDescriptionCn(String descriptionCn) {
		this.descriptionCn = descriptionCn;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getDescriptionEn() {
		return this.descriptionEn;
	}

	/**
	 * 设置
	 * 
	 * @param descriptionEn
	 */
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	/**
	 * 获取物理类型
	 * 
	 * @return 物理类型
	 */
	public Integer getProdTypePhyId() {
		return this.prodTypePhyId;
	}

	/**
	 * 设置物理类型
	 * 
	 * @param prodTypePhyId
	 *            物理类型
	 */
	public void setProdTypePhyId(Integer prodTypePhyId) {
		this.prodTypePhyId = prodTypePhyId;
	}

	/**
	 * 获取图库类型
	 * 
	 * @return 图库类型
	 */
	public Integer getProdTypePicId() {
		return this.prodTypePicId;
	}

	/**
	 * 设置图库类型
	 * 
	 * @param prodTypePicId
	 *            图库类型
	 */
	public void setProdTypePicId(Integer prodTypePicId) {
		this.prodTypePicId = prodTypePicId;
	}

	/**
	 * 获取业务类型
	 * 
	 * @return 业务类型
	 */
	public Integer getProdTypeBizId() {
		return this.prodTypeBizId;
	}

	/**
	 * 设置业务类型
	 * 
	 * @param prodTypeBizId
	 *            业务类型
	 */
	public void setProdTypeBizId(Integer prodTypeBizId) {
		this.prodTypeBizId = prodTypeBizId;
	}

	/**
	 * 获取灌装类型
	 * 
	 * @return 灌装类型
	 */
	public Integer getProdTypeJarId() {
		return this.prodTypeJarId;
	}

	/**
	 * 设置灌装类型
	 * 
	 * @param prodTypeJarId
	 *            灌装类型
	 */
	public void setProdTypeJarId(Integer prodTypeJarId) {
		this.prodTypeJarId = prodTypeJarId;
	}

	/**
	 * 获取容量类型
	 * 
	 * @return 容量类型
	 */
	public Integer getProdTypeConId() {
		return this.prodTypeConId;
	}

	/**
	 * 设置容量类型
	 * 
	 * @param prodTypeConId
	 *            容量类型
	 */
	public void setProdTypeConId(Integer prodTypeConId) {
		this.prodTypeConId = prodTypeConId;
	}

	/**
	 * 获取区域类型
	 * 
	 * @return 区域类型
	 */
	public Integer getProdTypeAreId() {
		return this.prodTypeAreId;
	}

	/**
	 * 设置区域类型
	 * 
	 * @param prodTypeAreId
	 *            区域类型
	 */
	public void setProdTypeAreId(Integer prodTypeAreId) {
		this.prodTypeAreId = prodTypeAreId;
	}

	/**
	 * 获取高度类型
	 * 
	 * @return 高度类型
	 */
	public Integer getProdTypeHigId() {
		return this.prodTypeHigId;
	}

	/**
	 * 设置高度类型
	 * 
	 * @param prodTypeHigId
	 *            高度类型
	 */
	public void setProdTypeHigId(Integer prodTypeHigId) {
		this.prodTypeHigId = prodTypeHigId;
	}

	/**
	 * 获取宽度类型
	 * 
	 * @return 宽度类型
	 */
	public Integer getProdTypeWidId() {
		return this.prodTypeWidId;
	}

	/**
	 * 设置宽度类型
	 * 
	 * @param prodTypeWidId
	 *            宽度类型
	 */
	public void setProdTypeWidId(Integer prodTypeWidId) {
		this.prodTypeWidId = prodTypeWidId;
	}

	/**
	 * 获取瓶口类型
	 * 
	 * @return 瓶口类型
	 */
	public Integer getProdTypeNekId() {
		return this.prodTypeNekId;
	}

	/**
	 * 设置瓶口类型
	 * 
	 * @param prodTypeNekId
	 *            瓶口类型
	 */
	public void setProdTypeNekId(Integer prodTypeNekId) {
		this.prodTypeNekId = prodTypeNekId;
	}

	/**
	 * 获取来源类型
	 * 
	 * @return 来源类型
	 */
	public Integer getProdTypeSouId() {
		return this.prodTypeSouId;
	}

	/**
	 * 设置来源类型
	 * 
	 * @param prodTypeSouId
	 *            来源类型
	 */
	public void setProdTypeSouId(Integer prodTypeSouId) {
		this.prodTypeSouId = prodTypeSouId;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getProdCostId() {
		return this.prodCostId;
	}

	/**
	 * 设置
	 * 
	 * @param prodCostId
	 */
	public void setProdCostId(Integer prodCostId) {
		this.prodCostId = prodCostId;
	}

	public ProdTypePhy getProdTypePhy() {
		return prodTypePhy;
	}

	public void setProdTypePhy(ProdTypePhy prodTypePhy) {
		this.prodTypePhy = prodTypePhy;
	}

	public ProdTypeWid getProdTypeWid() {
		return prodTypeWid;
	}

	public void setProdTypeWid(ProdTypeWid prodTypeWid) {
		this.prodTypeWid = prodTypeWid;
	}

	public ProdTypeSou getProdTypeSou() {
		return prodTypeSou;
	}

	public void setProdTypeSou(ProdTypeSou prodTypeSou) {
		this.prodTypeSou = prodTypeSou;
	}

	public ProdTypeCon getProdTypeCon() {
		return prodTypeCon;
	}

	public void setProdTypeCon(ProdTypeCon prodTypeCon) {
		this.prodTypeCon = prodTypeCon;
	}

	public ProdTypeHig getProdTypeHig() {
		return prodTypeHig;
	}

	public void setProdTypeHig(ProdTypeHig prodTypeHig) {
		this.prodTypeHig = prodTypeHig;
	}

	public ProdTypeNek getProdTypeNek() {
		return prodTypeNek;
	}

	public void setProdTypeNek(ProdTypeNek prodTypeNek) {
		this.prodTypeNek = prodTypeNek;
	}

	public ProdTypeAre getProdTypeAre() {
		return prodTypeAre;
	}

	public void setProdTypeAre(ProdTypeAre prodTypeAre) {
		this.prodTypeAre = prodTypeAre;
	}

	public ProdTypeBiz getProdTypeBiz() {
		return prodTypeBiz;
	}

	public void setProdTypeBiz(ProdTypeBiz prodTypeBiz) {
		this.prodTypeBiz = prodTypeBiz;
	}

	public ProdTypePic getProdTypePic() {
		return prodTypePic;
	}

	public void setProdTypePic(ProdTypePic prodTypePic) {
		this.prodTypePic = prodTypePic;
	}

	public ProdTypeJar getProdTypeJar() {
		return prodTypeJar;
	}

	public void setProdTypeJar(ProdTypeJar prodTypeJar) {
		this.prodTypeJar = prodTypeJar;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public ProdCost getProdCost() {
		return prodCost;
	}

	public void setProdCost(ProdCost prodCost) {
		this.prodCost = prodCost;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getLastUpdateAdminId() {
		return lastUpdateAdminId;
	}

	public void setLastUpdateAdminId(Integer lastUpdateAdminId) {
		this.lastUpdateAdminId = lastUpdateAdminId;
	}

	public List<ProdPic> getProdPicList() {
		return prodPicList;
	}

	public void setProdPicList(List<ProdPic> prodPicList) {
		this.prodPicList = prodPicList;
	}

	public ProdPic getMajorPic() {
		return majorPic;
	}

	public void setMajorPic(ProdPic majorPic) {
		this.majorPic = majorPic;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getExtCnLayout() {
		String extCnLayout = "";
		if(this.getProdTypePicId()!=null&&this.getProdTypePicId()!=4) {
			if (!this.getNameCn().isEmpty()) {
				String area = this.getNameCn().substring(0, this.getNameCn().length() > 1 ? 1 : this.getNameCn().length());
				String contory = this.getNameCn().substring(1,
						this.getNameCn().length() > 3 ? 3 : this.getNameCn().length());
				// System.out.println("-----------------------------------------");
				// System.out.println("this contory ======="+contory);
				// System.out.println("-----------------------------------------");
				String type = "";
				String con = "";
				if (this.getNameCn().length() > 3) {
					type = this.getNameCn().substring(3, this.getNameCn().length() > 4 ? 4 : this.getNameCn().length());
				}
				if (this.getNameCn().length() > 4) {
					con = this.getNameCn().substring(4, this.getNameCn().length() > 6 ? 6 : this.getNameCn().length());
				}

				switch (area) {
				case "1":
					extCnLayout += "亚洲";
					switch (contory) {
					case "01":
						extCnLayout += "中国";
						break;
					case "02":
						extCnLayout += "日本";
						break;
					case "03":
						extCnLayout += "韩国";
						break;
					case "04":
						extCnLayout += "其他国家";
						break;
					case "05":
						extCnLayout += "印度";
						break;
					default:

						break;
					}

					break;
				case "2":
					extCnLayout += "欧洲";
					switch (contory) {
					case "01":
						extCnLayout += "英国";
						break;
					case "02":
						extCnLayout += "德国";
						break;
					case "03":
						extCnLayout += "法国";
						break;
					case "04":
						extCnLayout += "其他国家";
						break;
					default:

						break;
					}

					break;
				case "3":
					extCnLayout += "美洲";
					switch (contory) {
					case "01":
						extCnLayout += "美国";
						break;
					case "02":
						extCnLayout += "加拿大";
						break;
					case "03":
						extCnLayout += "南美";
						break;

					default:

						break;
					}

					break;
				case "4":
					extCnLayout += "大洋洲";
					switch (contory) {
					case "01":
						extCnLayout += "澳洲";
						break;
					case "02":
						extCnLayout += "新西兰";
						break;
					default:

						break;
					}

					break;
				case "5":
					extCnLayout += "非洲";
					switch (contory) {
					case "500":
						extCnLayout += "非洲";
						break;
					default:

						break;
					}

					break;
				default:

					break;
				}

				switch (type) {
				case "C":
					extCnLayout += "日化品";
					break;
				case "D":
					extCnLayout += "饮料";
					break;
				case "F":
					extCnLayout += "调味品";
					break;
				case "M":
					extCnLayout += "药品";
					break;
				case "S":
					extCnLayout += "休闲食品";
					break;
				default:

					break;
				}

				switch (con) {
				case "01":
					extCnLayout += "200ml以下";
					break;
				case "02":
					extCnLayout += "200-500ml";
					break;
				case "03":
					extCnLayout += "500-1000ml";
					break;
				case "04":
					extCnLayout += "1000-3000ml";
					break;
				case "05":
					extCnLayout += "3000ml以上";
					break;
				default:

					break;
				}

			}

		}
		
		return extCnLayout;
	}

	public String getExtEnLayout() {
		String extEnLayout = "";
		if(this.getProdTypePicId()!=null&&this.getProdTypePicId()!=4) {
			if (!this.getNameEn().isEmpty()) {
				String area = this.getNameEn().substring(0, this.getNameEn().length() > 1 ? 1 : this.getNameEn().length());
				String contory = this.getNameEn().substring(1,this.getNameEn().length() > 3 ? 3 : this.getNameEn().length());
				String type = "";
				String con = "";
				if (this.getNameEn().length() > 3) {
					type = this.getNameEn().substring(3, this.getNameEn().length() > 4 ? 4 : this.getNameEn().length());
				}
				if (this.getNameEn().length() > 4) {
					con = this.getNameEn().substring(4, this.getNameEn().length() > 6 ? 6 : this.getNameEn().length());
				}

				switch (area) {
				case "1":
					extEnLayout += "Asia";
					switch (contory) {
					case "01":
						extEnLayout += "China";
						break;
					case "02":
						extEnLayout += "Japan";
						break;
					case "03":
						extEnLayout += "Korea";
						break;
					case "04":
						extEnLayout += "Other Contory";
						break;
					case "05":
						extEnLayout += "India";
						break;
					default:

						break;
					}

					break;
				case "2":
					extEnLayout += "Europe";
					switch (contory) {
					case "01":
						extEnLayout += "England";
						break;
					case "02":
						extEnLayout += "Germany";
						break;
					case "03":
						extEnLayout += "France";
						break;
					case "04":
						extEnLayout += "Other Contory";
						break;
					default:

						break;
					}

					break;
				case "3":
					extEnLayout += "America";
					switch (contory) {
					case "01":
						extEnLayout += "U.S.A";
						break;
					case "02":
						extEnLayout += "Canada";
						break;
					case "03":
						extEnLayout += "South America";
						break;

					default:

						break;
					}

					break;
				case "4":
					extEnLayout += "Oceania";
					switch (contory) {
					case "01":
						extEnLayout += "Australia";
						break;
					case "02":
						extEnLayout += "New Zealand";
						break;
					default:

						break;
					}

					break;
				case "5":
					extEnLayout += "Africa";
					switch (contory) {
					case "500":
						extEnLayout += "Africa";
						break;
					default:

						break;
					}

					break;
				default:

					break;
				}

				switch (type) {
				case "C":
					extEnLayout += "Daily chemicals";
					break;
				case "D":
					extEnLayout += "Drinks";
					break;
				case "F":
					extEnLayout += "Condiment";
					break;
				case "M":
					extEnLayout += "Drugs";
					break;
				case "S":
					extEnLayout += "Leisure Food";
					break;
				default:

					break;
				}

				switch (con) {
				case "01":
					extEnLayout += "Less 200ml";
					break;
				case "02":
					extEnLayout += "200-500ml";
					break;
				case "03":
					extEnLayout += "500-1000ml";
					break;
				case "04":
					extEnLayout += "1000-3000ml";
					break;
				case "05":
					extEnLayout += "greater 3000ml";
					break;
				default:

					break;
				}

			}
		}
		

		return extEnLayout;
	}

}