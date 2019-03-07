package com.kollaboralabs.auth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kollaboralabs.auth.config.AuthConstant;
import com.kollaboralabs.auth.config.ResponseMessage;
import com.kollaboralabs.auth.domain.UserRequestData;
import com.kollaboralabs.auth.domain.UserResponseData;
import com.kollaboralabs.auth.entity.User;
import com.kollaboralabs.auth.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Find all users 
     * @return
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "findAllUsers", notes = "Retrieve all users information detail. It is configurable by page, size, and sort requirement. ")
    @ApiImplicitParams({
        @ApiImplicitParam(
            name = AuthConstant.API_KEY,
            value = AuthConstant.API_KEY_DESC,
            required = true,
            dataType = "string",
            paramType = "header"
        ),
        @ApiImplicitParam(
            name = AuthConstant.ACCEPT_LANGUAGE,
            value = AuthConstant.ACCEPT_LANGUAGE_DESC,
            required = false,
            dataType = "string",
            paramType = "header",
            defaultValue = "en"
        )
    })
    public List<User> findAllUsers() {
        List<User> users = this.userService.findAllUsers();
        return users;
    }

    /**
     * Find user by login
     * @param login
     * @return
     */
    @RequestMapping(
        value = "/{login:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "findUserByLogin", notes = "Retrieve user detail information")
    @ApiImplicitParams({
        @ApiImplicitParam(
            name = AuthConstant.API_KEY,
            value = AuthConstant.API_KEY_DESC,
            required = true,
            dataType = "string",
            paramType = "header"
        ),
        @ApiImplicitParam(
            name = AuthConstant.ACCEPT_LANGUAGE,
            value = AuthConstant.ACCEPT_LANGUAGE_DESC,
            required = false,
            dataType = "string",
            paramType = "header",
            defaultValue = "en"
        )
    })
    public ResponseEntity<UserResponseData> findUserByLogin(
            @ApiParam(value = AuthConstant.LOGIN_DESC, required = true)
            @PathVariable(AuthConstant.LOGIN) String login) {
        User user = this.userService.findUserByLogin(login);
        if (user != null) {
            UserResponseData userResponseData = this.userService.mapToUserResponseData(user);
            return new ResponseEntity<>(userResponseData, HttpStatus.OK);
        	
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create user
     * @param request
     * @param userRequestData
     * @return
     */
    @RequestMapping(
        value = "/",
        method = RequestMethod.POST
    )
    @ApiOperation(value = "createUser", notes = "Create new user in Mednet user table.")
    @ApiImplicitParams({
        @ApiImplicitParam(
            name = AuthConstant.API_KEY,
            value = AuthConstant.API_KEY_DESC,
            required = true,
            dataType = "string",
            paramType = "header"
        ),
        @ApiImplicitParam(
            name = AuthConstant.ACCEPT_LANGUAGE,
            value = AuthConstant.ACCEPT_LANGUAGE_DESC,
            required = false,
            dataType = "string",
            paramType = "header",
            defaultValue = "en"
        )
    })
    public ResponseEntity<?> createUserEndPoint(
            HttpServletRequest request,
            @Validated @RequestBody UserRequestData userRequestData) {
        User user = this.userService.createUser(userRequestData); // Login cannot be null or empty
        UserResponseData userResponseData = this.userService.mapToUserResponseData(user);
        return new ResponseEntity<>(userResponseData, HttpStatus.CREATED);
    }

    /**
     * Find user by email
     * @param email
     * @return
     */
    @RequestMapping(
        value = "/email/{email:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "findUserByEmailIgnoreCase", notes = "Find user by email (case insensitive)")
    @ApiImplicitParams({
        @ApiImplicitParam(
            name = AuthConstant.API_KEY,
            value = AuthConstant.API_KEY_DESC,
            required = true,
            dataType = "string",
            paramType = "header"
        ),
        @ApiImplicitParam(
            name = AuthConstant.ACCEPT_LANGUAGE,
            value = AuthConstant.ACCEPT_LANGUAGE_DESC,
            required = false,
            dataType = "string",
            paramType = "header",
            defaultValue = "en"
        )
    })
    public ResponseEntity<UserResponseData> findUserByEmailIgnoreCase(
                @ApiParam(value = AuthConstant.EMAIL_DESC, required = true)
                @PathVariable(AuthConstant.EMAIL) String email) {
        User user = this.userService.findUserByEmailIgnoreCase(email);
        if (user != null) {
            UserResponseData userResponseData = this.userService.mapToUserResponseData(user);
            return new ResponseEntity<>(userResponseData, HttpStatus.OK);
        	
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete user by login
     * @param request
     * @param login
     * @return
     */
    @RequestMapping(
        value = "/{login:.+}",
        method = RequestMethod.DELETE
    )
    @ApiOperation(value = "deleteUserByLogin", notes = "Delete user by login")
    @ApiImplicitParams({
        @ApiImplicitParam(
            name = AuthConstant.API_KEY,
            value = AuthConstant.API_KEY_DESC,
            required = true,
            dataType = "string",
            paramType = "header"
        ),
        @ApiImplicitParam(
            name = AuthConstant.ACCEPT_LANGUAGE,
            value = AuthConstant.ACCEPT_LANGUAGE_DESC,
            required = false,
            dataType = "string",
            paramType = "header",
            defaultValue = "en"
        )
    })
    public ResponseEntity<ResponseMessage> deleteUserByLogin(
            HttpServletRequest request,
            @ApiParam(value = AuthConstant.LOGIN_DESC, required = true)
            @PathVariable(AuthConstant.LOGIN) String login) {
        try {
            this.userService.deleteUserByLogin(login);
        } catch (Exception e) {
            return this.buildResponse(HttpStatus.NOT_FOUND, request);
        }

        return this.buildResponse(HttpStatus.OK, request);
    }

    private ResponseEntity<ResponseMessage> buildResponse(HttpStatus httpStatus, HttpServletRequest request) {
        return new ResponseEntity<>(new ResponseMessage(httpStatus, request.getRequestURI()), httpStatus);
    }
}
