package com.woowaSisters.woowaSisters.user;

import com.woowaSisters.woowaSisters.user.model.User;
import com.woowaSisters.woowaSisters.util.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.woowaSisters.woowaSisters.util.BaseResponseStatus.*;

@Slf4j
@Service
public class UserProvider {

    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public User retrieveByEmail(String email) throws BaseException {
        if (checkEmail(email) == 0)
            throw new BaseException(USERS_EMPTY_USER_EMAIL);
        try {
            return userDao.selectByEmail(email);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public User retrieveById(Long user_id) throws BaseException {
        if (checkId(user_id) == 0)
            throw new BaseException(USERS_EMPTY_USER_ID);
        try {
            return userDao.selectById(user_id);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkId(Long id) throws BaseException {
        try {
            return userDao.checkId(id);
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
