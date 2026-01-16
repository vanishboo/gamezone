<nav class="navbar">
    <div class="logo">GameZone</div>
    <div class="nav-links">
        <a href="${contextPath}/home"       <#if active == 'home'>style="color:#00ff88"</#if>>Главная</a>
        <a href="${contextPath}/computers"  <#if active == 'computers'>style="color:#00ff88"</#if>>Компьютеры</a>
        <a href="${contextPath}/rooms"      <#if active == 'rooms'>style="color:#00ff88"</#if>>Комнаты</a>
        <a href="${contextPath}/my-bookings"<#if active == 'bookings'>style="color:#00ff88"</#if>>Мои брони</a>
        <a href="${contextPath}/profile"    <#if active == 'profile'>style="color:#00ff88"</#if>>Профиль</a>
        <a href="${contextPath}/logout">Выйти</a>
    </div>
</nav>