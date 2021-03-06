package ddd.usermanagement.service;


import ddd.sharedkernel.domain.events.orders.UserRegistered;
import ddd.sharedkernel.domain.helpers.DateCustom;
import ddd.sharedkernel.infra.DomainEventPublisher;
import ddd.usermanagement.domain.exceptions.InvalidArgumentsException;
import ddd.usermanagement.domain.exceptions.PasswordsDoNotMatchException;
import ddd.usermanagement.domain.exceptions.UsernameAlreadyExistsException;
import ddd.usermanagement.domain.model.Role;
import ddd.usermanagement.domain.model.User;
import ddd.usermanagement.domain.repository.UserRepository;
import ddd.usermanagement.service.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final DomainEventPublisher domainEventPublisher;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(DomainEventPublisher domainEventPublisher) {
        this.domainEventPublisher = domainEventPublisher;
    }

    /**
     * Method that returns the user with the given username
     *
     * @param username
     * @return User - the user with the given username
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    /**
     * Method that saves/registers a new user and returns the same
     * Publishes an event when the user is register - UserRegistered DomainEvent
     *
     * @param dto - object containing the data for saving/registering the new user
     * @return Optional<User>
     */
    @Transactional
    public Optional<User> save(UserForm dto) {
        if (dto.getUsername() == null || dto.getPassword() == null || dto.getRepeatPassword() == null ||
                dto.getEmail() == null || dto.getBirthday() == null)
            throw new InvalidArgumentsException();
        if (this.userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(dto.getUsername());
        }
        if (!dto.getPassword().equals(dto.getRepeatPassword()))
            throw new PasswordsDoNotMatchException();
        User user = new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getEmail(),
                DateCustom.getLocalDateTimeFromDateStringDateDate(dto.getBirthday()), Role.ROLE_USER);
        this.userRepository.save(user);
        UserRegistered u = new UserRegistered(user.getUsername());
        domainEventPublisher.publish(u);
        return Optional.of(this.userRepository.save(user));
    }


    private void setUser(User user) {
        User update = this.userRepository.findByUsername(user.getUsername()).get();
        update.setEmail(user.getEmail());
        update.setPassword(passwordEncoder.encode(user.getPassword()));
        update.setRole(user.getRole());
        update.setBirthday(user.getBirthday());
        userRepository.save(update);
    }


}