package common.core.tag;

import java.util.List;

import common.core.tag.entity.Resources;


/**
 * desc:   后台系统权限管理service，包括账号，角色，资源等管理
 */
public interface ActPermissionService {
	/**
     * 根据用户查询权限
     */
	public List<Resources> queryResourcesByUserno(String userNo);
	/**
	 * 根椐菜单CODE查询递归 父菜单
	 */
	public List<Resources> queryResourcesStartWithByCODE(String code);
}
