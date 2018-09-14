package com.fims.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fims.system.domain.RoleDO;

@Service
public interface RoleService {

	RoleDO get(Long id);

	List<RoleDO> list();

	int save(RoleDO role);

	int update(RoleDO role);

	int remove(Long id);

	List<RoleDO> list(Long userId);

	int batchremove(Long[] ids);

	boolean exitOne(Map<String, Object> params);

	boolean exit(Map<String, Object> params);
}
