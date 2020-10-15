package model;

public class AccountType {
	  private int typeId; // primary key
	  private String type; // {Checking, Savings}; // not null, unique
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	}
