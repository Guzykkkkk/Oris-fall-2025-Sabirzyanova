<#import "/spring.ftl" as spring>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "register.title"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-5">

            <div class="card shadow-sm">
                <div class="card-body">

                    <h2 class="mb-4 text-center">
                        <@spring.message "register.title"/>
                    </h2>

                    <form action="/register" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message "register.username"/>
                            </label>

                            <input class="form-control"
                                   type="text"
                                   name="username"
                                   value="${(form.username)!}"
                                   placeholder="<@spring.message 'register.username'/>"
                                   autofocus>

                            <#if errors?? && errors.hasFieldErrors('username')>
                                <div class="text-danger small mt-1">
                                    ${errors.getFieldError('username').defaultMessage}
                                </div>
                            </#if>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">
                                <@spring.message "register.password"/>
                            </label>

                            <input class="form-control"
                                   type="password"
                                   name="password"
                                   placeholder="<@spring.message 'register.password'/>">

                            <#if errors?? && errors.hasFieldErrors('password')>
                                <div class="text-danger small mt-1">
                                    ${errors.getFieldError('password').defaultMessage}
                                </div>
                            </#if>
                        </div>

                        <button class="btn btn-primary w-100" type="submit">
                            <@spring.message "register.submit"/>
                        </button>
                    </form>

                    <p class="mt-3 text-center">
                        Уже есть аккаунт?
                        <a href="/login">
                            <@spring.message "nav.login"/>
                        </a>
                    </p>

                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>