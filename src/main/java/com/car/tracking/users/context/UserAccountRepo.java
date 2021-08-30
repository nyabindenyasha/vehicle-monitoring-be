package com.car.tracking.users.context;

import com.car.tracking.commons.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Repository
public interface UserAccountRepo extends BaseRepository<UserAccount> {

    Optional<UserAccount> findByUsername(String username);

    UserAccount getByUsername(String username);

    boolean existsByUsername(String username);

    Optional<UserAccount> findByEmail(String email);

    boolean existsByEmail(String email);

}
