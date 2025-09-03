document.addEventListener("DOMContentLoaded", () => {
    // AUDIO DE FONDO
    const audio = document.getElementById("audioFondo");
    const botonAudio = document.getElementById("toggleMute");

    if (audio && botonAudio) {
        audio.volume = 0.7;
        let haIniciado = false;

        botonAudio.addEventListener("click", () => {
            if (!haIniciado) {
                audio.play();
                haIniciado = true;
                audio.muted = false;
                botonAudio.textContent = "🔇";
            } else {
                audio.muted = !audio.muted;
                botonAudio.textContent = audio.muted ? "🔈" : "🔇";
            }
        });
    }

    // CARRITO
    const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    const carritoIcono = document.getElementById('icono-carrito');
    const carritoFlotante = document.getElementById('carrito-flotante');

    carritoIcono?.addEventListener('click', () => {
        carritoFlotante.style.display = carritoFlotante.style.display === 'block' ? 'none' : 'block';
    });

    document.querySelectorAll('.btn-comprar').forEach(boton => {
        boton.addEventListener('click', function () {
            const card = this.closest('.card');
            const titulo = card.querySelector('h4').innerText;
            const precioText = card.querySelector('.price').innerText;
            const precio = parseFloat(precioText.replace(/[^\d.]/g, ''));

            const productoExistente = carrito.find(item => item.nombre === titulo);

            if (productoExistente) {
                productoExistente.cantidad += 1;
            } else {
                carrito.push({nombre: titulo, precio: precio, cantidad: 1});
            }

            actualizarCarrito();
        });
    });

    function actualizarCarrito() {
        const lista = carritoFlotante?.querySelector('ul');
        let totalElement = carritoFlotante?.querySelector('.total');

        if (!lista)
            return;

        lista.innerHTML = '';
        let total = 0;

        carrito.forEach((item, index) => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div class="item-carrito">
                    <span class="item-nombre">${item.nombre}</span>
                    <span class="item-detalle">S/ ${item.precio.toFixed(2)} x ${item.cantidad}</span>
                    <div class="item-controles">
                        <button class="btn-disminuir" data-index="${index}">➖</button>
                        <span class="cantidad">${item.cantidad}</span>
                        <button class="btn-aumentar" data-index="${index}">➕</button>
                        <button class="btn-eliminar" data-index="${index}">🗑️</button>
                    </div>
                </div>
            `;
            lista.appendChild(li);
            total += item.precio * item.cantidad;
        });

        if (!totalElement) {
            totalElement = document.createElement('p');
            totalElement.classList.add('total');
            carritoFlotante.appendChild(totalElement);
        }

        totalElement.textContent = `Total: S/ ${total.toFixed(2)}`;
        localStorage.setItem("carrito", JSON.stringify(carrito));

        document.querySelectorAll('.btn-disminuir').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const index = parseInt(e.target.getAttribute('data-index'));
                if (carrito[index].cantidad > 1) {
                    carrito[index].cantidad--;
                    actualizarCarrito();
                }
            });
        });

        document.querySelectorAll('.btn-aumentar').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const index = parseInt(e.target.getAttribute('data-index'));
                carrito[index].cantidad++;
                actualizarCarrito();
            });
        });

        document.querySelectorAll('.btn-eliminar').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const index = parseInt(e.target.getAttribute('data-index'));
                carrito.splice(index, 1);
                actualizarCarrito();
            });
        });
    }

    actualizarCarrito(); 


    if (window.location.pathname.includes("zonapago.html")) {
        const listaProductos = document.getElementById("lista-productos");
        const totalFinal = document.getElementById("total-final");
        const formulario = document.getElementById("pago-form");
        const mensajeExito = document.getElementById("mensaje-exito");

        const carritoGuardado = JSON.parse(localStorage.getItem("carrito")) || [];


        let total = 0;
        carritoGuardado.forEach(item => {
            const li = document.createElement("li");
            li.textContent = `${item.nombre} - S/ ${item.precio.toFixed(2)} x ${item.cantidad}`;
            listaProductos?.appendChild(li);
            total += item.precio * item.cantidad;
        });

        totalFinal.textContent = `Total: S/ ${total.toFixed(2)}`;

        formulario?.addEventListener("submit", (e) => {
            e.preventDefault();

            const nombre = document.getElementById("nombre").value.trim();
            const email = document.getElementById("email").value.trim();
            const tarjeta = document.getElementById("tarjeta").value.trim();
            const mes = document.getElementById("mes").value;
            const anio = document.getElementById("anio").value;
            const cupon = document.getElementById("cupon").value.trim().toUpperCase();
            const cvv = document.getElementById("cvv").value.trim();


            if (!nombre || !email || !tarjeta || !mes || !anio || !cvv) {
                alert("Por favor, completa todos los campos.");
                return;
            }


            let descuento = 0;
            let mensajeCupon = "";

            if (cupon === "DESCUENTO10") {
                descuento = total * 0.10;
                mensajeCupon = "Cupón aplicado: 10% de descuento.";
            } else if (cupon !== "") {
                mensajeCupon = "Cupón inválido. No se aplicó descuento.";
            }

            const totalPagar = total - descuento;

            document.getElementById("mensajePago").textContent = mensajeCupon;
            document.getElementById("descuento").textContent = `S/ ${descuento.toFixed(2)}`;
            document.getElementById("totalPagar").textContent = `S/ ${totalPagar.toFixed(2)}`;

            mensajeExito.hidden = false;
            formulario.reset();
            localStorage.removeItem("carrito");
            listaProductos.innerHTML = "";
            totalFinal.textContent = "Total: S/ 0.00";
        });
    }

    let lastScrollTop = 0;
    const header = document.querySelector('header');

    window.addEventListener("scroll", () => {
        const scrollTop = window.scrollY || document.documentElement.scrollTop;

        if (scrollTop > lastScrollTop) {

            header.style.top = "-100px"; 
        } else {

            header.style.top = "0";
        }

        lastScrollTop = scrollTop <= 0 ? 0 : scrollTop; 
    });

});


