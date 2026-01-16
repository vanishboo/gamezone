function showGames(btn) {
    const list = btn.nextElementSibling;
    const icon = btn.querySelector('.buttong-games-icon');

    list.classList.toggle('show')

    if (list.classList.contains('show')) {
        icon.classList.remove('fa-chevron-down');
        icon.classList.add('fa-chevron-up');
    } else {
        icon.classList.remove('fa-chevron-up');
        icon.classList.add('fa-chevron-down');
    }
}