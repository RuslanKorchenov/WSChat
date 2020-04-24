<#import "header.ftl" as c/>
<@c.page title="Sign Up">

<div>
    <#if error??>
        ${error}
    </#if>
    <form action="/signUp" method="post" accept-charset="UTF-8">
        <input type="email" name="email" placeholder="email">
        <input type="text" name="username" placeholder="username">
        <input type="password" name="password" placeholder="password">
        <input type="submit" value="Sign Up">
    </form>
</div>
</@c.page>