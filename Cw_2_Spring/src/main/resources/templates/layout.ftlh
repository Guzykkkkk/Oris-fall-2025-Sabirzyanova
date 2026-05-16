<#import "/spring.ftl" as spring>

<#macro page titleKey="app.title">
    <!DOCTYPE html>
    <html lang="${.locale}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><@spring.message titleKey/></title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">

        <style>
            body {
                min-height: 100vh;
            }

            .message {
                border-radius: 12px;
                padding: 12px 16px;
                margin-bottom: 10px;
            }

            .my-message {
                background: #e7f1ff;
                margin-left: 80px;
            }

            .other-message {
                background: #f1f3f5;
                margin-right: 80px;
            }

            .message-meta {
                font-size: 0.85rem;
                color: #6c757d;
                margin-bottom: 4px;
            }

            @media (max-width: 576px) {
                .my-message,
                .other-message {
                    margin-left: 0;
                    margin-right: 0;
                }
            }
        </style>
    </head>

    <body class="bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="/">
                <@spring.message "app.title"/>
            </a>

            <button class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#mainNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="mainNavbar">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/">
                            <@spring.message "nav.home"/>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/feed">
                            <@spring.message "nav.feed"/>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/users">
                            <@spring.message "nav.users"/>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/admin/messages">
                            <@spring.message "nav.admin"/>
                        </a>
                    </li>
                </ul>

                <div class="d-flex gap-2">
                    <a class="btn btn-outline-light btn-sm" href="?lang=ru">RU</a>
                    <a class="btn btn-outline-light btn-sm" href="?lang=en">EN</a>
                </div>
            </div>
        </div>
    </nav>

    <main class="container py-4">
        <#nested>
    </main>

    <footer class="container py-4 text-center text-muted small">
        <a href="?lang=ru">Русский</a>
        |
        <a href="?lang=en">English</a>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
    </html>
</#macro>