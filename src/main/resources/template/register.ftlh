<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация — GameZone</title>
    <link rel="stylesheet" href="/css/auth.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body class="body-auth">
<div class="box-auth">
    <div class="title-main"><i class="fas fa-user-plus"></i> GameZone</div>
    <h2 class="title-form">Регистрация</h2>
    <#if error??>
    <div class="msg-error">
        <#if error == "invalid_data">
            Проверьте данные (логин 4-20, пароль 8+)
        </#if>
        <#if error == "user_exists">
            Пользователь существует
        </#if>
    </div>
    </#if>

    <form action="${contextPath}/register" method="post">
        <div class="field-input">
            <label class="label-input"><i class="fas fa-user"></i> Логин</label>
            <input type="text" name="username" class="input-text" placeholder="4-20 символов" required>
        </div>
        <div class="field-input">
            <label class="label-input"><i class="fas fa-lock"></i> Пароль</label>
            <input type="password" name="password" class="input-text" placeholder="минимум 8 символов" required>
        </div>
        <button type="submit" class="btn-go">Создать аккаунт</button>
    </form>
    <div class="links-auth">Есть аккаунт? <a href="${contextPath}/login" class="link-reg">Войти</a></div>
</div>
</body>
</html>