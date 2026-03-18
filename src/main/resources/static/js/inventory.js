const API_URL = '/api/inventario';

document.addEventListener('DOMContentLoaded', loadJuegos);
document.getElementById('juegoForm').addEventListener('submit', saveJuego);
document.getElementById('cancelEdit').addEventListener('click', cancelEdit);

async function loadJuegos() {
    const response = await fetch(API_URL);
    const juegos = await response.json();
    const tbody = document.getElementById('inventarioTable');
    tbody.innerHTML = '';
    juegos.forEach(j => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${j.idJuego}</td>
            <td>${j.nombreJuego}</td>
            <td>${j.cantidad}</td>
            <td>
                <button class="action-btn" onclick="editJuego(${j.idJuego})">Editar</button>
                <button class="action-btn delete-btn" onclick="deleteJuego(${j.idJuego})">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function saveJuego(e) {
    e.preventDefault();
    const id = document.getElementById('juegoId').value;
    const juego = {
        nombreJuego: document.getElementById('nombreJuego').value,
        cantidad: parseInt(document.getElementById('cantidad').value)
    };
    let response;
    if (id) {
        response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(juego)
        });
    } else {
        response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(juego)
        });
    }
    if (response.ok) {
        cancelEdit();
        loadJuegos();
    } else alert('Error');
}

async function editJuego(id) {
    const response = await fetch(`${API_URL}/${id}`);
    const juego = await response.json();
    document.getElementById('juegoId').value = juego.idJuego;
    document.getElementById('nombreJuego').value = juego.nombreJuego;
    document.getElementById('cantidad').value = juego.cantidad;
    document.getElementById('formTitle').innerText = 'Editar Juego';
    document.getElementById('cancelEdit').classList.remove('hidden');
}

function cancelEdit() {
    document.getElementById('juegoForm').reset();
    document.getElementById('juegoId').value = '';
    document.getElementById('formTitle').innerText = 'Agregar Juego';
    document.getElementById('cancelEdit').classList.add('hidden');
}

async function deleteJuego(id) {
    if (confirm('¿Eliminar juego?')) {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) loadJuegos();
        else alert('Error (puede tener préstamos asociados)');
    }
}