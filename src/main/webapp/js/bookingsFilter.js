async function loadBookings(status, buttonElement) {
    const container = document.getElementById('bookingsContainer');
    container.innerHTML = '<div class="loading-spinner"><i class="fas fa-spinner fa-spin"></i><p>Загрузка...</p></div>';
    try {
        const response = await fetch(window.CONTEXT_PATH + '/bookings-ajax?status=' + status);
        if (!response.ok) throw new Error();

        const html = await response.text();
        container.innerHTML = html;

        document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
        if (buttonElement) buttonElement.classList.add('active');

    } catch (e) {
        container.innerHTML = '<div style="color: #ff4444; text-align: center; padding: 2rem;">Ошибка загрузки</div>';
    }
}


document.addEventListener('DOMContentLoaded', () => {
    const allBtn = document.querySelector('.filter-btn');
    if (allBtn) loadBookings('all', allBtn);
});