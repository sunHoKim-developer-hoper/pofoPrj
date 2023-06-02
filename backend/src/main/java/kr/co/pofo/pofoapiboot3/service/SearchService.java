package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import kr.co.pofo.pofoapiboot3.entity.Search;

public interface SearchService {
	List<Search> getSearchList(Integer id);
	int deleteHistory(Search search);
	int add (Search search);
}
