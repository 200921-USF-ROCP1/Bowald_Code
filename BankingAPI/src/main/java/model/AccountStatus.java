package model;

public class AccountStatus {
	  private int statusId; // primary key
	  private String status; // {Pending, Open, Closed, Denied}; // not null, unique
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	}