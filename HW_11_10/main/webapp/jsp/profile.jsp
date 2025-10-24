<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 10.10.2025
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<div id="avatar-container">
    <choose>
        <when test="${not empty sessionScope.userId}">
            <img id="avatar-img"
                 src="/user/avatar?userId=${sessionScope.userId}"
                 alt="Avatar" class="avatar"
                 onerror="this.src='/images/default-avatar.png'; this.onerror=null;">
        </when>
        </otherwise>
            <div class="no-avatar">No Avatar</div>
        </otherwise>
    </choose>
</div>