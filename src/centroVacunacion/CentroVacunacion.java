package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CentroVacunacion {
	int capacidadDiariaVacunacion, cantidadVacunasTotales;
	String nombreCentro;

	// List<Persona> listaDeEsperaTrabajadoresSalud = new ArrayList<Persona>();
	// List<Persona> listaDeEsperaEnfermedades = new ArrayList<Persona>();
	// List<Persona> listaDeEsperaEstandar = new ArrayList<Persona>();
	List<Turno> listadoTurnos = new ArrayList<Turno>();
	// List<Vacuna> fechasVacunas = new ArrayList<Vacuna>();
	HashMap<Integer, Integer> vacunados = new HashMap<Integer, Integer>();
	List<Persona> listaDeEspera = new ArrayList<Persona>();
	// HashMap<Tupla<Vacuna, Fecha>,Integer>vacunasTotales=new HashMap<Tupla<Vacuna,
	// Fecha>, Integer>();
	Map<Vacuna, Integer> vacunasTotales = new HashMap<Vacuna, Integer>();
	// Auxiliares para los métodos encargados a la asignación de turnos.
	Fecha fecha;
	int cantidadTurnosRealizados = 0;
	int cantidadSputnik = 0;
	int cantidadSinopharm = 0;
	int cantidadPfizer = 0;
	int cantidadModerna = 0;
	int cantidadAstraZeneca = 0;

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
				this.cantidadVacunasTotales += cantidadVacuna;
				this.cantidadSputnik += cantidadVacuna;
			}
			if (nombreVacuna.equals("Pfizer")) {
				vacunasTotales.put(new Pfizer(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
				this.cantidadPfizer += cantidadVacuna;
			}
			if (nombreVacuna.equals("Moderna")) {
				vacunasTotales.put(new Moderna(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
				this.cantidadModerna += cantidadVacuna;
			}
			if (nombreVacuna.equals("Sinopharm")) {
				vacunasTotales.put(new Sinopharm(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
				this.cantidadSinopharm += cantidadVacuna;
			}
			if (nombreVacuna.equals("AstraZeneca")) {
				vacunasTotales.put(new AstraZeneca(), cantidadVacuna);
				this.cantidadVacunasTotales += cantidadVacuna;
				this.cantidadAstraZeneca += cantidadVacuna;
			}
		}
	}

	public int vacunasDisponibles() {
		return this.cantidadVacunasTotales;
	}

	public int vacunasDisponibles2(String nombreVacuna) {
		if (nombreVacuna.equals("Sputnik")) {
			return this.cantidadSputnik;
		} else if (nombreVacuna.equals("Sinopharm")) {
			return this.cantidadSinopharm;
		} else if (nombreVacuna.equals("Moderna")) {
			return this.cantidadModerna;
		} else if (nombreVacuna.equals("Pfizer")) {
			return this.cantidadPfizer;
		} else {
			return this.cantidadAstraZeneca;
		}
	}
	
	public int vacunasDisponibles3(String nombreVacuna) {
		int cantidadVVacunas=0;
		for (Vacuna vac : vacunasTotales.keySet()) {
			if (vac.nombre.equals(nombreVacuna)) {
				cantidadVVacunas+=vacunasTotales.get(vac);
			}
		}
		return cantidadVVacunas;
	}


	public void inscribirPersona(Integer DNI, Fecha fecha, boolean personaTieneEnfermedad,
			boolean personaTrabajaSalud) {
		// Persona persona = new Persona(DNI, fecha, personaTieneEnfermedad,
		// personaTrabajaSalud);
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
		// generarTurnos(listaDeEsperaEnfermedades, fecha, cantidadTurnosRealizados);
		// generarTurnos(listaDeEsperaEstandar, fecha, cantidadTurnosRealizados);
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
//REHACER
	/*
	 * public void eliminarPersonasEnEspera() { for (Turno tur : listadoTurnos) {
	 * this.listaDeEsperaEnfermedades.remove(tur.persona);
	 * this.listaDeEsperaEstandar.remove(tur.persona);
	 * this.listaDeEsperaTrabajadoresSalud.remove(tur.persona); } }
	 */

	public List<Turno> obtenerPersonasDeFecha(Fecha fecha) {
		List<Turno> turnosDichoDia = new ArrayList<Turno>();
		for (Turno turno : listadoTurnos) {
			if (turno.fecha == (fecha)) {
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

	public void vacunarInscripto(Integer DNI, Fecha fecha) {
		for (Persona per : listaDeEspera()) {
			if (per.DNI == DNI) {

			}
		}
		if (listaDeEspera.contains(DNI) && listadoTurnos.contains(fecha))

			--this.cantidadVacunasTotales;

	}
	/*
	 * si no asistio debo actualizar stock de vacunas, sacar de la lista de espera
	 * correspondiete relacionarlo con que si NO esta agregado al hashmap/lista de
	 * ya vacunados con esa misma fecha?? tambien puedo poner un fecha.siguiente o
	 * algo asi + un dia o compararlo asi para saber si paso un dia y no se
	 * vacuno/agregado a la lista de vacunados(fecha)
	 */

	public List<Turno> generarReporteVacunados() {
		List<Turno> listadoVacunados = new ArrayList<Turno>();
		for (Turno turno : listadoTurnos) {
			if (turno.personaVacunada) {
				listadoVacunados.add(turno);
			}
		}
		return listadoVacunados;
	}

	/*
	 * public List<Persona> listaDeEspera() { List<Persona> listaEsperaConjunto =
	 * new ArrayList<Persona>();
	 * listaEsperaConjunto.addAll(listaDeEsperaTrabajadoresSalud);
	 * listaEsperaConjunto.addAll(listaDeEsperaEstandar);
	 * listaEsperaConjunto.addAll(listaDeEsperaEnfermedades); return
	 * listaEsperaConjunto; }
	 */

	public List<Persona> turnosConFecha(Fecha fechaInicial) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<Vacuna, Persona> reporteVacunacion() {
		return null;
	}

	public Object vacunasDisponibles(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Persona> reporteVacunasVencidas() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Persona> listaDeEspera() {
		return listaDeEspera;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Tu centro vacunatorio designado es: " + nombreCentro.toString();
	}
}