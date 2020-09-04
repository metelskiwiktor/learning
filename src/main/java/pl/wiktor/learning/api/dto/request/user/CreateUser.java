package pl.wiktor.learning.api.dto.request.user;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class CreateUser {
    @Email(message = "Nieprawidłowy adres email")
    private String email;
    @Size(min = 5, message = "Hasło musi mieć co najmniej 5 znaków")
    private String password;
    @Size(min = 7, message = "Nazwa musi mieć co najmniej 7 znaków")
    private String name;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
