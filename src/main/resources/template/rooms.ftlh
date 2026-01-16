<#assign pageTitle = "Комнаты">
<#assign active = "rooms">
<#assign extraCss = ["/css/rooms.css"]>

<#assign content>
    <div class="page-header">
        <h1><i class="fas fa-door-open"></i> Наши Игровые Комнаты</h1>
        <p>Выберите зал для группового гейминга</p>
    </div>
    <div class="rooms-grid">
        <div class="card room-card">
            <div class="room-header"><div class="room-name"><i class="fas fa-users"></i> Основной зал</div></div>
            <div class="room-description">Профессиональные станции для киберспорта и командных игр. Идеально для турниров.</div>
            <div class="specs-list">
                <div class="spec-item"><i class="fas fa-bolt"></i> RTX 4070 • Ryzen 7 5800X • 32GB RAM</div>
                <div class="spec-item"><i class="fas fa-tv"></i> 27" 240Hz IPS мониторы</div>
                <div class="spec-item"><i class="fas fa-users"></i> До 5 игроков</div>
            </div>
            <div class="room-features">
                <div class="features-title">Особенности:</div>
                <div class="features-list">
                    <span class="feature-tag">Любые игры</span>
                    <span class="feature-tag">Доступная цена</span>
                </div>
            </div>
        </div>
        <div class="card room-card">
            <div class="room-header"><div class="room-name"><i class="fas fa-crown"></i> VIP комната</div></div>
            <div class="room-description">Приватная зона с топ-железом и комфортом для ценителей.</div>
            <div class="specs-list">
                <div class="spec-item"><i class="fas fa-bolt"></i> RTX 4090 • i9-14900K • 64GB DDR5</div>
                <div class="spec-item"><i class="fas fa-tv"></i> 32" 4K 144Hz OLED</div>
            </div>
            <div class="room-features">
                <div class="features-title">Особенности:</div>
                <div class="features-list">
                    <span class="feature-tag">Приватность</span>
                    <span class="feature-tag">Премиум</span>
                    <span class="feature-tag">Буткемпы</span>
                    <span class="feature-tag">Мини-бар</span>
                </div>
            </div>
        </div>
        <div class="card room-card">
            <div class="room-header"><div class="room-name"><i class="fas fa-gamepad"></i> Консольная зона</div></div>
            <div class="room-description">Консольный гейминг с PS5/Xbox и 4K TV для друзей.</div>
            <div class="specs-list">
                <div class="spec-item"><i class="fas fa-gamepad"></i> PS5 + Xbox Series X</div>
                <div class="spec-item"><i class="fas fa-tv"></i> 55" 4K QLED TV</div>
                <div class="spec-item"><i class="fas fa-users"></i> Плати за одного — играйте вдвоем</div>
            </div>
            <div class="room-features">
                <div class="features-title">Особенности:</div>
                <div class="features-list">
                    <span class="feature-tag">Эксклюзивы</span>
                    <span class="feature-tag">Удобство</span>
                    <span class="feature-tag">Мини-бар</span>
                </div>
            </div>
        </div>
    </div>
    <div class="auto-booking">
        <a href="${contextPath}/auto-booking" class="btn btn-primary">Забронировать</a>
    </div>
    <a href="${contextPath}/computers" class="btn btn-secondary">← К отдельным ПК</a>
</#assign>

<#include "layout.ftlh">