package com.audieni.api;

import com.audieni.exceptions.ExistingUserException;
import com.audieni.exceptions.IncorrectPasswordException;
import com.audieni.exceptions.UserNotFoundException;
import com.audieni.models.User;
import com.audieni.models.UserDAO;
import com.audieni.services.UserService;
import io.javalin.http.Context;

import java.util.Base64;

public class UserAPI {
    static UserService userService = new UserService(new UserDAO());


    public static void login(Context ctx) throws UserNotFoundException, IncorrectPasswordException {
        String sessionId = ctx.cookie("session_id");
        if (sessionId == null) {
            String auth = new String(Base64.getDecoder().decode(ctx.header("Authorization").replace("Basic ", "")));
            String email = auth.split(":")[0];
            String password = auth.split(":")[1];
            sessionId = generateSessionId();
            User user = userService.loginUser(email, password);
            user.setSessionId(sessionId);
            userService.updateUser(user);
            ctx.cookie("session_id", sessionId);
            ctx.json(user);
            ctx.status(200);
        } else {
            User user = userService.getUserBySessionId(sessionId);
            ctx.json(user);
            ctx.status(200);
        }
    }

    public static void logout(Context ctx) {
        ctx.removeCookie("session_id");
        ctx.status(200);
    }

    public static void register(Context ctx) throws ExistingUserException {
        String auth = new String(Base64.getDecoder().decode(ctx.header("Authorization").replace("Basic ", "")));
        String email = auth.split(":")[0];
        String password = auth.split(":")[1];

        if(!userService.getUserByEmail(email)) {
            User user = userService.registerUser(email, password);
            ctx.json(user);
            ctx.status(200);
        } else {
            ctx.result("Account with this email already exists.");
            ctx.status(400);
        }
    }

    public static String generateSessionId() {
        final String chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        int n = 10;
        StringBuilder sessionId = new StringBuilder();

        for (int i = 0; i < n; i++) {
            sessionId.append(chars.charAt((int) (chars.length() * Math.random())));
        }

        return sessionId.toString();
    }
}
