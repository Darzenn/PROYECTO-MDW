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
    if (!inputCorreo.checkValidity()) {
      inputCorreo.setCustomValidity("Por favor ingresa un correo electrónico válido.");
    } else {
      inputCorreo.setCustomValidity("");
    }
  });

  let usuariosRegistrados = [
    { username: 'usuario1', password: 'pass123' },
    { username: 'usuario2', password: 'pass456' }
  ];

  function hideMessages() {
    successMessage.classList.add("d-none");
    loginMessage.classList.add("d-none");
    registerMessage.classList.add("d-none");
  }

  function showLogin() {
    loginTab.classList.add('active');
    registerTab.classList.remove('active');
    loginForm.classList.add('active');
    registerForm.classList.remove('active');
    hideMessages();
  }

  function showRegister() {
    registerTab.classList.add('active');
    loginTab.classList.remove('active');
    registerForm.classList.add('active');
    loginForm.classList.remove('active');
    hideMessages();
  }

  loginTab.addEventListener('click', showLogin);
  registerTab.addEventListener('click', showRegister);

  // Registro
  registerForm.addEventListener('submit', (e) => {
    e.preventDefault();
    hideMessages();

    const username = document.getElementById('register-username').value.trim();
    const email = document.getElementById('register-email').value.trim();
    const password = document.getElementById('register-password').value;
    const password2 = document.getElementById('register-password2').value;

    if (password !== password2) {
      registerMessage.textContent = 'Las contraseñas no coinciden.';
      registerMessage.classList.remove("d-none");
      return;
    }
    if (usuariosRegistrados.some(u => u.username.toLowerCase() === username.toLowerCase())) {
      registerMessage.textContent = 'El usuario ya existe. Por favor, elige otro.';
      registerMessage.classList.remove("d-none");
      return;
    }

    usuariosRegistrados.push({ username, password });

    registerForm.reset();
    registerForm.classList.remove('active');
    successMessage.classList.remove("d-none");

    showLogin();
  });

  // Login
  loginForm.addEventListener('submit', (e) => {
    e.preventDefault();
    hideMessages();

    const username = document.getElementById('login-username').value.trim();
    const password = document.getElementById('login-password').value;

    const usuario = usuariosRegistrados.find(u => u.username.toLowerCase() === username.toLowerCase());

    if (!usuario) {
      loginMessage.textContent = "El usuario no existe. Por favor, regístrate primero.";
      loginMessage.classList.remove("d-none");
      return;
    }
    if (usuario.password !== password) {
      loginMessage.textContent = "Contraseña incorrecta.";
      loginMessage.classList.remove("d-none");
      return;
    }

    window.location.href = "paginaPrincipal.html";
  });
});