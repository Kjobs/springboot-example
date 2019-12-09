package com.springboot.mybatis.service;

import com.springboot.mybatis.api.ServerInternalException;
import com.springboot.mybatis.mapper.ModelMapper;
import com.springboot.mybatis.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kobs
 * @date 2019/12/9
 */
@Service
public class ModelService {

    @Autowired
    private ModelMapper mapper;

    public TestModel getOne(Integer id) {
        TestModel model = mapper.findByModelId(id);
        if (model == null) {
            throw new ServerInternalException("查询错误，没有找到实体id");
        }
        return model;
    }

    public List<TestModel> getList() {
        return mapper.findAll();
    }

    public void addModel(TestModel model) {
        try {
            mapper.addModel(model);
        } catch (RuntimeException e) {
            throw new ServerInternalException("添加实体出现错误，请重试");
        }
    }

    public void updateById(Integer id, String name) {
        try {
            mapper.updateModel(id, name);
        } catch (RuntimeException e) {
            throw new ServerInternalException("更新实体出现错误，请重试");
        }
    }

    public void deleteOne(String name) {
        try {
            mapper.deleteByName(name);
        } catch (RuntimeException e) {
            throw new ServerInternalException("删除实体失败");
        }
    }
}
