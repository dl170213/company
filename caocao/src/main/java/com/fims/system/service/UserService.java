package com.fims.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fims.system.vo.UserVO;
import org.springframework.stereotype.Service;

import com.fims.common.domain.Tree;
import com.fims.system.domain.DeptDO;
import com.fims.system.domain.UserDO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	UserDO get(Long id);

	UserDO getByUsername(String username);

	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserDO user);

	int update(UserDO user);

	int remove(Long userId);

	int batchremove(Long[] userIds);

	int saveUserRole(String id,String roles);

	boolean exit(Map<String, Object> params);

	Set<String> listRoles(Long userId);

	int resetPwd(UserVO userVO,UserDO userDO) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	Tree<DeptDO> getTree();

	/**
	 * 更新个人信息
	 * @param userDO
	 * @return
	 */
	int updatePersonal(UserDO userDO);

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
