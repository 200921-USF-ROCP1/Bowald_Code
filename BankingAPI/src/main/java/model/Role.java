package model;

public class Role {
	private int roleId; // primary key

	private String role; //{Admin, Employee, Standard, Premium}  // not null, unique : Admin, Employee, Standard, or Premium

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		if(role==null) {
			switch(roleId) {
			case 0:
				role = "Admin";
				break;
			case 1:
				role = "Employee";
				break;
			case 2:
				role = "Standard";
			case 3:
				role = "Premium";
				break;
			}
		}
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Role setRoleId(String r) {
		// TODO Auto-generated method stub
		switch(r) {
		case "Admin":
			roleId = 0;
			role = "Admin";
			break;
		case "Employee":
			roleId = 1;
			role = "Employee";
			break;
		case "Standard":
			roleId = 2;
			role = "Standard";
		case "Premium":
			roleId = 3;
			role = "Premium";
			break;
		}
		return this;
	}

}