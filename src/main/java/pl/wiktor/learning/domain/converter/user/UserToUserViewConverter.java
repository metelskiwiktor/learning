package pl.wiktor.learning.domain.converter.user;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.user.UserView;
import pl.wiktor.learning.domain.user.User;

@Component
public class UserToUserViewConverter implements Converter<User, UserView> {
    @Override
    public UserView convert(User user) {
        return new UserView(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
