package rikkei.academy.service.user;

import rikkei.academy.model.Role;
import rikkei.academy.model.User;

import java.util.Set;

public interface IUserService {
    boolean existedByUsername(String username);
    boolean existedByEmail(String email);
    void save(User user);
    User userLogin(String username, String password);
    Set<Role> findRoleByUserId(int user_id);
    void updateAvatar(String avatar, int id);
}
