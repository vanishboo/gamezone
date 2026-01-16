<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle!''} — GameZone</title>
    <link rel="stylesheet" href="${contextPath}/css/main.css">
    <#if extraCss??>
        <#list extraCss as css><link rel="stylesheet" href="${contextPath}${css}"></#list>
    </#if>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<#if active??><#include "fragments/navbar.ftlh"></#if>
<main class="container">
    ${content}
</main>
<#if extraJs??>
    <#list extraJs as js><script src="${contextPath}${js}"></script></#list>
</#if>
</body>
<script>
    window.CONTEXT_PATH = '${contextPath}';
</script>
</html>