<#macro leadForm pageSource>
    <div class="lead-box">
        <h3>${springMacroRequestContext.getMessage("lead.title")}</h3>
        <#if leadSuccess??><div class="flash ok">${leadSuccess}</div></#if>
        <#if leadError??><div class="flash error">${leadError}</div></#if>
        <form class="lead-form" method="post" action="/lead">
            <input type="hidden" name="pageSource" value="${pageSource}">
            <input type="text" name="phoneNumber" placeholder="+7 (999) 123-45-67">
            <button class="btn" type="submit">${springMacroRequestContext.getMessage("lead.button")}</button>
        </form>
    </div>
</#macro>
