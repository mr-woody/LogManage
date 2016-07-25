package lms.code.beans;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity(name = "LMS_Roles")
public class LMS_Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "S_LMS_Roles")
	@SequenceGenerator(name = "S_LMS_Roles", allocationSize = 1, initialValue = 1, sequenceName = "S_LMS_Roles")
	private long roleID;
	private String name;
	private String authors;
	
	//[start] 导航属性
	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	private Set<LMS_Staffs> staffs = new HashSet<LMS_Staffs>();
	//[end]
	
	//[start] get and set methods
	public long getRoleID() {
		return roleID;
	}

	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Set<LMS_Staffs> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<LMS_Staffs> staffs) {
		this.staffs = staffs;
	}
	//[end]
}
