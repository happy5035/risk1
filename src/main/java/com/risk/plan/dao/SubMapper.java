package com.risk.plan.dao;

import com.risk.plan.common.MyBatisRepository;
import com.risk.plan.entity.Sub;
@MyBatisRepository
public interface SubMapper {
    int deleteByPrimaryKey(String subid);

    int insert(Sub record);

    int insertSelective(Sub record);

    Sub selectByPrimaryKey(String subid);

    int updateByPrimaryKeySelective(Sub record);

    int updateByPrimaryKey(Sub record);
}