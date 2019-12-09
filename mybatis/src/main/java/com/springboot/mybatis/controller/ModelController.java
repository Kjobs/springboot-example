package com.springboot.mybatis.controller;

import com.springboot.mybatis.model.TestModel;
import com.springboot.mybatis.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kobs
 * @date 2019/12/9
 */
@RestController
@RequestMapping(value = "model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping(value = "getOne")
    public TestModel getOne(Integer id) {
        return modelService.getOne(id);
    }

    @GetMapping(value = "getAll")
    public List<TestModel> getList() {
        return modelService.getList();
    }

    @PostMapping(value = "addOne")
    public void addModel(TestModel model){
        modelService.addModel(model);
    }

    @PostMapping(value = "updateName")
    public void updateModel(Integer id, String name) {
        modelService.updateById(id, name);
    }

    @DeleteMapping(value = "deleteByName")
    public void deleteModel(String name) {
        modelService.deleteOne(name);
    }
}
