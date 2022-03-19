package com.example.test.model;

import com.example.test.LoginActivity;
import com.example.test.LoginView;

public class LoginPresenter {

    LoginView loginView;

    public LoginPresenter(LoginActivity loginView) {
        this.loginView = loginView;
    }

    public void login(String username,String password) {
        if (username.isEmpty()) {
            loginView.setErrorPassword();
            return;
        } else if (password.isEmpty()) {
            loginView.setErrorPassword();
            return;
        } else {
            // giả sử tk là admin với mật khẩu là admin nhé :D
            if (username.equalsIgnoreCase("dao")
                    && password.
                    equalsIgnoreCase("123")) {
                loginView.loginSuccessful();
                loginView.navigateHome();
            } else {
                loginView.loginFail();
            }
        }
    }
}
