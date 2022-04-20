package ua.martishyn.app.controller.commands;

import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.ViewPath;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {    }
}