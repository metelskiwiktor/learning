package pl.wiktor.learning.domain.user;

import org.springframework.stereotype.Service;
import pl.wiktor.learning.api.dto.response.user.UserView;
import pl.wiktor.learning.api.exception.DomainException;
import pl.wiktor.learning.api.exception.ExceptionCode;
import org.springframework.core.convert.ConversionService;
import pl.wiktor.learning.lib.Assertion;

import static pl.wiktor.learning.api.exception.ExceptionCode.Resource.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ConversionService conversionService;

    public UserService(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    public UserView createUser(String password, String name, String email) {
        User user = new User(
                password,
                email,
                name,
                Role.REGULAR,
                Status.ENABLED
        );

        userRepository.save(user);

        return conversionService.convert(user, UserView.class);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DomainException(ExceptionCode.RESOURCE_NOT_FOUND, USER.getPolish(), id));
    }
}
