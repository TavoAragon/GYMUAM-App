const API_URL = '/api/prestamos';
const ALUMNOS_URL = '/api/alumnos';
const INVENTARIO_URL = '/api/inventario';

let alumnos = [], juegos = [];

document.addEventListener('DOMContentLoaded', async () => {
    await loadAlumnosSelect();
    await loadJuegosSelect();
    await loadPrestamos();
    document.getElementById('prestamoForm').addEventListener('submit', crearPrestamo);
    document.getElementById('filterAll').addEventListener('click', () => loadPrestamos());
    document.getElementById('filterActive').addEventListener('click', () => loadPrestamos('activos'));
    document.getElementById('alumnoFilter').addEventListener('change', filtrarPorAlumno);
});

async function loadAlumnosSelect() {
    const res = await fetch(ALUMNOS_URL);
    alumnos = await res.json();
    const select = document.getElementById('alumnoSelect');
    const filter = document.getElementById('alumnoFilter');
    select.innerHTML = '<option value="">Seleccione un alumno</option>';
    filter.innerHTML = '<option value="">-- Todos --</option>';
    alumnos.forEach(a => {
        select.innerHTML += `<option value="${a.matricula}">${a.matricula} - ${a.nombreCompleto}</option>`;
        filter.innerHTML += `<option value="${a.matricula}">${a.matricula} - ${a.nombreCompleto}</option>`;
    });
}

async function loadJuegosSelect() {
    const res = await fetch(INVENTARIO_URL);
    juegos = await res.json();
    const select = document.getElementById('juegoSelect');
    select.innerHTML = '<option value="">Seleccione un juego</option>';
    juegos.forEach(j => {
        select.innerHTML += `<option value="${j.idJuego}">${j.nombreJuego} (disp: ${j.cantidad})</option>`;
    });
}

async function loadPrestamos(filter = 'todos') {
    let url = API_URL;
    if (filter === 'activos') url = '/api/prestamos/activos';
    const res = await fetch(url);
    const prestamos = await res.json();
    renderPrestamos(prestamos);
}

async function filtrarPorAlumno() {
    const matricula = document.getElementById('alumnoFilter').value;
    if (!matricula) return loadPrestamos();
    const res = await fetch(`/api/prestamos/alumno/${matricula}`);
    const prestamos = await res.json();
    renderPrestamos(prestamos);
}

function renderPrestamos(prestamos) {
    const tbody = document.getElementById('prestamosTable');
    tbody.innerHTML = '';
    prestamos.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${p.idPrestamo}</td>
            <td>${p.alumno.matricula} - ${p.alumno.nombreCompleto}</td>
            <td>${p.juego.nombreJuego}</td>
            <td>${p.fechaPrestamo}</td>
            <td>${p.fechaDevolucion || 'Pendiente'}</td>
            <td>
                ${!p.fechaDevolucion ? `<button class="action-btn" onclick="devolver(${p.idPrestamo})">Devolver</button>` : ''}
                <button class="action-btn delete-btn" onclick="eliminarPrestamo(${p.idPrestamo})">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function crearPrestamo(e) {
    e.preventDefault();
    const alumnoMat = document.getElementById('alumnoSelect').value;
    const juegoId = document.getElementById('juegoSelect').value;
    if (!alumnoMat || !juegoId) return alert('Seleccione alumno y juego');

    const prestamo = {
        alumno: { matricula: parseInt(alumnoMat) },
        juego: { idJuego: parseInt(juegoId) }
    };

    const res = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(prestamo)
    });

    if (res.ok) {
        alert('Préstamo registrado');
        document.getElementById('prestamoForm').reset();
        loadPrestamos();
        loadJuegosSelect(); // refresh stock
    } else {
        const err = await res.text();
        alert('Error: ' + err);
    }
}

async function devolver(id) {
    if (!confirm('¿Registrar devolución?')) return;
    const res = await fetch(`${API_URL}/${id}/devolver`, { method: 'PUT' });
    if (res.ok) {
        loadPrestamos();
        loadJuegosSelect();
    } else alert('Error');
}

async function eliminarPrestamo(id) {
    if (!confirm('¿Eliminar préstamo?')) return;
    const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    if (res.ok) loadPrestamos();
    else alert('Error');
}