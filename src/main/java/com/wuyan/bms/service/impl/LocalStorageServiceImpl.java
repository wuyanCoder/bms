package com.wuyan.bms.service.impl;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.common.result.ResponseCode;
import com.wuyan.bms.common.utils.FileUtil;
import com.wuyan.bms.common.utils.SecurityUtils;
import com.wuyan.bms.mapper.LocalStorageDao;
import com.wuyan.bms.model.LocalStorage;
import com.wuyan.bms.service.LocalStorageService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class  LocalStorageServiceImpl implements LocalStorageService {

    @Autowired
    private LocalStorageDao localStorageDao;
    @Value("${file-path}")
    private String filePath;

    @Override
    public Results<LocalStorage> getByPage(Map<String, Object> params,Integer offset, Integer limit) {
        return new Results(0,"",null,localStorageDao.count(params), localStorageDao.list(params,offset, limit));

    }

    @Override
    public Results save(LocalStorage localStorage) {
        int res = 0;
        if(StringUtils.isEmpty(localStorage.getId())){
            res =  localStorageDao.save(localStorage);
        }else{
            res =  localStorageDao.update(localStorage);
        }
        Results results = new Results();
        results.setCode(ResponseCode.SUCCESS.getCode());
        results.setMsg(ResponseCode.SUCCESS.getMessage());
        return results;
    }

    @Override
    public Results getById(Integer id) {
        LocalStorage res =  localStorageDao.getById(id);
        Results results = new Results();
        results.setCode(ResponseCode.SUCCESS.getCode());
        results.setMsg(ResponseCode.SUCCESS.getMessage());
        return results;
    }

    @Override
    public LocalStorage getLocalStorageById(Integer id) {
        return localStorageDao.getById(id);
    }

    @Override
    public Results upload(String name, MultipartFile multipartFile) {

        //检查大小
        //获取后缀
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, filePath + type + File.separator);
        if (Objects.isNull(file)) {
            return Results.failure(ResponseCode.FILE_UPLOAD_FAILURE);
        }
        //防止异常出错
        try {
            name = org.apache.commons.lang3.StringUtils.isBlank(name) ? FileUtil.getFileNameNoExtension(multipartFile.getOriginalFilename()) : name;
            LocalStorage localStorage = new LocalStorage(file.getName(), name, suffix, file.getPath(), type, FileUtil.getSizeString(multipartFile.getSize()));
            localStorage.setCreateBy(SecurityUtils.getUsername());
            localStorageDao.save(localStorage);
        } catch (Exception e) {
            FileUtil.del(file);
            throw e;
        }
        return Results.success();
    }

    @Override
    @Transactional
    public Results update(LocalStorage localStorage) {
        int res =  localStorageDao.update(localStorage);
        Results results = new Results();
        results.setCode(ResponseCode.SUCCESS.getCode());
        results.setMsg(ResponseCode.SUCCESS.getMessage());
        return results;
    }

    @Override
    @Transactional
    public Results delete(LocalStorage localStorage) {
        int res =  localStorageDao.delete(localStorage);
        Results results = new Results();
        results.setCode(ResponseCode.SUCCESS.getCode());
        results.setMsg(ResponseCode.SUCCESS.getMessage());
        return results;
    }

}
