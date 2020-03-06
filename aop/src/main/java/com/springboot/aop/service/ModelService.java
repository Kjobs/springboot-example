package com.springboot.aop.service;

import com.springboot.aop.entity.TestModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kobs
 * @date 2019/12/4
 */
@Service
public class ModelService {

    public void printModel(TestModel tm) {
        System.out.format("number:%s%n", tm.getNumber());
        System.out.format("string:%s%n", tm.getString());
    }

    public TestModel getModel() {
        System.out.println("model--" + Thread.currentThread().getName());
        TestModel tm = new TestModel();
        tm.setNumber(123);
        tm.setString("abc");
        return tm;
    }

    public List<TestModel> getModels() {
        List<TestModel> modelList = new ArrayList<>();
        TestModel tm1 = new TestModel(1, "a");
        modelList.add(tm1);
        TestModel tm2 = new TestModel(2,"b");
        modelList.add(tm2);
        return modelList;
    }
}
