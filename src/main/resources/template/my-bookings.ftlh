<#assign pageTitle = "Мои брони">
<#assign active = "bookings">
<#assign extraCss = ["/css/bookings-list.css"]>

<#assign content>
  <div class="page-header">
    <h1><i class="fas fa-calendar-alt"></i> Мои бронирования</h1>
    <p>История ваших игровых сессий</p>
  </div>
  <div class="filter-bar">
    <button class="btn filter-btn active" onclick="loadBookings('all', this)">Все</button>
    <button class="btn filter-btn" onclick="loadBookings('confirmed', this)">Подтвержденные</button>
    <button class="btn filter-btn" onclick="loadBookings('active', this)">Активные</button>
    <button class="btn filter-btn" onclick="loadBookings('completed', this)">Завершённые</button>
    <button class="btn filter-btn" onclick="loadBookings('cancelled', this)">Отменённые</button>
  </div>
  <div id="bookingsContainer">
    <div class="empty-state">
      <i class="fas fa-calendar-times empty-icon"></i>
      <h3>Бронирований пока нет</h3>
      <p>Самое время забронировать сессию!</p>
      <a href="${contextPath}/computers" class="btn btn-primary">Выбрать компьютер</a>
    </div>
  </div>
</#assign>

<#include "layout.ftlh">
<script src="${contextPath}/js/bookingsFilter.js"></script>