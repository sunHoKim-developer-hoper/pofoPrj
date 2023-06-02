package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;

@Mapper
public interface CollectionsRepository {
    List<Portfolio> findByUserId(int id);
    int count(int id);
}
