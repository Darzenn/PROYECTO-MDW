document.addEventListener("DOMContentLoaded", () => {

    const loginTab = document.getElementById('login-tab');
    const registerTab = document.getElementById('register-tab');
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const successMessage = document.getElementById('success-message');
    const loginMessage = document.getElementById('login-message');
    const registerMessage = document.getElementById('register-message');
    const inputCorreo = document.getElementById("register-email");

    inputCorreo.addEventListener("input", function () {
        const emailValido = inputCorreo.checkValidity();

        if (!emailValido) {
            inputCorreo.setCustomValidity("Por favor ingresa un correo electrónico válido.");
        } else {
            inputCorreo.setCustomValidity("");
        }
    });

// Simulación de "base de datos" en frontend
    let usuariosRegistrados = [
        {username: 'usuario1', password: 'pass123'},
        {username: 'usuario2', password: 'pass456'}
    ];

// Función para cambiar pestañas
    function showLogin() {
        loginTab.classList.add('active');
        registerTab.classList.remove('active');
        loginForm.classList.add('active');
        registerForm.classList.remove('active');
        successMessage.style.display = 'none';
        loginMessage.style.display = 'none';
        registerMessage.style.display = 'none';
    }

    function showRegister() {
        registerTab.classList.add('active');
        loginTab.classList.remove('active');
        registerForm.classList.add('active');
        loginForm.classList.remove('active');
        successMessage.style.display = 'none';
        loginMessage.style.display = 'none';
        registerMessage.style.display = 'none';
    }

    loginTab.addEventListener('click', showLogin);
    registerTab.addEventListener('click', showRegister);

// Manejo del registro
    registerForm.addEventListener('submit', (e) => {
        e.preventDefault();

        // Limpiar mensajes
        registerMessage.style.display = 'none';

        const username = document.getElementById('register-username').value.trim();
        const email = document.getElementById('register-email').value.trim();
        const password = document.getElementById('register-password').value;
        const password2 = document.getElementById('register-password2').value;

        // Validación simple
        if (password !== password2) {
            registerMessage.textContent = 'Las contraseñas no coinciden.';
            registerMessage.style.display = 'block';
            return;
        }
        if (usuariosRegistrados.some(u => u.username.toLowerCase() === username.toLowerCase())) {
            registerMessage.textContent = 'El usuario ya existe. Por favor, elige otro.';
            registerMessage.style.display = 'block';
            return;
        }

        // "Guardar" usuario (simulación)
        usuariosRegistrados.push({username, password});

        registerForm.reset();
        registerForm.classList.remove('active');
        successMessage.style.display = 'block';

        showLogin();
    });

// Manejo del login
    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();

        loginMessage.style.display = 'none';

        const username = document.getElementById('login-username').value.trim();
        const password = document.getElementById('login-password').value;

        const usuario = usuariosRegistrados.find(u => u.username.toLowerCase() === username.toLowerCase());

        if (!usuario) {
            loginMessage.textContent = "El usuario no existe. Por favor, regístrate primero.";
            loginMessage.style.display = 'block';
            return;
        }
        if (usuario.password !== password) {
            loginMessage.textContent = "Contraseña incorrecta.";
            loginMessage.style.display = 'block';
            return;
        }

        loginMessage.style.display = 'none';

        window.location.href = "paginaPrincipal.html";
    });

});