package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;

public interface CollectionsService {
    List<Portfolio> getByUserId(int id);
}
