package lms.code.service;

import java.util.Collection;

import lms.code.beans.LMS_Roles;

public interface RoleService {
	
	/**
	 * 获取所有角色数据
	 * @return
	 */
	Collection<LMS_Roles> getAllRoles();

	/**
	 * 根据角色id获取一个角色数据
	 * @param roleID 角色id
	 * @return
	 */
	LMS_Roles getOneRole(Long roleID);
	
	/**
	 * 添加一个角色数据
	 * @param roleInfo 角色数据
	 * @return 1：成功 <br/> 0：角色名已存在
	 */
	int addOneRole(LMS_Roles roleInfo);
	
	/**
	 * 删除一个角色数据
	 * @param roleID 角色id
	 * @return
	 */
	boolean deleteOneRole(Long roleID);
	
	/**
	 * 更新一个角色数据
	 * @param roleInfo 角色数据
	 * @return 1：成功 <br/> 0：角色名已存在
	 */
	int updateOneRole(LMS_Roles roleInfo);
}
