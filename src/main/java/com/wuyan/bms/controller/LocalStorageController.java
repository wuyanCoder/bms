package com.wuyan.bms.controller;

import com.wuyan.bms.common.result.PageTableRequest;
import com.wuyan.bms.model.LocalStorage;
import io.swagger.annotations.ApiOperation;
import com.wuyan.bms.common.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.service.LocalStorageService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
@RequestMapping("/localStorages")
public class LocalStorageController {

    @Autowired
    private LocalStorageService localStorageService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "保存")
    @ResponseBody
    public Results save(LocalStorage localStorage) {
      return  localStorageService.save(localStorage);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public Results get(@PathVariable Integer id) {
        return localStorageService.getById(id);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改")
    public Results  update(LocalStorage localStorage) {
       return localStorageService.update(localStorage);
    }

    @GetMapping("/listPage")
    @ApiOperation(value = "列表")
    @ResponseBody
    public Results<LocalStorage> list(PageTableRequest request, LocalStorage localStorage) {
          request.countOffset();
          Map<String, Object> param = null;
          try {
              param =   StrUtil.convertToMap(localStorage);
          } catch (Exception e) {
              e.printStackTrace();
          }
        return localStorageService.getByPage(param, request.getOffset(), request.getLimit());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除")
    @ResponseBody
    public Results delete(LocalStorage localStorage) {
        return localStorageService.delete( localStorage);
    }

   @ApiOperation(value = "编辑页面", notes = "跳转到菜单信息编辑页面")//描述
   @RequestMapping(value = "/addOrEdit", method = RequestMethod.GET)
    public ModelAndView roleEdit(Model model, HttpServletRequest request, LocalStorage localStorage) {
        LocalStorage newLocalStorage = new LocalStorage();
        if(0 != localStorage.getId()){
            newLocalStorage = localStorageService.getLocalStorageById(localStorage.getId());
        }
        model.addAttribute("localStorage",newLocalStorage);
        ModelAndView modelAndView =  new ModelAndView("file/addLocalStorage");
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件")
    @ResponseBody
    public Results upload(@RequestParam String name, @RequestParam MultipartFile file) {
        return localStorageService.upload(name,file);
    }

}
