package com.wuyan.bms.service;

import com.wuyan.bms.model.LocalStorage;
import com.wuyan.bms.common.result.Results;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface LocalStorageService {

   Results<LocalStorage>  getByPage(Map<String, Object> params,Integer offset, Integer limit);

   Results  save(LocalStorage localStorage);

   Results  getById(Integer id);

   Results update(LocalStorage localStorage);

   Results  delete(LocalStorage localStorage);

   LocalStorage getLocalStorageById(Integer id);

   Results upload(String name, MultipartFile file);
}

