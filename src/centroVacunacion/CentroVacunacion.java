package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CentroVacunacion {
	int capacidadDiariaVacunacion;
	String nombreCentro;

	List<Turno> listadoTurnos = new ArrayList<Turno>();
	List<Persona> listaDeEspera = new ArrayList<Persona>();

	Map<Integer, Integer> vacunados = new HashMap<Integer, Integer>();
	Map<Vacuna, Integer> vacunasVencidas = new HashMap<Vacuna, Integer>();

	List<Cargamento> cargamentoVacunas = new ArrayList<Cargamento>();

	Cargamento cargamento;
	Fecha fecha;
	Fecha fechaHoy = new Fecha(2, 6, 2021);
	int cantidadTurnosRealizados = 0;

	public CentroVacunacion(String nombreCentro, int capacidadDiariaVacunacion) {
		this.nombreCentro = nombreCentro;
		this.capacidadDiariaVacunacion = capacidadDiariaVacunacion;
	}

	// Doubtful
	public void ingresarVacunas(String nombreVacuna, int cantidadVacuna, Fecha fechaIngreso) {
		if (cantidadVacuna <= 0) {
			throw new RuntimeException("No se están agregando vacunas.");
		} else {
			if (nombreVacuna.equals("Sputnik")) {
				cargamentoVacunas.add(new Cargamento(new Sputnik(), cantidadVacuna, fechaIngreso, false));
			} else if (nombreVacuna.equals("Pfizer")) {
				cargamentoVacunas.add(new Cargamento(new Pfizer(), cantidadVacuna, fechaIngreso, false));
			} else if (nombreVacuna.equals("Moderna")) {
				cargamentoVacunas.add(new Cargamento(new Moderna(), cantidadVacuna, fechaIngreso, false));
			} else if (nombreVacuna.equals("Sinopharm")) {
				cargamentoVacunas.add(new Cargamento(new Sinopharm(), cantidadVacuna, fechaIngreso, false));
			} else if (nombreVacuna.equals("AstraZeneca")) {
				cargamentoVacunas.add(new Cargamento(new AstraZeneca(), cantidadVacuna, fechaIngreso, false));
			} else {
				throw new RuntimeException("El nombre de la vacuna es incorrecto.");
			}
		}
	}

	public void revisarVencimiento() {
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.vacuna.nombre == "Pfizer") {
				if (carg.fechaIngreso.compareTo(fechaHoy) == 1) {
					carg.seVencio();
				}
			} else if (carg.vacuna.nombre == "Moderna") {
				if (carg.fechaIngreso.compareTo(fechaHoy) == 2) {
					carg.seVencio();
				}
			}
		}
	}

	public void quitarVencidas() {
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.estanVencidas) {
				cargamentoVacunas.remove(carg);
				vacunasVencidas.put(carg.vacuna, carg.cantidad);
			}
		}
	}

	public int vacunasDisponibles() {
		revisarVencimiento();
		quitarVencidas();
		return cargamentoVacunas.size();
	}

	public int vacunasDisponibles(String nombreVacuna) {
		revisarVencimiento();
		quitarVencidas();
		int cantidadVacunas = 0;
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.vacuna.nombre.equals(nombreVacuna)) {
				cantidadVacunas += cargamentoVacunas.size();
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
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.cantidad > 0) {
				if (persona.esMayor60() && carg.vacuna.esParaMayores60) {
					return carg.vacuna;
				} else {
					return carg.vacuna;
				}
			}
		}
		// Si no lo puedo vacunar por no tener mas vacunas, null
		return null;
	}

	public void vacunarInscripto(Integer DNI, Fecha fechaDada) {
		for (Turno tur : listadoTurnos) {
			//Si ya estabas vacunado tiramos una excepcion.
			if (!tur.personaVacunada) {
				if (tur.persona.DNI.equals(DNI) && tur.fecha.equals(fechaDada)) {
					tur.seVacuno();
				}
				/*
				 * Si tu fecha dada es anterior a la que preguntan es porque ya se te pasó.
				 * 21/02/2021 < 22/02/2021
				 */
				else if (tur.fecha.anterior(fechaDada)) {
					ingresarVacunas(tur.vacuna.nombre, tur.vacuna.cantidad, tur.fecha);
				}
			} else {
				throw new RuntimeException("La persona ya está vacunada.");
			}
		}
	}

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
	
	public List<Vacuna> vacunasVencidas(){
		return vacunasVencidas();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Tu centro vacunatorio designado es: " + nombreCentro.toString();
	}
}