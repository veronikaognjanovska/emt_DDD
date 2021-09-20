package ddd.usermanagement.service.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserForm {

    @NotNull
    private String username;
    private String password;
    private String repeatPassword;
    @NotNull
    private String email;
    @NotNull
    private String birthday;

    public UserForm(String username, String password, String email, String birthday) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.repeatPassword=null;
    }

}
