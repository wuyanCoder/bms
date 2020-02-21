package com.wuyan.bms.model;


public class LocalStorage extends BaseEntity<Integer> {

	private String realName;
	private String name;
	private String suffix;
	private String path;
	private String type;
	private String size;
	private String createBy;
	private String updateBy;
	private String deleteBy;

	public LocalStorage(){}

	public LocalStorage(String realName, String name, String suffix, String path, String type, String size) {
		this.realName = realName;
		this.name = name;
		this.suffix = suffix;
		this.path = path;
		this.type = type;
		this.size = size;
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getDeleteBy() {
		return deleteBy;
	}
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

}
