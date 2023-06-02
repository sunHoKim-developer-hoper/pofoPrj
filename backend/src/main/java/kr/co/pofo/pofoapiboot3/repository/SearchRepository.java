package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.entity.Search;

@Mapper
public interface SearchRepository {
	List<Portfolio>findAllPortfolios();
	List<Search> findSearchList(Integer id);
	int delete(Search search);
	int insert(Search search);
}
	