<#import "layout.ftl" as layout>
<#import "common.ftl" as common>
<@layout.page title=pageTitle>
    <section class="section">
        <div class="container">
            <h1>${springMacroRequestContext.getMessage("summary.title")}</h1>
            <div class="summary-box">
                <h2>${car.name}</h2>
                <p class="muted">${trim.name} — ${trim.description}</p>
                <ul>
                    <#list trim.options as option>
                        <li>${option.name} — ${option.price} ₽</li>
                    </#list>
                </ul>
                <div class="price">${springMacroRequestContext.getMessage("summary.price")}: ${totalPrice} ₽</div>
                <a class="btn secondary" href="/models/${car.id}">Вернуться к выбору</a>
            </div>
            <@common.leadForm pageSource="/models/${car.id}/summary/${trim.id}" />
        </div>
    </section>
</@layout.page>
