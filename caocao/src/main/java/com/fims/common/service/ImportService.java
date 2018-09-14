package com.fims.common.service;

import com.fims.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/7/27.
 */
public interface ImportService {

    int importProduct(MultipartFile file);

    R importPO(MultipartFile file,String seachname);

    R importCheck(MultipartFile file,String seachname,String creatid);

    R importSBI(MultipartFile file,String seachname);

    R importSBI1(MultipartFile file,String seachname);

    R importSBI2(MultipartFile file,String seachname);

    R importPOSBI(MultipartFile file,String seachname);

    R importPOBase(MultipartFile file,String seachname);
}
