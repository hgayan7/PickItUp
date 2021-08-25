package com.project.pickItUp.repository;

import java.util.Optional;
import com.project.pickItUp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findById(@Param("id") Long id);

    @Query(
            nativeQuery = true,
            value = "select count(*) from user_table as u inner join organization_user_table as ou on "+
                    "u.id = ou.user_id inner join organization_table as o on o.id = ou.organization_id " +
                    "where u.id = ?2 and o.id = ?1"
    )
    Long getOrganizationMemberCount(Long orgId, Long userId);

}
