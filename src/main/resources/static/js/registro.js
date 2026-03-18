const API_URL = '/api/registro';
const ALUMNOS_URL = '/api/alumnos';
const ACTIVIDADES_URL = '/api/actividades';

let alumnos = [], actividades = [];

document.addEventListener('DOMContentLoaded', async () => {
    await loadAlumnosSelects();
    await loadActividadesSelect();
    await loadRegistros();
    document.getElementById('registroForm').addEventListener('submit', crearRegistro);
    document.getElementById('filtrarBtn').addEventListener('click', filtrar);
});

async function loadAlumnosSelects() {
    const res = await fetch(ALUMNOS_URL);
    alumnos = await res.json();
    const selectForm = document.getElementById('alumnoRegSelect');
    const selectFiltro = document.getElementById('alumnoFiltro');
    selectForm.innerHTML = '<option value="">Seleccione un alumno</option>';
    selectFiltro.innerHTML = '<option value="">-- Todos --</option>';
    alumnos.forEach(a => {
        selectForm.innerHTML += `<option value="${a.matricula}">${a.matricula} - ${a.nombreCompleto}</option>`;
        selectFiltro.innerHTML += `<option value="${a.matricula}">${a.matricula} - ${a.nombreCompleto}</option>`;
    });
}

async function loadActividadesSelect() {
    const res = await fetch(ACTIVIDADES_URL);
    actividades = await res.json();
    const select = document.getElementById('actividadRegSelect');
    select.innerHTML = '<option value="">Seleccione una actividad</option>';
    actividades.forEach(a => {
        select.innerHTML += `<option value="${a.idActividad}">${a.nombreActividad}</option>`;
    });
}

async function loadRegistros(filtros = {}) {
    let url = API_URL;
    const params = new URLSearchParams();
    if (filtros.alumno) params.append('alumno', filtros.alumno);
    if (filtros.fecha) params.append('fecha', filtros.fecha);
    if (params.toString()) url += '?' + params.toString();

    // Nota: El backend no tiene endpoints de filtro directo, usamos los que existen:
    // - findByAlumnoMatricula y findByFecha. Pero podemos obtener todos y filtrar en cliente.
    // Por simplicidad, cargamos todos y filtramos en cliente. Para producción, sería mejor implementar endpoints en backend.
    const res = await fetch(API_URL);
    let registros = await res.json();

    if (filtros.alumno) {
        registros = registros.filter(r => r.alumno.matricula == filtros.alumno);
    }
    if (filtros.fecha) {
        registros = registros.filter(r => r.fecha === filtros.fecha);
    }
    renderRegistros(registros);
}

function renderRegistros(registros) {
    const tbody = document.getElementById('registrosTable');
    tbody.innerHTML = '';
    registros.forEach(r => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${r.idRegistro}</td>
            <td>${r.alumno.matricula} - ${r.alumno.nombreCompleto}</td>
            <td>${r.actividad.nombreActividad}</td>
            <td>${r.fecha}</td>
            <td>
                <button class="action-btn delete-btn" onclick="eliminarRegistro(${r.idRegistro})">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function crearRegistro(e) {
    e.preventDefault();
    const alumnoMat = document.getElementById('alumnoRegSelect').value;
    const actividadId = document.getElementById('actividadRegSelect').value;
    const fecha = document.getElementById('fecha').value;
    if (!alumnoMat || !actividadId || !fecha) return alert('Complete todos los campos');

    const registro = {
        alumno: { matricula: parseInt(alumnoMat) },
        actividad: { idActividad: parseInt(actividadId) },
        fecha: fecha
    };

    const res = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registro)
    });

    if (res.ok) {
        alert('Asistencia registrada');
        document.getElementById('registroForm').reset();
        loadRegistros();
    } else {
        const err = await res.text();
        alert('Error: ' + err);
    }
}

function filtrar() {
    const alumno = document.getElementById('alumnoFiltro').value;
    const fecha = document.getElementById('fechaFiltro').value;
    loadRegistros({ alumno, fecha });
}

async function eliminarRegistro(id) {
    if (confirm('¿Eliminar registro de asistencia?')) {
        const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (res.ok) loadRegistros();
        else alert('Error');
    }
}