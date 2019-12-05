package com.springboot.aop.controller;

import com.springboot.aop.entity.TestModel;
import com.springboot.aop.log.CustomLogAnnotation;
import com.springboot.aop.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kobs
 * @date 2019/12/4
 */
@RestController
public class DataController {

    @Autowired
    private ModelService modelService;

    @GetMapping(value = "get")
    public TestModel getModel() {
        return modelService.getModel();
    }

    @GetMapping(value = "getList")
    public List<TestModel> getModels() {
        return modelService.getModels();
    }

    @CustomLogAnnotation(index = 0)
    @PostMapping(value = "show")
    public void showModel(TestModel model) {
        modelService.printModel(model);
    }

    @GetMapping(value = "testString")
    public String testString() {
        return "test";
    }
}
