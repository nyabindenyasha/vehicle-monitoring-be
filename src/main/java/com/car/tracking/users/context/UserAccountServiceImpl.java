package com.car.tracking.users.context;

import com.car.tracking.commons.exceptions.InvalidRequestException;
import com.car.tracking.commons.exceptions.ItemNotFoundException;
import com.car.tracking.commons.jpa.BaseServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, UserAccountCreateContext, UserAccountUpdateContext> implements UserAccountService {

    private final UserAccountRepo userAccountRepo;

    public UserAccountServiceImpl(UserAccountRepo userAccountRepo) {
        super(userAccountRepo);
        this.userAccountRepo = userAccountRepo;
    }

    @Override
    protected Class<UserAccount> getEntityClass() {
        return UserAccount.class;
    }

    @Override
    public UserAccount create(UserAccountCreateContext request) {

        boolean detailsExists = userAccountRepo.existsByUsername(request.getUsername());

        if (detailsExists) {
            throw new InvalidRequestException("User Account with the same username already exists");
        }

        UserAccount userAccount = UserAccount.fromCommand(request);

        return userAccountRepo.save(userAccount);

    }


    @Override
    public UserAccount update(UserAccountUpdateContext request) {

        UserAccount userAccount = findById(request.getId());

        userAccount.update(request);

        return userAccountRepo.save(userAccount);
    }

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userAccountRepo.existsByUsername(username);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepo.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("UserAccount record not found!"));
    }

    @Override
    public UserAccount getByUsername(String username) {
        return userAccountRepo.getByUsername(username);
    }

    @Override
    public UserAccount login(LoginRequest loginRequest) {

        val exists = existsByUsername(loginRequest.getUsername());

        if(!exists)
            throw new InvalidRequestException("Incorrect username or password.");

        val userAccount = findByUsername(loginRequest.getUsername());

        if (userAccount.getPassword().equals(loginRequest.getPassword())) {
            userAccount.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
            return userAccount;
        }

        else
            throw new InvalidRequestException("Incorrect username or password.");
    }

}
