package com.kims.community.users.repository;

import com.kims.community.users.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    /**
     * 조회 (userId 기준)
     * @param id must not be {@literal null}.
     * @return boolean
     */
    boolean existsById(Long id);

    /**
     * 조회 (이메일 기준)
     * @param email email
     * @return Optional<Users>
     */
    Optional<Users> findByEmail(String email);

    /**
     * 이메일 존재 여부
     * @param email email
     * @return int
     */
    boolean existsByEmail(String email);

    /**
     * 닉네임 존재 여부
     * @param nickName nickName
     * @return int
     */
    boolean existsByNickName(String nickName);

}
