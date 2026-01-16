<#assign pageTitle = "Редактирование брони">
<#assign active = "bookings">

<#assign content>
    <div class="container">
        <div class="page-header">
            <h1><i class="fas fa-edit"></i> Редактировать бронь</h1>
        </div>

        <div class="card">
            <form action="${contextPath}/edit-booking" method="post">
                <input type="hidden" name="bookingId" value="${booking.id}">

                <#if error??>
                    <div class="msg-error">
                        <#if error == "intersect"><i class="fas fa-exclamation-triangle"></i> Время занято! Попробуйте другое</#if>
                    </div>
                </#if>

                <div class="form-group">
                    <label class="form-label"><i class="fas fa-calendar-day"></i> Дата:</label>
                    <input type="date" class="form-input" name="date"
                           value="${booking.startTime?string[0..9]?replace('-', '')?number?string('####-##-##')}"
                           min="${.now?string('yyyy-MM-dd')}" required>
                </div>

                <div class="form-group">
                    <label class="form-label"><i class="fas fa-clock"></i> Время начала:</label>
                    <select class="form-select" name="time" id="timeSelect" required>
                        <#assign currentTime = booking.startTime?string?substring(11,16) >
                        <#list 0..47 as i>
                            <#assign hour = (i/2)?floor >
                            <#assign minute = (i % 2) * 30 >
                            <#assign time = hour?string("00") + ":" + minute?string("00") >
                            <option value="${time}" <#if time == currentTime>selected</#if>>${time}</option>
                        </#list>
                    </select>

                </div>

                <div class="form-group">
                    <label class="form-label"><i class="fas fa-hourglass-half"></i> Длительность:</label>
                    <select class="form-select" name="duration" id="durationSelect" required>
                        <#list [1,2,3,4,5,6,7,8,9,10] as hours>
                            <#assign minutes = hours * 60>
                            <#assign durationText = hours + " час">

                            <#assign startHour = booking.startTime?string[11..12]?number>
                            <#assign startMin = booking.startTime?string[14..15]?number>
                            <#assign endHour = booking.endTime?string[11..12]?number>
                            <#assign endMin = booking.endTime?string[14..15]?number>
                            <#if endHour < startHour><#assign endHour = endHour + 24></#if>
                            <#assign oldHours = endHour - startHour>
                            <#assign oldMinutes = endMin - startMin>
                            <#if oldMinutes < 0>
                                <#assign oldHours = oldHours - 1>
                                <#assign oldMinutes = oldMinutes + 60>
                            </#if>
                            <#assign oldTotalMinutes = oldHours * 60 + oldMinutes>

                            <option value="${minutes}" <#if minutes == oldTotalMinutes>selected</#if>>
                                ${durationText} (${minutes} мин)
                            </option>
                        </#list>
                    </select>
                </div>

                <div class="text-center" style="background: rgba(0,255,136,0.1); padding: 1rem; border-radius: 8px; margin: 1.5rem 0; border: 1px solid var(--accent);">
                    <strong><i class="fas fa-coins"></i> Стоимость: <span id="price" style="color: var(--accent); font-size: 1.2rem;">${booking.totalPrice}</span> ₽</strong>
                </div>

                <div class="text-center" style="background: rgba(255,193,7,0.1); padding: 1rem; border-radius: 8px; margin: 1.5rem 0; border: 1px solid #ffc107; color: #ffc107;">
                    <i class="fas fa-exclamation-circle"></i> Если выбранное время занято - получите ошибку при сохранении
                </div>

                <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                    <button type="submit" class="btn btn-primary" style="flex: 1;">
                        <i class="fas fa-save"></i> Сохранить изменения
                    </button>
                    <a href="${contextPath}/my-bookings" class="btn btn-secondary" style="flex: 1; text-align: center;">
                        <i class="fas fa-times"></i> Отмена
                    </a>
                </div>
            </form>
        </div>
    </div>
</#assign>

<#include "layout.ftlh">

<script>
    document.getElementById('durationSelect').addEventListener('change', function() {
        const duration = this.value;
        <#assign startHour = booking.startTime?string[11..12]?number>
        <#assign startMin = booking.startTime?string[14..15]?number>
        <#assign endHour = booking.endTime?string[11..12]?number>
        <#assign endMin = booking.endTime?string[14..15]?number>
        <#if endHour < startHour><#assign endHour = endHour + 24></#if>
        <#assign oldHours = endHour - startHour>
        <#assign oldMinutes = endMin - startMin>
        <#if oldMinutes < 0>
        <#assign oldHours = oldHours - 1>
        <#assign oldMinutes = oldMinutes + 60>
        </#if>
        <#assign oldTotalMinutes = oldHours * 60 + oldMinutes>
        const pricePerHour = ${booking.totalPrice} / (${oldTotalMinutes} / 60);
        const newPrice = Math.round(pricePerHour * (duration / 60));
        document.getElementById('price').textContent = newPrice;
    });
</script>