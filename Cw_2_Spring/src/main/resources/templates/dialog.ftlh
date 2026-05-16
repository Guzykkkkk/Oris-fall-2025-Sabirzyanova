<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Диалог</title>
    <style>
        body { font-family: Arial; max-width: 700px; margin: 20px auto; }
        .message { border-bottom: 1px solid #eee; padding: 8px; }
        .my-message { background: #e6f7ff; }
        .other-message { background: #f9f9f9; }
        .meta { font-size: 0.8em; color: #666; }
        .message-text { margin: 5px 0; }
        form { margin-top: 20px; padding: 10px; background: #f0f0f0; border-radius: 8px; }
        textarea { width: 80%; padding: 8px; }
        button { padding: 8px 16px; }
        .back { margin-bottom: 15px; display: inline-block; }
    </style>
</head>
<body>
<div>
    <a class="back" href="/feed">← Назад к диалогам</a>
    <a class="back" style="float:right" href="/logout">Выйти</a>
</div>
<h2>Диалог с пользователем</h2>
<div id="messages">
    <#if messages?has_content>
        <#list messages as msg>
            <div class="message <#if msg.sender.username == currentUsername>my-message<#else>other-message</#if>">
                <div class="meta">
                    <strong>${msg.sender.username}</strong> · ${msg.sentAt}
                    <#if msg.read> ✓ прочитано</#if>
                </div>
                <div class="message-text">${msg.text}</div>
            </div>
        </#list>
    <#else>
        <p>Сообщений пока нет. Напишите первое сообщение!</p>
    </#if><#if errors?? && errors.hasFieldErrors('text')>
        <div class="text-danger small mt-1">
            ${errors.getFieldError('text').defaultMessage}
        </div>
    </#if>

    <button class="btn btn-primary mt-3" type="submit">
        <@spring.message "dialog.send"/>
    </button>
</div>
</form>

<#assign lastMessageId = 0>
<#if messages?has_content>
<#assign lastMessageId = messages[messages?size - 1].id>
</#if>
<form action="/feed/${recipientId}" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <textarea name="text" rows="3" placeholder="Ваше сообщение..." required></textarea><br>
    <button type="submit">Отправить</button>
</form>
</body>
</html>

<script>
    let lastMessageId = ${lastMessageId?c};
    const recipientId = ${recipientId?c};

    function escapeHtml(text) {
        const div = document.createElement("div");
        div.innerText = text;
        return div.innerHTML;
    }

    function appendMessage(message) {
        const messagesContainer = document.getElementById("messages");

        if (document.querySelector('[data-message-id="' + message.id + '"]')) {
            return;
        }

        const emptyAlert = messagesContainer.querySelector(".alert");
        if (emptyAlert) {
            emptyAlert.remove();
        }

        const messageDiv = document.createElement("div");
        messageDiv.className = "message " + (message.own ? "my-message" : "other-message");
        messageDiv.dataset.messageId = message.id;

        messageDiv.innerHTML =
            '<div class="message-meta">' +
            '<strong>' + escapeHtml(message.senderUsername) + '</strong>' +
            ' · ' + escapeHtml(message.sentAt) +
            (message.read ? ' ✓' : '') +
            '</div>' +
            '<div class="message-text">' + escapeHtml(message.text) + '</div>';

        messagesContainer.appendChild(messageDiv);
        lastMessageId = Math.max(lastMessageId, message.id);
    }

    function loadNewMessages() {
        fetch("/feed/" + recipientId + "/new?afterId=" + lastMessageId)
            .then(response => response.json())
            .then(messages => {
                messages.forEach(appendMessage);
            })
            .catch(error => console.error("Polling error:", error));
    }

    setInterval(loadNewMessages, 5000);
</script>




