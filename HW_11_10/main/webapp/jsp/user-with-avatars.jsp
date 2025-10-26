<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 24.10.2025
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 24.10.2025
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<style>
    #users-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: 20px;
        padding: 20px;
    }
    .user-card {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 15px;
        padding: 20px;
        color: white;
        box-shadow: 0 8px 25px rgba(0,0,0,0.1);
        transition: transform 0.3s ease;
        display: flex;
        align-items: center;
        gap: 15px;
    }
    .user-card:hover {
        transform: translateY(-5px);
    }
    .user-card .avatar {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        border: 3px solid white;
        object-fit: cover;
    }
    .user-card .no-avatar {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        background: rgba(255,255,255,0.2);
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        border: 2px solid white;
    }
    .user-info h3 {
        margin: 0 0 5px 0;
        font-size: 1.2em;
    }
    .user-info p {
        margin: 0;
        opacity: 0.9;
        font-size: 0.9em;
    }
</style>

<div id="users-container">
    <p> Загружаем пользователей...</p>
</div>

<script>
    fetch('/users/with-avatars')
        .then(response => response.json())
        .then(users => {
            const container = document.getElementById('users-container');
            container.innerHTML = '';

            users.forEach(user => {
                const userDiv = document.createElement('div');
                userDiv.className = 'user-card';

                if (user.hasAvatar) {
                    const img = document.createElement('img');
                    img.src = '/user/avatar?userId=' + user.id;
                    img.className = 'avatar';
                    img.alt = user.username + ' avatar';
                    userDiv.appendChild(img);
                } else {
                    const noAvatar = document.createElement('div');
                    noAvatar.className = 'no-avatar';
                    noAvatar.textContent = '👤';
                    userDiv.appendChild(noAvatar);
                }

                const infoDiv = document.createElement('div');
                infoDiv.className = 'user-info';
                infoDiv.innerHTML = '<h3>' + user.username + '</h3><p>' + user.email + '</p>';

                userDiv.appendChild(infoDiv);
                container.appendChild(userDiv);
            });
        })
        .catch(error => {
            document.getElementById('users-container').innerHTML =
                '<p style="color: #ff4757; text-align: center;"> Ошибка загрузки пользователей</p>';
        });
</script>