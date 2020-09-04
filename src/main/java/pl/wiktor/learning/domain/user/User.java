package pl.wiktor.learning.domain.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String password;
    private String email;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Status status;

    public User(String password, String email, String name, Role role, Status status) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
