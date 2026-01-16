<#assign pageTitle = "Профиль">
<#assign active = "profile">
<#assign extraCss = ["/css/profile.css"]>

<#assign content>
    <div class="page-header">
        <h1><i class="fas fa-user"></i> Профиль игрока</h1>
    </div>
    <div class="profile-info">
        <div class="card profile-card">
            <h3><i class="fas fa-user"></i> Логин</h3>
            <p>${user.username}</p>
        </div>
        <div class="card profile-card">
            <h3><i class="fas fa-id-card"></i> ID аккаунта</h3>
            <p>#${user.id}</p>
        </div>
    </div>
    <div class="profile-actions">
        <a href="${contextPath}/my-bookings" class="btn btn-primary user-btn">Мои брони</a>
        <a href="${contextPath}/computers" class="btn btn-primary user-btn">Забронировать ПК</a>
    </div>
</#assign>

<#include "layout.ftlh">