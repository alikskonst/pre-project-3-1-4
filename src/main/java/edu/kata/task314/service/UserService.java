package edu.kata.task314.service;

import edu.kata.task314.entity.User;

public interface UserService extends CommonService<User> {

    User findOne(String login);

    boolean isExistByLogin(String login);
}
