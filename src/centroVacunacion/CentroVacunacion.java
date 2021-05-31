package centroVacunacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CentroVacunacion {
	int capacidadDiariaVacunacion,cantidadVacunasTotales;
	String nombreCentro;

	List<Persona> listaDeEsperaTrabajadoresSalud = new ArrayList<Persona>();
	List<Persona> listaDeEsperaEnfermedades = new ArrayList<Persona>();
	List<Persona> listaDeEsperaEstandar = new ArrayList<Persona>();
	List<Turno> listadoTurnos = new ArrayList<Turno>();
	List<Vacuna> fechasVacunas = new ArrayList<Vacuna>();

	Map<Vacuna, Integer> vacunasTotales = new HashMap<Vacuna, Integer>();
	// Auxiliares para los métodos encargados a la asignación de turnos.
	Fecha fecha;
	int cantidadTurnosRealizados = 0;

	public CentroVacunacion(String nombreCentro, int capacidadDiariaVacunacion) {
		this.nombreCentro = nombreCentro;
		this.capacidadDiariaVacunacion = capacidadDiariaVacunacion;
	}

	// Doubtful
	public void ingresarVacunas(String nombreVacuna, int cantidadVacuna, Fecha fecha) {
		if (cantidadVacuna < 0) {
			throw new RuntimeException("No se están agregando vacunas.");
		} else {
			if (nombreVacuna.equals("Sputnik")) {
				vacunasTotales.put(new Sputnik(), cantidadVacuna);
				this.cantidadVacunasTotales+=cantidadVacuna;
			}
			if (nombreVacuna.equals("Pfizer")) {
				vacunasTotales.put(new Pfizer(), cantidadVacuna);
				this.cantidadVacunasTotales+=cantidadVacuna;
			}
			if (nombreVacuna.equals("Moderna")) {
				vacunasTotales.put(new Moderna(), cantidadVacuna);
				this.cantidadVacunasTotales+=cantidadVacuna;
			}
			if (nombreVacuna.equals("Sinopharm")) {
				vacunasTotales.put(new Sinopharm(), cantidadVacuna);
				this.cantidadVacunasTotales+=cantidadVacuna;
			}
			if (nombreVacuna.equals("AstraZeneca")) {
				vacunasTotales.put(new AstraZeneca(), cantidadVacuna);
				this.cantidadVacunasTotales+=cantidadVacuna;
			}
		}
	}

	public int vacunasDisponibles() {
		return cantidadVacunasTotales;
	}

	public void inscribirPersona(Integer DNI, Fecha fecha, boolean personaTieneEnfermedad,
			boolean personaTrabajaSalud) {
		Persona persona = new Persona(DNI, fecha, personaTieneEnfermedad, personaTrabajaSalud);
		if (personaTrabajaSalud) {
			listaDeEsperaTrabajadoresSalud.add(persona);
		} else if (personaTieneEnfermedad) {
			listaDeEsperaEnfermedades.add(persona);
		} else {
			listaDeEsperaEstandar.add(persona);
		}
	}

	public void generarTurnos(Fecha fecha) {
		generarTurnos(listaDeEsperaTrabajadoresSalud, fecha, cantidadTurnosRealizados);
		generarTurnos(listaDeEsperaEnfermedades, fecha, cantidadTurnosRealizados);
		generarTurnos(listaDeEsperaEstandar, fecha, cantidadTurnosRealizados);
	}

	// Preguntar Germán
	public void generarTurnos(List<Persona> personas, Fecha fecha, int cantidadTurnosRealizados) {
		for (Persona per : personas) {
			Vacuna vac = getVacuna(per);
			if (vac != null) {
				if (capacidadDiariaVacunacion <= cantidadTurnosRealizados) {
					Turno tur = new Turno(per, vac, fecha);
					this.cantidadTurnosRealizados++;
					listadoTurnos.add(tur);
				} else {
					fecha.avanzarUnDia();
					Turno turB = new Turno(per, vac, fecha);
					this.cantidadTurnosRealizados++;
					listadoTurnos.add(turB);
				}
			}
		}
		eliminarPersonasEnEspera();
	}

	public void eliminarPersonasEnEspera() {
		for (Turno tur : listadoTurnos) {
			this.listaDeEsperaEnfermedades.remove(tur.persona);
			this.listaDeEsperaEstandar.remove(tur.persona);
			this.listaDeEsperaTrabajadoresSalud.remove(tur.persona);
		}
	}

	public List<Turno> obtenerPersonasDeFecha(Fecha fecha) {
		List<Turno> turnosDichoDia = new ArrayList<Turno>();
		for (Turno turno : listadoTurnos) {
			if (turno.fecha.equals(fecha)) {
				turnosDichoDia.add(turno);
			}
		}
		return turnosDichoDia;
	}

	public Vacuna getVacuna(Persona persona) {
		for (Vacuna vac : vacunasTotales.keySet()) {
			if (vacunasTotales.get(vac) > 0) {
				return vac;
			}
		}
		// Si no lo puedo vacunar por no tener mas vacunas, null
		return null;
	}

	// TERMINALO PAYASO
	public void vacunarInscripto(Integer DNI, Fecha fecha) {
	}

	public List<Turno> generarReporteVacunados() {
		List<Turno> listadoVacunados = new ArrayList<Turno>();
		for (Turno turno : listadoTurnos) {
			if (turno.personaVacunada) {
				listadoVacunados.add(turno);
			}
		}
		return listadoVacunados;
	}

	public List<Persona> listaDeEspera() {
		List<Persona> listaEsperaConjunto = new ArrayList<Persona>();
		listaEsperaConjunto.addAll(listaDeEsperaTrabajadoresSalud);
		listaEsperaConjunto.addAll(listaDeEsperaEstandar);
		listaEsperaConjunto.addAll(listaDeEsperaEnfermedades);
		return listaEsperaConjunto;
	}

}