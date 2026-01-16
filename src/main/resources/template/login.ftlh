<#assign pageTitle = "Вход">
<#assign content>
    <div class="title-main"><i class="fas fa-sign-in-alt"></i> GameZone</div>
    <h2 class="title-form">Вход в аккаунт</h2>
    <#if error??>
        <div class="msg-error">
            <#if error == "invalid">Неверный логин/пароль</#if>
        </div>
    </#if>
    <form action="${contextPath}/login" method="post">
        <div class="field-input">
            <label class="label-input"><i class="fas fa-user"></i> Логин</label>
            <input type="text" name="username" class="input-text" placeholder="введите логин" required>
        </div>
        <div class="field-input">
            <label class="label-input"><i class="fas fa-lock"></i> Пароль</label>
            <input type="password" name="password" class="input-text" placeholder="введите пароль" required>
        </div>
        <button type="submit" class="btn-go">Войти</button>
    </form>
    <div class="links-auth">Нет аккаунта? <a href="${contextPath}/register" class="link-reg">Зарегистрироваться</a></div>
</#assign>

<#include "layout_auth.ftlh">