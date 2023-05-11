package rikkei.academy.controller;

import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;
import rikkei.academy.service.role.IRoleService;
import rikkei.academy.service.role.RoleServiceIMPL;
import rikkei.academy.service.user.IUserService;
import rikkei.academy.service.user.UserServiceIMPL;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/user")
public class UserController extends HttpServlet {
private IRoleService roleService = new RoleServiceIMPL();
private IUserService userService = new UserServiceIMPL();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "register":
                showFormRegister(request,response);
                break;
            case "login":
                showFormLogin(request, response);
                break;
            case "logout":
                    logOut(request, response);
                break;
            case "avatar":
                showFormChangeAvatar(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if(action==null){
            action = "";
        }
        switch (action){
            case "register":
                actionRegister(request, response);
                break;
            case "login":
                actionLogin(request, response);
                break;
            case "avatar":
                actionUpdateAvatar(request, response);
                break;
        }
    }
    private void showFormRegister(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/form-login/register.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void actionRegister(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = "admin";
        String role2= "pm";
        Set<String> strRole = new HashSet<>();
        strRole.add(role);
        strRole.add(role2);
        Set<Role> roleSet = new HashSet<>();
        strRole.forEach(role1 ->{
            switch (role1){
                case "admin":
                    roleSet.add(roleService.findByName(RoleName.ADMIN));
                    break;
                case "pm":
                    roleSet.add(roleService.findByName(RoleName.PM));
                    break;
                default:
                    roleSet.add(roleService.findByName(RoleName.USER));
            }
        });
        if(userService.existedByUsername(username)){
            request.setAttribute("validate", "The username is existed!");
            request.setAttribute("name", name);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            showFormRegister(request,response);
        } else if (userService.existedByEmail(email)){
            request.setAttribute("validate", "The email is existed!");
            request.setAttribute("name", name);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            showFormRegister(request,response);

        } else {
            User user = new User(name, username, email,password,roleSet);
            userService.save(user);
            request.setAttribute("validate", "Register success !");
            try {
                response.sendRedirect("/user?action=login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // Xây dựng chức năng Login
    private void showFormLogin(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/form-login/login.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void actionLogin(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.userLogin(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            try {
                response.sendRedirect("index.jsp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            request.setAttribute("validate", "Login failed ! Please check your account !");
            showFormLogin(request, response);
        }
    }
    // LOG OUT
    private void logOut(HttpServletRequest request, HttpServletResponse response){
        // false check session cos ton tai hay ko ? neu ton tai tra ve obj tuong ung, ko thi tra ve null
        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
            // Xóa các thuộc tính been trong session
            session.invalidate();
            try {
                response.sendRedirect("index.jsp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // Chức năng change avatar
    private void showFormChangeAvatar(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/upload/upload-avatar.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void actionUpdateAvatar(HttpServletRequest request, HttpServletResponse response){
        String avatar = request.getParameter("avatar");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        userService.updateAvatar(avatar, user.getId());
        user.setAvatar(avatar);
        try {
            response.sendRedirect("index.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}