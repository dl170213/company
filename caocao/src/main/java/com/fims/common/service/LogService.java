package com.fims.common.service;

import org.springframework.stereotype.Service;

import com.fims.common.domain.LogDO;
import com.fims.common.domain.PageDO;
import com.fims.common.utils.Query;
@Service
public interface LogService {
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
