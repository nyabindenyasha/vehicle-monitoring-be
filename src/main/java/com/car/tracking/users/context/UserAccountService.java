package com.car.tracking.users.context;

import com.car.tracking.commons.jpa.BaseService;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

public interface UserAccountService extends BaseService<UserAccount, UserAccountCreateContext, UserAccountUpdateContext> {

    boolean existsByUsername(String username);

    UserAccount findByUsername(String username);

    UserAccount getByUsername(String username);

    UserAccount login(LoginRequest loginRequest);

}
