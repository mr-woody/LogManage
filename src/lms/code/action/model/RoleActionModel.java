package lms.code.action.model;

import java.util.Collection;

public class RoleActionModel {
	
	
	//[start] DeleteOneRole method parameters
	private Long roleID;
	//[end]
	
	//[start] AddOneRole method parameters
	private String roleName;
	private Collection<String> authors;
	//[end]
	
	//[start] get and set methods
	public Long getRoleID() {
		return roleID;
	}
	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Collection<String> getAuthors() {
		return authors;
	}
	public void setAuthors(Collection<String> authors) {
		this.authors = authors;
	}
	//[end]
}
