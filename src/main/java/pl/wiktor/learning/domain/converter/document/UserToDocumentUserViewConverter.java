package pl.wiktor.learning.domain.converter.document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.document.UserView;
import pl.wiktor.learning.domain.user.User;

@Component
public class UserToDocumentUserViewConverter implements Converter<User, UserView> {
    @Override
    public UserView convert(User user) {
        return new UserView(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
