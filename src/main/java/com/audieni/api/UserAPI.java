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

    // user info accepted using Postman's Authorization,
    // sent through header encrypted in Base64
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
        String sessionId = ctx.cookie("session_id");
        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            user.setSessionId("-1");
            userService.updateUser(user);
            ctx.removeCookie("session_id");
            ctx.result("You have logged out.");
            ctx.status(200);
        } else {
            ctx.result("You never logged in.");
            ctx.status(400);
        }
    }

    // user info accepted using Postman's Authorization,
    // sent through header encrypted in Base64
    public static void register(Context ctx) throws ExistingUserException {
        String sessionId = ctx.cookie("session_id");
        if (sessionId == null) {
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
        } else {
            ctx.result("You are already logged in.");
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
