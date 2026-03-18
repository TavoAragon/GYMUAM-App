const API_URL = '/api/actividades';

document.addEventListener('DOMContentLoaded', loadActividades);
document.getElementById('actividadForm').addEventListener('submit', saveActividad);
document.getElementById('cancelEdit').addEventListener('click', cancelEdit);

async function loadActividades() {
    const response = await fetch(API_URL);
    const actividades = await response.json();
    const tbody = document.getElementById('actividadesTable');
    tbody.innerHTML = '';
    actividades.forEach(act => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${act.idActividad}</td>
            <td>${act.nombreActividad}</td>
            <td>
                <button class="action-btn" onclick="editActividad(${act.idActividad})">Editar</button>
                <button class="action-btn delete-btn" onclick="deleteActividad(${act.idActividad})">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function saveActividad(e) {
    e.preventDefault();
    const id = document.getElementById('actividadId').value;
    const actividad = {
        nombreActividad: document.getElementById('nombreActividad').value
    };
    let response;
    if (id) {
        response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(actividad)
        });
    } else {
        response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(actividad)
        });
    }
    if (response.ok) {
        cancelEdit();
        loadActividades();
    } else alert('Error');
}

async function editActividad(id) {
    const response = await fetch(`${API_URL}/${id}`);
    const act = await response.json();
    document.getElementById('actividadId').value = act.idActividad;
    document.getElementById('nombreActividad').value = act.nombreActividad;
    document.getElementById('formTitle').innerText = 'Editar Actividad';
    document.getElementById('cancelEdit').classList.remove('hidden');
}

function cancelEdit() {
    document.getElementById('actividadForm').reset();
    document.getElementById('actividadId').value = '';
    document.getElementById('formTitle').innerText = 'Registrar Nueva Actividad';
    document.getElementById('cancelEdit').classList.add('hidden');
}

async function deleteActividad(id) {
    if (confirm('¿Eliminar actividad?')) {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) loadActividades();
        else alert('Error (puede tener asistencias asociadas)');
    }
}