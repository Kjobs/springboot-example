package com.springboot.mybatis.mapper;

import com.springboot.mybatis.model.TestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kobs
 * @date 2019/12/9
 */
@Mapper
@Component
public interface ModelMapper {

    /**
     * 条件查询
     * @return 查询到的实体
     */
    TestModel findByModelId(Integer id);

    /**
     * 无条件查询
     * @return 实体列表
     */
    List<TestModel> findAll();

    /**
     * 添加实体
     */
    void addModel(TestModel model);

    /**
     * 根据id更新名称
     * @param id 判断条件
     * @param name 名称
     */
    void updateModel(Integer id, String name);

    /**
     * 条件删除
     */
    void deleteByName(String name);
}
