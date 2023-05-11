package com.kims.community.users.repository;

import com.kims.community.users.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    /**
     * 조회 (이메일 기준)
     * @param email email
     * @return Optional<Users>
     */
    Optional<Users> findByEmail(String email);

    /**
     * 이메일 카운트
     * @param email email
     * @return int
     */
    int countByEmail(String email);

    /**
     * 닉네임 카운트
     * @param nickName nickName
     * @return int
     */
    int countByNickName(String nickName);

}
