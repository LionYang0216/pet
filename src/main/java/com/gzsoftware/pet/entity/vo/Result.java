package com.gzsoftware.pet.entity.vo;
/**
 * for all json response standard format
 * @author pango leung
 *
 */
public class Result {
	

	public static int RESULT_FLAG_SUCCESS = 1;
	public static int RESULT_FLAG_FAIL = 0 ;
	public static String RESULT_MSG_SUCCESS = "success";
	public static String RESULT_MSG_FAIL = "fail";

	private Integer flag;
	private String msg;
	private Object data;
	public Integer recordsTotal;
	public Integer recordsFiltered;
	
	/**
	 * 只传入flag 返回 flag 和 msg
	 * @param flag
	 */
	public Result(Integer flag){
		this.flag = flag;
		if((flag == RESULT_FLAG_SUCCESS)){
			this.msg = RESULT_MSG_SUCCESS;
		}
		if((flag == RESULT_FLAG_FAIL)){
			this.msg = RESULT_MSG_FAIL;
		}
	}
	
	/**
	 * 只传入flag data 返回 flag 和 msg 和 data
	 * @param flag
	 */
	public Result(Integer flag,Object data){
		this.flag = flag;
		this.data = data;
		if((flag == RESULT_FLAG_SUCCESS)){
			this.msg = RESULT_MSG_SUCCESS;
		}
		if((flag == RESULT_FLAG_FAIL)){
			this.msg = RESULT_MSG_FAIL;
		}
	}
	
	/**
	 * 传入flag 和 msg
	 * @param flag
	 * @param msg
	 */
	public Result(Integer flag,String msg){
		this.flag = flag;
		this.msg = msg;
		if((flag == RESULT_FLAG_SUCCESS) && (msg.isEmpty())){
			this.msg = RESULT_MSG_SUCCESS;
		}
		if((flag == RESULT_FLAG_FAIL) && (msg.isEmpty())){
			this.msg = RESULT_MSG_FAIL;
		}
		this.data = null;
	}
	
	/**
	 * 传入 flag msg data
	 * @param flag
	 * @param msg
	 * @param data
	 */
	public Result(Integer flag,String msg,Object data){
		this.flag = flag;
		this.msg = msg;
		this.data = data;
		if((flag == RESULT_FLAG_SUCCESS) && (msg.isEmpty())){
			this.msg = RESULT_MSG_SUCCESS;
		}
		if((flag == RESULT_FLAG_FAIL) && (msg.isEmpty())){
			this.msg = RESULT_MSG_FAIL;
		}
	}
	
	/**
	 * List Result In Page
	 * @param flag
	 * @param msg
	 * @param data
	 * @param recordsTotal
	 * @param recordsFiltered
	 */
	public Result(Integer flag,Object data, Integer recordsTotal, Integer recordsFiltered){
		this.flag = flag;
		this.data = data;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		if((flag == RESULT_FLAG_SUCCESS)){
			this.msg = RESULT_MSG_SUCCESS;
		}
		if((flag == RESULT_FLAG_FAIL)){
			this.msg = RESULT_MSG_FAIL;
		}
	}
	
	/**
	 * List Result In Page with msg
	 * @param flag
	 * @param msg
	 * @param data
	 * @param recordsTotal
	 * @param recordsFiltered
	 */
	public Result(Integer flag,String msg,Object data, Integer recordsTotal, Integer recordsFiltered){
		this.flag = flag;
		this.msg = msg;
		this.data = data;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		if((flag == RESULT_FLAG_SUCCESS) && (msg.isEmpty())){
			this.msg = RESULT_MSG_SUCCESS;
		}
		if((flag == RESULT_FLAG_FAIL) && (msg.isEmpty())){
			this.msg = RESULT_MSG_FAIL;
		}
	}
	
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	
	

}
