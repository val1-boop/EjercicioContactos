const API = "/api/contactos";

async function cargarContactos() {
    const res = await fetch(API);
    const data = await res.json();

    const container = document.getElementById("contacts");
    container.innerHTML = "";

    data.forEach(c => {
        container.innerHTML += `
            <div class="card">
                <p><b>ID:</b> ${c.id}</p>
                <p><b>Nombre:</b> ${c.nombre}</p>
                <p><b>Teléfono:</b> ${c.telefono}</p>
                <button onclick="eliminar(${c.id})">Eliminar</button>
            </div>
        `;
    });
}

async function guardar() {
    const nombre = document.getElementById("nombre").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    const msg = document.getElementById("msg");

    if (!nombre || !telefono) {
        msg.textContent = "Todos los campos son obligatorios";
        msg.className = "msg error";
        return;
    }

    const res = await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, telefono })
    });

    if (!res.ok) {
        msg.textContent = "Error al registrar";
        msg.className = "msg error";
        return;
    }

    msg.textContent = "Contacto registrado correctamente";
    msg.className = "msg success";

    cargarContactos();
}

async function eliminar(id) {
    await fetch(`${API}/${id}`, { method: "DELETE" });
    cargarContactos();
}

cargarContactos();