<#macro page title>
    <!DOCTYPE html>
    <html lang="${.locale}">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title}</title>
        <link rel="stylesheet" href="/assets/styles.css">
    </head>
    <body>
    <header class="header">
        <div class="container header-inner">
            <a class="logo" href="/">F-мобиль</a>
            <nav class="nav">
                <a href="/">${springMacroRequestContext.getMessage("nav.home")}</a>
                <a href="/models">${springMacroRequestContext.getMessage("nav.models")}</a>
                <a href="?lang=ru">RU</a>
                <a href="?lang=en">EN</a>
            </nav>
        </div>
    </header>
    <main>
        <#nested>
    </main>
    <footer class="footer">
        <div class="container">${springMacroRequestContext.getMessage("footer.text")}</div>
    </footer>
    </body>
    </html>
</#macro>
