<#import "/spring.ftl" as spring>
<#import "layout.ftlh" as layout>

<@layout.page titleKey="error.title">

    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow-sm border-danger">
                <div class="card-body text-center p-5">
                    <h1 class="display-4 text-danger">
                        ${statusCode!500}
                    </h1>

                    <h2 class="h4 mb-3">
                        ${errorMessage!"Что-то пошло не так..."}
                    </h2>

                    <p class="text-muted">
                        ${timestamp!}
                    </p>

                    <a href="/" class="btn btn-primary">
                        <@spring.message "error.home"/>
                    </a>
                </div>
            </div>
        </div>
    </div>

</@layout.page>