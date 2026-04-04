<#import "layout.ftl" as layout>
<#import "common.ftl" as common>
<@layout.page title=pageTitle>
    <section class="section">
        <div class="container">
            <h1>${car.name}</h1>
            <p class="muted">${car.shortDescription}</p>
            <div class="grid" style="grid-template-columns: 1.1fr 1fr; align-items: start;">
                <div class="card">
                    <img src="${car.imageUrl}" alt="${car.name}">
                </div>
                <div>
                    <h2>${springMacroRequestContext.getMessage("config.title")}</h2>
                    <#list car.trims as trim>
                        <div class="trim">
                            <h3>${trim.name}</h3>
                            <p class="muted">${trim.description}</p>
                            <div class="price">+ ${trim.extraPrice} ₽</div>
                            <ul>
                                <#list trim.options as option>
                                    <li>${option.name} — ${option.price} ₽</li>
                                </#list>
                            </ul>
                            <a class="btn" href="/models/${car.id}/summary/${trim.id}">Выбрать эту комплектацию</a>
                        </div>
                    </#list>
                </div>
            </div>
            <@common.leadForm pageSource="/models/${car.id}" />
        </div>
    </section>
</@layout.page>
