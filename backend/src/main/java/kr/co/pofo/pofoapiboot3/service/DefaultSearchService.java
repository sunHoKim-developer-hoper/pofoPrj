package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.entity.Search;
import kr.co.pofo.pofoapiboot3.repository.SearchRepository;

@Service
public class DefaultSearchService implements SearchService{
	
	@Autowired
	SearchRepository repository;
	
	public List<Search> getSearchList(Integer id){
		List<Search> list = repository.findSearchList(id);
 		return list;
	}

	@Override
	public int deleteHistory(Search search) {
		return repository.delete(search);
	}

	public int add(Search search) {
		return repository.insert(search);
	}
}
