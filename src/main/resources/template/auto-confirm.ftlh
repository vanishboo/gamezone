<#assign pageTitle = "Подтверждение авто-брони">
<#assign active = "rooms">
<#assign extraCss = ["/css/booking.css", "/css/auto-booking.css"]>

<#assign content>
    <div class="card booking-card">
        <div class="page-header">
            <h1><i class="fas fa-check-circle"></i> Подтверждение</h1>
            <p>Проверьте детали</p>
        </div>
        <div class="booking-info">
            <div class="info-grid">
                <div class="info-item">
                    <span class="info-label"><i class="fas fa-calendar"></i> Дата</span>
                    <span class="info-value">${bookingDate}</span>
                </div>
                <div class="info-item">
                    <span class="info-label"><i class="fas fa-clock"></i> Время начала</span>
                    <span class="info-value">${startTime}</span>
                </div>
                <div class="info-item">
                    <span class="info-label"><i class="fas fa-stopwatch"></i> Длительность</span>
                    <span class="info-value">${duration} мин</span>
                </div>
                <div class="info-item">
                    <span class="info-label"><i class="fas fa-users"></i> Мест</span>
                    <span class="info-value">${computers?size}</span>
                </div>
            </div>
        </div>
        <div class="total-price">Общая стоимость: ${totalPrice} ₽</div>
        <div class="actions">
            <form action="${contextPath}/auto-confirm" method="post" class="inline">
                <button type="submit" class="btn btn-primary btn-confirm">✅ Подтвердить</button>
            </form>
            <a href="${contextPath}/auto-booking" class="btn btn-secondary btn-cancel">❌ Отмена</a>
        </div>
    </div>
</#assign>

<#include "layout.ftlh">