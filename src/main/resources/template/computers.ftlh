<#assign pageTitle = "Компьютеры">
<#assign active = "computers">
<#assign extraCss = ["/css/computers.css"]>

<#assign content>
    <div class="page-header">
        <h1><i class="fas fa-desktop"></i> Игровые Зоны</h1>
        <p>Выберите компьютер для бронирования</p>
    </div>
    <div class="rooms-grid">
        <div class="card room-card">
            <div class="room-header">
                <div class="room-name"><i class="fas fa-users"></i> Основной зал</div>
                <div class="room-price">${mainRoomPrice}₽/час</div>
            </div>
            <div class="room-description">Профессиональные игровые станции для киберспорта и хардкорного гейминга</div>
            <div class="computers-list">
                <#list mainComputers as computer>
                    <div class="card computer-item">
                        <div class="computer-header">
                            <span class="computer-id">Компьютер ${computer.id}</span>
                        </div>
                        <div class="computer-specs">
                            <div class="spec-item"><i class="fas fa-microchip"></i> RTX 4070 • Ryzen 7 5800X • 32GB RAM</div>
                            <div class="spec-item"><i class="fas fa-tv"></i> 27" 120Hz IPS • 1ms • G-Sync</div>
                            <div class="spec-item"><i class="fas fa-keyboard"></i> Mechanical Keyboard • Gaming Mouse</div>
                            <div class="spec-item"><i class="fas fa-headphones"></i> 7.1 Surround Headset</div>
                        </div>
                        <div class="computer-games">
                            <div class="games-btn" onclick="showGames(this)">
                                <span>Игры</span>
                                <i class="fas fa-chevron-down buttong-games-icon"></i>
                            </div>
                            <div class="games-list">
                                <#list computer.games as game><span class="game-tag">${game.name}</span></#list>
                            </div>
                        </div>
                        <a href="${contextPath}/booking?computerId=${computer.id}" class="btn btn-primary book-btn">Забронировать</a>
                    </div>
                </#list>
            </div>
        </div>
        <div class="card room-card">
            <div class="room-header">
                <div class="room-name"><i class="fas fa-crown"></i> VIP комната</div>
                <div class="room-price">${vipRoomPrice}₽/час</div>
            </div>
            <div class="room-description">Максимальная производительность и премиум комфорт для ценителей</div>
            <div class="computers-list">
                <#list vipComputers as computer>
                    <div class="card computer-item">
                        <div class="computer-header"><span class="computer-id">VIP Компьютер ${computer.id}</span></div>
                        <div class="computer-specs">
                            <div class="spec-item"><i class="fas fa-microchip"></i> RTX 4090 • i9-14900K • 64GB DDR5</div>
                            <div class="spec-item"><i class="fas fa-tv"></i> 32" 4K 144Hz OLED • HDR1000</div>
                            <div class="spec-item"><i class="fas fa-keyboard"></i> Custom Mechanical • Wireless Mouse</div>
                            <div class="spec-item"><i class="fas fa-chair"></i> Ergonomic Gaming Chair • RGB Lighting</div>
                            <div class="spec-item"><i class="fas fa-tint"></i> Water Cooling • Silent Operation</div>
                        </div>
                        <div class="computer-games">
                            <div class="games-btn" onclick="showGames(this)">
                                <span>Игры</span>
                                <i class="fas fa-chevron-down buttong-games-icon"></i>
                            </div>
                        <div class="games-list"><#list computer.games as game><span class="game-tag">${game.name}</span></#list></div>
                        </div>
                        <a href="${contextPath}/booking?computerId=${computer.id}" class="btn btn-primary book-btn">Забронировать</a>
                    </div>
                </#list>
            </div>
        </div>
        <div class="card room-card">
            <div class="room-header">
                <div class="room-name"><i class="fas fa-gamepad"></i> Консольная зона</div>
                <div class="room-price">${consoleRoomPrice}₽/час</div>
            </div>
            <div class="room-description">Новейшие консоли с 4K TV и полными библиотеками игр</div>
            <div class="computers-list">
                <#list consoleComputers as computer>
                    <div class="card computer-item">
                        <div class="computer-header">
                            <span class="computer-id"><#if computer.id % 2 == 0>PlayStation 5<#else>Xbox Series X</#if></span>
                        </div>
                        <div class="computer-specs">
                            <div class="spec-item"><i class="fas fa-hdd"></i><#if computer.id % 2 == 0> PS5 Digital • 825GB SSD<#else>Xbox Series X • 1TB SSD</#if></div>
                            <div class="spec-item"><i class="fas fa-tv"></i> 55" 4K QLED TV • 120Hz • HDR</div>
                            <div class="spec-item"><i class="fas fa-gamepad"></i> Dual Wireless Controllers</div>
                            <div class="spec-item"><i class="fas fa-volume-up"></i> Dolby Atmos Sound</div>
                            <div class="spec-item"><i class="fas fa-book"></i> Full Game Library • Online Multiplayer</div>
                        </div>
                        <div class="computer-games">
                            <div class="games-btn" onclick="showGames(this)">
                            <span>Игры</span>
                            <i class="fas fa-chevron-down buttong-games-icon"></i>
                        </div>                            <div class="games-list"><#list computer.games as game><span class="game-tag">${game.name}</span></#list></div>
                        </div>
                        <a href="${contextPath}/booking?computerId=${computer.id}" class="btn btn-primary book-btn">Забронировать</a>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</#assign>

<#include "layout.ftlh">
<script src="${contextPath}/js/showGames.js"></script>