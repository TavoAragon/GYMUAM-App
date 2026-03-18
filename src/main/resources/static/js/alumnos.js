const API_URL = '/api/alumnos';

document.addEventListener('DOMContentLoaded', loadAlumnos);
document.getElementById('alumnoForm').addEventListener('submit', saveAlumno);
document.getElementById('cancelEdit').addEventListener('click', cancelEdit);

async function loadAlumnos() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Error backend");
        }

        const alumnos = await response.json();

        console.log("Datos recibidos:", alumnos);

        const tbody = document.getElementById('alumnosTable');
        tbody.innerHTML = '';

        alumnos.forEach(al => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${al.matricula}</td>
                <td>${al.nombreCompleto}</td>
                <td>${al.carrera}</td>
                <td>
                    <button class="action-btn" onclick="editAlumno(${al.matricula})">Editar</button>
                    <button class="action-btn delete-btn" onclick="deleteAlumno(${al.matricula})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(tr);
        });

    } catch(error) {
        console.error("Error cargando alumnos:", error);
        alert("No carga alumnos");
    }
}

async function saveAlumno(e) {
    e.preventDefault();
    const matricula = document.getElementById('matricula').value;
    const alumno = {
        matricula: parseInt(matricula),
        nombreCompleto: document.getElementById('nombreCompleto').value,
        carrera: document.getElementById('carrera').value,
        password: document.getElementById('password').value
    };

    const id = document.getElementById('alumnoMatricula').value;
    let response;
    if (id) {
        // Update
        response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(alumno)
        });
    } else {
        // Create
        response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(alumno)
        });
    }

    if (response.ok) {
        cancelEdit();
        loadAlumnos();
    } else {
        const error = await response.text();
        alert('Error: ' + error);
    }
}

async function editAlumno(matricula) {
    const response = await fetch(`${API_URL}/${matricula}`);
    const alumno = await response.json();
    document.getElementById('alumnoMatricula').value = alumno.matricula;
    document.getElementById('matricula').value = alumno.matricula;
    document.getElementById('nombreCompleto').value = alumno.nombreCompleto;
    document.getElementById('carrera').value = alumno.carrera;
    document.getElementById('password').value = alumno.password; // password visible (optional)
    document.getElementById('formTitle').innerText = 'Editar Alumno';
    document.getElementById('cancelEdit').classList.remove('hidden');
}

function cancelEdit() {
    document.getElementById('alumnoForm').reset();
    document.getElementById('alumnoMatricula').value = '';
    document.getElementById('formTitle').innerText = 'Registrar Nuevo Alumno';
    document.getElementById('cancelEdit').classList.add('hidden');
}

async function deleteAlumno(matricula) {
    if (confirm('¿Eliminar este alumno? Se eliminarán también sus préstamos y asistencias (si la BD no lo impide).')) {
        const response = await fetch(`${API_URL}/${matricula}`, { method: 'DELETE' });
        if (response.ok) loadAlumnos();
        else alert('Error al eliminar (puede tener registros asociados)');
    }
}