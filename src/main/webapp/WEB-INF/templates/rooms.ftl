<#import "header.ftl" as c/>
<@c.page title="Rooms">
    <form action="/rooms" method="post" accept-charset="UTF-8">
        <input type="text" name="name" placeholder="Room name">
        <input type="submit" value="Create room">
    </form>
    <hr>
    <#list rooms as room>
        <a href="/rooms/${room.token}">&gt; ${room.name}</a>
        <br>
    </#list>
</@c.page>