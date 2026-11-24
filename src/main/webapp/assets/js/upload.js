// Ждем полной загрузки страницы
document.addEventListener("DOMContentLoaded", function() {
    
    // Находим инпут выбора файла и тег картинки по их ID
    const filePicker = document.getElementById('filePicker');
    const avatarPreview = document.getElementById('avatarPreview');

    // Если оба элемента найдены на странице
    if (filePicker && avatarPreview) {
        
        // Слушаем событие изменения (когда файл выбран)
        filePicker.addEventListener('change', function(event) {
            const file = event.target.files[0]; // Берем первый выбранный файл
            
            if (file) {
                // Создаем временную локальную ссылку на файл в памяти браузера
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    // Подставляем эту ссылку в атрибут src нашей картинки
                    avatarPreview.src = e.target.result;
                };
                
                reader.readAsDataURL(file);
            }
        });
    }
});
