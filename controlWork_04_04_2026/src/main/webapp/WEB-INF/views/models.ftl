<#import "layout.ftl" as layout>
<#import "common.ftl" as common>
<@layout.page title=pageTitle>
    <section class="section">
        <div class="container">
            <h1>${springMacroRequestContext.getMessage("models.title")}</h1>
            <div class="grid">
                <#list models as car>
                    <div class="card">
                        <img src="${car.imageUrl}" alt="${car.name}">
                        <div class="card-body">
                            <h3>${car.name}</h3>
                            <p class="muted">${car.shortDescription}</p>
                            <div class="price">от ${car.basePrice} ₽</div>
                            <a class="btn" href="/models/${car.id}">Выбрать</a>
                        </div>
                    </div>
                </#list>
            </div>
            <@common.leadForm pageSource="/models" />
        </div>
    </section>
</@layout.page>
