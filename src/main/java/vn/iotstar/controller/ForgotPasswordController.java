package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;

/**
 * Controller xử lý chức năng quên mật khẩu và đặt lại mật khẩu với mã xác thực OTP qua email
 * 
 * Môn học: Lập Trình Web (WEBPR330479)
 * Sinh viên: Trương Gia Kiệt - MSSV: 24110032
 */
@WebServlet(urlPatterns = {"/forgot-password", "/reset-password"})
public class ForgotPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/reset-password".equals(path)) {
            String account = req.getParameter("account");
            req.setAttribute("account", account);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();
        if ("/forgot-password".equals(path)) {
            handleForgotPassword(req, resp);
        } else if ("/reset-password".equals(path)) {
            handleResetPassword(req, resp);
        }
    }

    private void handleForgotPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        if (account == null || account.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập Email hoặc Tên đăng nhập!");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            return;
        }

        try {
            userService.forgotPassword(account.trim());
            // Điều hướng sang trang nhập mã OTP và mật khẩu mới
            resp.sendRedirect(req.getContextPath() + "/reset-password?account=" + account.trim() + "&msg=otp_sent");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("account", account);
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
        }
    }

    private void handleResetPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        String otp = req.getParameter("otp");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (otp == null || otp.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ mã OTP và mật khẩu mới!");
            req.setAttribute("account", account);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            req.setAttribute("account", account);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }

        try {
            userService.resetPassword(account.trim(), otp.trim(), newPassword.trim());
            resp.sendRedirect(req.getContextPath() + "/login?msg=reset_success");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("account", account);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
        }
    }
}
