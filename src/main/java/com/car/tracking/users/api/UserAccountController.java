package com.car.tracking.users.api;

import com.car.tracking.commons.ResponseMessage;
import com.car.tracking.commons.exceptions.InvalidRequestException;
import com.car.tracking.users.context.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "UserAccount")
@RequestMapping("v1/userAccount")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/login")
    @ApiOperation(tags = "Auth Rest", value = "Login Endpoint")
    public ResponseEntity<?> create(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("create() request: {} ", loginRequest);
        try {
            val user = userAccountService.login(loginRequest);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    @ApiOperation("Get UserAccount")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "username") Pageable pageable,
                                    @RequestParam(required = false) String search) {
        try {
            Page<UserAccount> userAccounts = userAccountService.findAll(pageable, search);
            return new ResponseEntity<>(userAccounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @ApiOperation("Get All UserAccount")
    public ResponseEntity<?> getAll() {
        try {
            Collection<UserAccount> userAccounts = userAccountService.findAll();
            return new ResponseEntity<>(userAccounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a UserAccount by Id")
    public ResponseEntity<?> getUserAccount(@PathVariable long id) {
        try {
            UserAccount userAccount = userAccountService.findById(id);
            return new ResponseEntity<>(userAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByUsername/{username}")
    @ApiOperation("Get a UserAccount by Username")
    public ResponseEntity<?> getUserAccount(@PathVariable String username) {
        try {
            UserAccount userAccount = userAccountService.findByUsername(username);
            return new ResponseEntity<>(userAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a UserAccount by Id")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            userAccountService.delete(id);
            return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("")
    @ApiOperation("Create UserAccount")
    public ResponseEntity<?> create(@Valid @RequestBody UserAccountCreateContext request) {
        try {
            request.setAdmin(true);
            UserAccount userAccountCreated = userAccountService.create(request);
            return new ResponseEntity<>(userAccountCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Update UserAccount")
    public ResponseEntity<?> update(@Valid @RequestBody UserAccountUpdateContext request, @PathVariable long id) {
        try {
            if (request.getId() != id) {
                throw new InvalidRequestException("Record not found!");
            }
            UserAccount userAccountUpdated = userAccountService.update(request);
            return new ResponseEntity<>(userAccountUpdated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
