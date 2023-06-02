package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Notification;

@Mapper
public interface NotificationRepository {
    void insert(Notification notification);

    void delete(Notification notification);

    List<Notification> findById(int id);

    void insertFromProfile(int fromMemberId, int toMemberId, int type);

    void updateFlag(int id);

    void deleteById(int id);
    // 팀 신청 확인
    Notification existsBy(Notification notification);

    void updateAcceptFlag(int id);

    void updateRejectFlag(int id);
}
