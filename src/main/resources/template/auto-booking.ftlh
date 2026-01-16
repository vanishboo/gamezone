<#assign pageTitle = "Авто-бронирование">
<#assign active = "rooms">
<#assign extraCss = ["/css/booking.css"]>

<#assign content>
    <div class="card booking-card">
        <div class="page-header">
            <h1><i class="fas fa-magic"></i> Авто-бронирование</h1>
            <p>Система подберёт лучший вариант</p>
        </div>
        <#if error??><div class="msg-error">❌ ${error}</div></#if>
        <form action="${contextPath}/auto-booking" method="post">
            <input type="hidden" name="auto" value="true">
            <div class="form-group">
                <label class="form-label"><i class="fas fa-users"></i> Количество игроков</label>
                <input type="number" name="players" class="form-input" min="1" max="10" required placeholder="Сколько ПК?">
            </div>
            <div class="form-group">
                <label class="form-label"><i class="fas fa-door-open"></i> Комната</label>
                <select name="roomId" class="form-select" required>
                    <option value="1">Основной зал</option>
                    <option value="2">VIP комната</option>
                    <option value="3">Консольная зона</option>
                </select>
            </div>
            <div class="form-group">
                <label class="form-label"><i class="fas fa-calendar"></i> Дата</label>
                <input type="date" name="bookingDate" class="form-input" required min="${.now?string('yyyy-MM-dd')}">
            </div>
            <div class="form-group">
                <label class="form-label"><i class="fas fa-clock"></i> Время начала</label>
                <input type="time" name="startTime" class="form-input" required step="1800">
            </div>
            <div class="form-group">
                <label class="form-label"><i class="fas fa-stopwatch"></i> Длительность</label>
                <select name="duration" class="form-select" required>
                    <option value="60">1 час</option><option value="120">2 часа</option><option value="180">3 часа</option>
                    <option value="240">4 часа</option><option value="300">5 часов</option><option value="360">6 часов</option>
                    <option value="420">7 часов</option><option value="480">8 часов</option><option value="540">9 часов</option>
                    <option value="600">10 часов</option> <#-- Убрал дубль 300 -->
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Найти и забронировать</button>
        </form>
        <a href="${contextPath}/computers" class="btn btn-secondary">← К выбору ПК</a>
    </div>
</#assign>

<#include "layout.ftlh">