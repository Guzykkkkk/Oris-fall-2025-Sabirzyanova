<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 10.10.2025
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 10.10.2025
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<style>
    .welcome-container {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        padding: 20px;
        border-radius: 15px;
        color: white;
        margin: 20px 0;
    }
    .welcome-container a {
        color: white;
        text-decoration: none;
        margin: 0 15px;
        padding: 10px 20px;
        background: rgba(255,255,255,0.2);
        border-radius: 25px;
        transition: all 0.3s ease;
        display: inline-block;
    }
    .welcome-container a:hover {
        background: rgba(255,255,255,0.3);
        transform: translateY(-2px);
    }
    .user-info {
        display: flex;
        align-items: center;
        gap: 15px;
        margin-bottom: 15px;
    }
    .avatar-small {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        border: 2px solid white;
        object-fit: cover;
    }
</style>

<div class="welcome-container">
    <c:choose>
        <c:when test="${not empty sessionScope.email}">
            <div class="user-info">
                <c:if test="${not empty sessionScope.userId}">
                    <img src="/user/avatar?userId=${sessionScope.userId}"
                         alt="Avatar"
                         class="avatar-small"
                         onerror="this.style.display='none'">
                </c:if>
                <p style="margin: 0; font-size: 1.2em;"> Hello, ${sessionScope.email}!</p>
            </div>
            <div class="nav-links">
                <a href="/profile"> Profile</a>
                <a href="/users/with-avatars"> Users</a>
                <a href="/logout">🚪 Logout</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="nav-links">
                <a href="/sign-up">Sign Up</a>
                <a href="/sign-in"> Sign In</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>