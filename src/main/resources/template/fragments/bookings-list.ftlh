<div class="bookings-grid">
    <#list bookings as b>
        <div class="booking-item">
            <div class="booking-item-header">
                <div>
                    <div class="booking-type">
                        <#assign type = roomMap[b.computerId?string]!0>
                        <#if type == 1>Основной зал
                        <#elseif type == 2>VIP комната
                        <#elseif type == 3>Консольная зона</#if>
                    </div>
                    <div class="booking-date">${b.startTime?substring(0,10)?replace("-", ".")}</div>
                    <div class="booking-time">${b.startTime?substring(11,16)} - ${b.endTime?substring(11,16)}</div>
                </div>
                <div class="booking-price">
                    <div class="price-amount">${b.totalPrice} ₽</div>
                    <div class="price-label">Общая стоимость</div>
                </div>
            </div>

            <div class="booking-details">
                <#assign hours = 0>
                <#assign minutes = 0>
                <#if b.startTime?? && b.endTime??>
                    <#assign startHour = b.startTime[11..12]?number>
                    <#assign startMin  = b.startTime[14..15]?number>
                    <#assign endHour   = b.endTime[11..12]?number>
                    <#assign endMin    = b.endTime[14..15]?number>

                    <#if endHour < startHour><#assign endHour += 24></#if>

                    <#assign hours = endHour - startHour>
                    <#assign minutes = endMin - startMin>
                    <#if minutes < 0>
                        <#assign hours -= 1>
                        <#assign minutes += 60>
                    </#if>
                </#if>

                <div class="detail-item">
                    <i class="fas fa-clock detail-icon"></i>
                    <div>
                        <div class="detail-label">Длительность</div>
                        <div class="detail-value">${hours}ч ${minutes}м</div>
                    </div>
                </div>

                <div class="detail-item">
                    <div class="detail-label">Номер станции</div>
                    <div class="detail-value">${b.computerId}</div>
                </div>

                <div class="detail-item">
                    <div class="detail-label">Статус</div>
                    <div class="booking-status status-${b.status}">
                        <#switch b.status>
                            <#case "confirmed">Подтверждена<#break>
                            <#case "active">В процессе<#break>
                            <#case "completed">Завершена<#break>
                            <#case "cancelled">Отменена<#break>
                            <#default>${b.status}
                        </#switch>
                    </div>
                </div>
            </div>

            <div class="booking-actions">
                <#if b.status == "confirmed">
                    <form action="${contextPath}/edit-booking" method="get" class="booking-form inline">
                        <input type="hidden" name="id" value="${b.id}">
                        <button type="submit" class="btn-booking-actions btn-primary btn-small">Изменить</button>
                    </form>
                </#if>

                <#if b.status == "confirmed" || b.status == "active">
                    <form action="${contextPath}/booking-actions" method="post" class="booking-form inline">
                        <input type="hidden" name="id" value="${b.id}">
                        <input type="hidden" name="action" value="cancel">
                        <button type="submit" class="btn-booking-actions btn-secondary btn-small">Отменить</button>
                    </form>
                </#if>

                <form action="${contextPath}/booking-actions" method="post" class="booking-form inline">
                    <input type="hidden" name="id" value="${b.id}">
                    <input type="hidden" name="action" value="delete">
                    <button type="submit" class="btn-booking-actions btn-danger btn-small">Удалить</button>
                </form>
            </div>
        </div>
    </#list>
</div>