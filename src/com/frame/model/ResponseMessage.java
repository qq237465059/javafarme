package com.frame.model;

public class ResponseMessage {
	private String msg;
	private boolean error;
	private Object data;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean getError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * ÃÌº” «∑Ò±®¥Ì
	 * 
	 * @param msg
	 * @param error
	 * @return
	 */
	public static ResponseMessage isError(String msg,boolean error){
		ResponseMessage rs = new ResponseMessage();
		rs.setError(error);
		rs.setMsg(msg);
		return rs;
	}
	
	public static ResponseMessage isError(String msg,boolean error,Object data){
		ResponseMessage rs = new ResponseMessage();
		rs.setError(error);
		rs.setMsg(msg);
		rs.setData(data);
		return rs;
	}
	
	public static boolean hasError(ResponseMessage rs){
		if(rs.getError()){
			return true;
		}else{
			return false;
		}
	}
}
