package ru.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.graduation.AuthorizedUser;
import ru.graduation.model.User;
import ru.graduation.repository.UserRepository;
import ru.graduation.to.UserTo;
import ru.graduation.util.UserUtil;

import java.util.List;

import static ru.graduation.repository.UserRepository.SORT_NAME_EMAIL;
import static ru.graduation.util.UserUtil.prepareToSave;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        repository.save(prepareToSave(UserUtil.updateFromTo(user, userTo), passwordEncoder));
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return repository.getByEmail(email);
    }

    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
