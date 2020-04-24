<#import "header.ftl" as c/>
<@c.page title="Sign In">
<div>
    <#if error??>
        ${error}
    </#if>
    <form action="/signIn" method="post" accept-charset="UTF-8">
        <input type="email" name="email" placeholder="email">
        <input type="password" name="password" placeholder="password">
        <input type="submit" value="Sign In">
    </form>
</div>
</@c.page>