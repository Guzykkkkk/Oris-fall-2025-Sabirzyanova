<#import "layout.ftl" as layout>
<#import "common.ftl" as common>
<@layout.page title=pageTitle>
    <section class="hero">
        <div class="container">
            <h1>${springMacroRequestContext.getMessage("hero.title")}</h1>
            <p>${springMacroRequestContext.getMessage("hero.subtitle")}</p>
            <a class="btn secondary" href="/models">${springMacroRequestContext.getMessage("nav.models")}</a>
        </div>
    </section>
    <section class="section">
        <div class="container">
            <h2>${springMacroRequestContext.getMessage("home.about.title")}</h2>
            <p class="muted">${springMacroRequestContext.getMessage("home.about.text")}</p>
            <div class="grid">
                <#list models as car>
                    <div class="card">
                        <img src="${car.imageUrl}" alt="${car.name}">
                        <div class="card-body">
                            <h3>${car.name}</h3>
                            <p class="muted">${car.shortDescription}</p>
                            <div class="price">от ${car.basePrice} ₽</div>
                            <a class="btn" href="/models/${car.id}">Открыть конфигуратор</a>
                        </div>
                    </div>
                </#list>
            </div>
            <@common.leadForm pageSource="/" />
        </div>
    </section>
</@layout.page>
