package pl.wiktor.learning.api.dto.response.document;

public class UserView {
    private String id;
    private String name;
    private String email;

    public UserView(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserView() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
