<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực Mã OTP | HCMUTE JPA</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>
        body { background-color: #f1f5f9; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .auth-card { max-width: 460px; margin: 60px auto; border-radius: 16px; border: none; box-shadow: 0 10px 25px rgba(0,0,0,0.08); overflow: hidden; }
        .auth-header { background: linear-gradient(135deg, #059669 0%, #10b981 100%); color: white; padding: 30px 20px; text-align: center; }
        .otp-input { letter-spacing: 8px; font-size: 1.6rem; text-align: center; font-weight: 700; }
    </style>
</head>
<body>

    <jsp:include page="/views/common/navbar.jsp" />

    <div class="container">
        <div class="card auth-card">
            <div class="auth-header">
                <i class="fa-solid fa-envelope-circle-check fa-2x mb-2"></i>
                <h4 class="fw-bold mb-0">KÍCH HOẠT TÀI KHOẢN</h4>
                <p class="small text-white-50 mb-0">Nhập mã xác thực 6 chữ số gửi đến Email</p>
            </div>
            <div class="card-body p-4">
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger py-2 small d-flex align-items-center mb-3" role="alert">
                        <i class="fa-solid fa-circle-exclamation me-2"></i>
                        <div>${errorMessage}</div>
                    </div>
                </c:if>
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success py-2 small d-flex align-items-center mb-3" role="alert">
                        <i class="fa-solid fa-circle-check me-2"></i>
                        <div>${successMessage}</div>
                    </div>
                </c:if>

                <p class="text-muted small text-center mb-4">
                    Mã kích hoạt OTP đã được gửi đến email: <br>
                    <strong class="text-primary">${email}</strong>
                </p>

                <form action="<c:url value='/verify-otp'/>" method="post">
                    <input type="hidden" name="email" value="${email}">

                    <div class="mb-4">
                        <label for="otp" class="form-label fw-semibold text-center w-100">MÃ XÁC THỰC (OTP)</label>
                        <input type="text" class="form-control otp-input form-control-lg" id="otp" name="otp" maxlength="6" placeholder="000000" required autofocus>
                        <div class="form-text text-center mt-2">Mã gồm 6 chữ số có hiệu lực trong vòng 5 phút</div>
                    </div>

                    <button type="submit" class="btn btn-success w-100 py-2 fw-bold shadow-sm">
                        <i class="fa-solid fa-check me-1"></i>Kích Hoạt Tài Khoản
                    </button>
                </form>

                <div class="text-center mt-4 pt-3 border-top">
                    <p class="small text-muted mb-0">Chưa nhận được mã? 
                        <a href="<c:url value='/verify-otp?action=resend&email=${email}'/>" class="fw-bold text-success text-decoration-none">
                            <i class="fa-solid fa-rotate-right me-1"></i>Gửi lại mã OTP
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/views/common/footer.jsp" />

</body>
</html>
