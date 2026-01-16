<#assign pageTitle = "Бронирование">
<#assign active = "computers">
<#assign extraCss = ["/css/booking.css"]>

<#assign content>
    <div class="card booking-card">
        <#if error??>
            <div class="msg-error">
                <#if error == "past_time">Нельзя бронировать на прошедшее время!</#if>
            </div>
        </#if>
        <div class="step-indicator">
            <div class="step ${(step == 1)?then('active', '')}">
                <div class="step-number">1</div>
                <div class="step-label">Дата</div>
            </div>
            <div class="step ${(step == 2)?then('active', '')}">
                <div class="step-number">2</div>
                <div class="step-label">Время</div>
            </div>
            <div class="step ${(step == 3)?then('active', '')}">
                <div class="step-number">3</div>
                <div class="step-label">Длительность</div>
            </div>
            <div class="step ${(step == 4)?then('active', '')}">
                <div class="step-number">4</div>
                <div class="step-label">Подтверждение</div>
            </div>
        </div>
        <#if step == 1>
            <form action="${contextPath}/booking" method="get">
                <input type="hidden" name="step" value="2">
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-calendar"></i> Выберите дату</label>
                    <input type="date" name="bookingDate" class="form-input" required min="${.now?string('yyyy-MM-dd')}">
                </div>
                <button type="submit" class="btn btn-primary">Продолжить →</button>
            </form>
        <#elseif step == 2>
            <form action="${contextPath}/booking" method="get">
                <input type="hidden" name="step" value="3">
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-clock"></i> Выберите время</label>
                    <div class="time-slots">
                        <#if freeSlots??>
                            <#list freeSlots?keys as time>
                                <#if freeSlots[time]>
                                    <button type="submit" name="startTime" value="${time}" class="btn time-slot available">${time}</button>
                                <#else>
                                    <div class="time-slot unavailable">${time}</div>
                                </#if>
                            </#list>
                        <#else>
                            <div style="grid-column: 1/-1; text-align: center; padding: 2rem;">Нет слотов</div>
                        </#if>
                    </div>
                </div>
            </form>
            <form action="${contextPath}/booking" method="get" class="inline">
                <input type="hidden" name="step" value="1">
                <button type="submit" class="btn btn-secondary">← Назад</button>
            </form>
        <#elseif step == 3>
            <form action="${contextPath}/booking" method="get">
                <input type="hidden" name="step" value="4">
                <div class="form-group">
                    <label class="form-label"><i class="fas fa-stopwatch"></i> Длительность</label>
                    <div class="duration-options">
                        <#if durations??>
                            <#list durations as dur>
                                <#assign durationMinutes = dur.hour * 60 + dur.minute>
                                <button type="submit" name="duration" value="${durationMinutes}" class="btn duration-option">
                                    <div class="duration-time">${dur}</div>
                                    <div class="duration-minutes">${durationMinutes} мин</div>
                                </button>
                            </#list>
                        <#else>
                            <div style="grid-column: 1/-1; text-align: center; padding: 2rem;">Нет длительностей</div>
                        </#if>
                    </div>
                </div>
            </form>
            <form action="${contextPath}/booking" method="get" class="inline">
                <input type="hidden" name="step" value="2">
                <input type="hidden" name="bookingDate" value="${Session.bookingDate!}">
                <button type="submit" class="btn btn-secondary">← Назад</button>
            </form>
        <#elseif step == 4>
            <div class="form-group">
                <label class="form-label"><i class="fas fa-check-circle"></i> Подтверждение</label>
                <div class="confirmation-details">
                    <div class="detail-row">
                        <span class="detail-label">Тип станции:</span>
                        <span class="detail-value">
                            <#if Session.computer??><#if Session.computer.roomId == 1>Основной зал<#elseif Session.computer.roomId == 2>VIP<#else>Консоль</#if></#if>
                        </span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Дата:</span> <span class="detail-value">${Session.bookingDate!}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Время:</span> <span class="detail-value">${Session.startTime!} - ${Session.endTime!}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Длительность:</span> <span class="detail-value">${(Session.duration / 60)?floor}ч ${(Session.duration % 60)}м</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Стоимость:</span> <span class="detail-value total-price">${Session.price!} ₽</span>
                    </div>
                </div>
            </div>
            <form action="${contextPath}/booking" method="post">
                <button type="submit" class="btn btn-primary">Подтвердить</button>
            </form>
            <form action="${contextPath}/booking" method="get" class="inline">
                <input type="hidden" name="step" value="3">
                <input type="hidden" name="bookingDate" value="${Session.bookingDate!}">
                <input type="hidden" name="startTime" value="${Session.startTime!}">
                <button type="submit" class="btn btn-secondary">← Назад</button>
            </form>
        </#if>
    </div>
</#assign>

<#include "layout.ftlh">