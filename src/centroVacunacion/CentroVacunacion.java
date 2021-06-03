package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CentroVacunacion {
	int capacidadDiariaVacunacion, cantidadVacunasTotales;
	String nombreCentro;

	List<Turno> listadoTurnos = new ArrayList<Turno>();
	List<Persona> listaDeEspera = new ArrayList<Persona>();

	Map<Integer, Integer> vacunados = new HashMap<Integer, Integer>();
	Map<Vacuna, Integer> vacunasTotales = new HashMap<Vacuna, Integer>();

	Fecha fecha;
	Fecha fechaHoy = new Fecha(2,6,2021);
	int cantidadTurnosRealizados = 0;

	public CentroVacunacion(String nombreCentro, int capacidadDiariaVacunacion) {
		this.nombreCentro = nombreCentro;
		this.capacidadDiariaVacunacion = capacidadDiariaVacunacion;
	}

	// Doubtful
	public void ingresarVacunas(String nombreVacuna, int cantidadVacuna, Fecha fechaIngreso) {
		if (cantidadVacuna < 0) {
			throw new RuntimeException("No se están agregando vacunas.");
		} else {
			if (nombreVacuna.equals("Sputnik")) {
				vacunasTotales.put(new Sputnik(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("Pfizer")) {
				vacunasTotales.put(new Pfizer(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("Moderna")) {
				vacunasTotales.put(new Moderna(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("Sinopharm")) {
				vacunasTotales.put(new Sinopharm(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("AstraZeneca")) {
				vacunasTotales.put(new AstraZeneca(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
			} else {
				throw new RuntimeException("El nombre de la vacuna es incorrecto.");
			}
		}
	}
	
	public int vacunasDisponibles() {
		return this.cantidadVacunasTotales;
	}

	public int vacunasDisponibles(String nombreVacuna) {
		int cantidadVacunas = 0;
		for (Vacuna vac : vacunasTotales.keySet()) {
			if (vac.nombre.equals(nombreVacuna)) {
				cantidadVacunas += vacunasTotales.get(vac);
			}
		}
		return cantidadVacunas;
	}

	public void inscribirPersona(Integer DNI, Fecha fecha, boolean personaTieneEnfermedad,
			boolean personaTrabajaSalud) {
		if (personaTrabajaSalud) {
			listaDeEspera.add(new Persona(DNI, fecha, personaTieneEnfermedad, personaTrabajaSalud));
		} else if (personaTieneEnfermedad) {
			listaDeEspera.add(new Persona(DNI, fecha, personaTieneEnfermedad, personaTrabajaSalud));
		} else {
			listaDeEspera.add(new Persona(DNI, fecha, personaTieneEnfermedad, personaTrabajaSalud));
		}
	}

	public void generarTurnos(Fecha fecha) {
		generarTurnos(listaDeEspera, fecha, cantidadTurnosRealizados);
	}

	// Preguntar Germán
	private void generarTurnos(List<Persona> listaDeEspera, Fecha fecha, int cantidadTurnosRealizados) {
		for (Persona per : listaDeEspera) {
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

	public void vacunarInscripto(Integer DNI, Fecha fecha) {
		for (Turno tur : listadoTurnos) {
			if (tur.persona.DNI.equals(DNI) && tur.persona.fecha.equals(fecha)) {
				tur.seVacuno();
			}
		}
	}
	/*
	 * si no asistio debo actualizar stock de vacunas, sacar de la lista de espera
	 * correspondiete relacionarlo con que si NO esta agregado al hashmap/lista de
	 * ya vacunados con esa misma fecha?? tambien puedo poner un fecha.siguiente o
	 * algo asi + un dia o compararlo asi para saber si paso un dia y no se
	 * vacuno/agregado a la lista de vacunados(fecha)
	 */

	public Map<Integer, Vacuna> reporteVacunacion() {
		Map<Integer, Vacuna> listadoVacunados = new HashMap<Integer, Vacuna>();
		for (Turno turno : listadoTurnos) {
			if (turno.personaVacunada) {
				listadoVacunados.put(turno.persona.DNI, turno.vacuna);
			}
		}
		return listadoVacunados;
	}

	public List<Turno> turnosConFecha(Fecha fecha) {
		List<Turno> turnosConFecha = new ArrayList<Turno>();
		for (Turno tur : listadoTurnos) {
			if (tur.fecha.equals(fecha)) {
				turnosConFecha.add(tur);
			}
		}
		return turnosConFecha;
	}

	public List<Integer> listaDeEspera() {
		List<Integer> listaDeEsperaPedida = new ArrayList<Integer>();
		for (Persona per : listaDeEspera) {
			listaDeEsperaPedida.add(per.DNI);
		}
		return listaDeEsperaPedida;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Tu centro vacunatorio designado es: " + nombreCentro.toString();
	}
}