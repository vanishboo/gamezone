<#assign pageTitle = "Личный кабинет">
<#assign active = "home">
<#assign extraCss = ["/css/profile.css"]>

<#assign content>
    <div class="page-header">
        <h1><i class="fas fa-trophy"></i> Готов к новым игровым победам?</h1>
        <p>Ваш личный игровой хаб в GameZone</p>
    </div>
    <div class="stats">
        <div class="card stat-card">
            <div class="stat-number">${cntBookings}</div>
            <div>Активных броней</div>
        </div>
    </div>
    <div class="quick-actions">
        <a href="${contextPath}/computers" class="card action-card btn-primary">
            <i class="fas fa-desktop"></i>
            <h3>Забронировать ПК</h3>
            <p>Выбери компьютер сам</p>
        </a>
        <a href="${contextPath}/my-bookings" class="card action-card btn-primary">
            <i class="fas fa-calendar-alt"></i>
            <h3>Мои брони</h3>
            <p>История и активные брони</p>
        </a>
        <a href="${contextPath}/rooms" class="card action-card btn-primary">
            <i class="fas fa-door-open"></i>
            <h3>Наши залы</h3>
            <p>Выбери зал и система подберет место</p>
        </a>
        <a href="${contextPath}/profile" class="card action-card btn-primary">
            <i class="fas fa-user"></i>
            <h3>Профиль</h3>
            <p>Настройки аккаунта</p>
        </a>
    </div>
</#assign>

<#include "layout.ftlh">