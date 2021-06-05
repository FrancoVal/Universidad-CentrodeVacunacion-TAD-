package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class CentroVacunacion {
	int capacidadDiariaVacunacion;
	String nombreCentro;

	List<Turno> listadoTurnos = new ArrayList<Turno>();
	List<Persona> listaDeEspera = new ArrayList<Persona>();

<<<<<<< HEAD
	List<Turno> vacunados = new ArrayList<Turno>();
	Map<Vacuna, Integer> vacunasVencidas = new HashMap<Vacuna, Integer>();

=======
	Map<Integer, Integer> vacunados = new HashMap<Integer, Integer>();
	Map<Vacuna, Integer> vacunasVencidas = new HashMap<Vacuna, Integer>(); 
	
>>>>>>> d568e250c742c4cb8774cfe776a5879315692a52
	List<Cargamento> cargamentoVacunas = new ArrayList<Cargamento>();

	int cantidadTurnosRealizados = 0;
	int cantidadVacunasTotales = 0;

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
				cargamentoVacunas.add(new Cargamento(new Sputnik(), cantidadVacuna, fechaIngreso));
				cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("Pfizer")) {
				cargamentoVacunas.add(new Cargamento(new Pfizer(), cantidadVacuna, fechaIngreso));
				cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("Moderna")) {
				cargamentoVacunas.add(new Cargamento(new Moderna(), cantidadVacuna, fechaIngreso));
				cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("Sinopharm")) {
				cargamentoVacunas.add(new Cargamento(new Sinopharm(), cantidadVacuna, fechaIngreso));
				cantidadVacunasTotales += cantidadVacuna;
			} else if (nombreVacuna.equals("AstraZeneca")) {
				cargamentoVacunas.add(new Cargamento(new AstraZeneca(), cantidadVacuna, fechaIngreso));
				cantidadVacunasTotales += cantidadVacuna;
			} else {
				throw new RuntimeException("El nombre de la vacuna es incorrecto.");
			}
		}
	}

	public void revisarVencimiento(Fecha fecha) {
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.fechaVencimiento != null) {
				if (carg.fechaVencimiento.compareTo(fecha) > 0) {
					carg.seVencio();
				}
			}
		}
	}
<<<<<<< HEAD

	public void quitarVacunasVencidas(Fecha fecha) {
		revisarVencimiento(fecha);
=======
 
	public void quitarVencidas() {
		revisarVencimiento();
>>>>>>> d568e250c742c4cb8774cfe776a5879315692a52
		List<Cargamento> cargamentoVacunasVencidas = new ArrayList<Cargamento>();
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.estanVencidas) {
				cargamentoVacunasVencidas.add(carg);
				vacunasVencidas.put(carg.vacuna, carg.cantidad);
				cantidadVacunasTotales -= carg.cantidad;
			}
		}
		cargamentoVacunas.removeAll(cargamentoVacunasVencidas);
	}

	public int vacunasDisponibles() {
		return cantidadVacunasTotales;
	}

	public int vacunasDisponibles(String nombreVacuna) {
		int cantidadVacunas = 0;
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.vacuna.nombre == nombreVacuna) {
				cantidadVacunas += carg.cantidad;
			}
		}
		return cantidadVacunas;
	}

	public void inscribirPersona(Integer DNI, Fecha fecha, boolean personaTieneEnfermedad,
			boolean personaTrabajaSalud) {
		listaDeEspera.add(new Persona(DNI, fecha, personaTieneEnfermedad, personaTrabajaSalud));
	}

	public void generarTurnos(Fecha fecha) {
		generarTurnos(listaDeEspera, fecha, cantidadTurnosRealizados);
	}

<<<<<<< HEAD
	public void quitarTurnosVencidos(Fecha fecha) {
		List<Turno> turnosVencidos = new ArrayList<Turno>();
		for (Turno tur : listadoTurnos) {
			if (tur.fecha.compareTo(fecha) < 0) {
				turnosVencidos.add(tur);
				cargamentoVacunas.add(new Cargamento(tur.vacuna, 1, tur.fecha));
				cantidadVacunasTotales++;
			}
		}
		listadoTurnos.removeAll(turnosVencidos);
	}

	private void generarTurnos(List<Persona> listaDeEspera, Fecha fecha, int cantidadTurnosRealizados) {
		quitarVacunasVencidas(fecha);
		quitarTurnosVencidos(fecha);
=======
	private void generarTurnos(List<Persona> listaDeEspera, Fecha fecha, int cantidadTurnosRealizados) {
		quitarVencidas();
>>>>>>> d568e250c742c4cb8774cfe776a5879315692a52
		if (Fecha.hoy().compareTo(fecha) <= 0) {
			for (Persona per : listaDeEspera) {
				Vacuna vac = getVacuna(per);
				if (vac != null) {
					if (this.capacidadDiariaVacunacion > this.cantidadTurnosRealizados) {
						Turno tur = new Turno(per, vac, fecha);
						this.cantidadTurnosRealizados++;
						listadoTurnos.add(tur);
						cantidadVacunasTotales--;
					} else {
						Fecha siguienteDia = new Fecha(fecha.dia(), fecha.mes(), fecha.anio());
						siguienteDia.avanzarUnDia();
						Turno turOtro = new Turno(per, vac, siguienteDia);
						this.cantidadTurnosRealizados++;
						listadoTurnos.add(turOtro);
						cantidadVacunasTotales--;
					}
				}
			}
		} else {
			throw new RuntimeException("No se puede asignar un turno con una fecha pasada.");
		}
		eliminarPersonasEnEspera();
	}

	public void eliminarPersonasEnEspera() {
		for (Turno tur : listadoTurnos) {
			this.listaDeEspera.remove(tur.persona);
		}
	}

	public Vacuna getVacuna(Persona persona) {
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.cantidad > 0) {
				if (persona.esMayor60() && carg.vacuna.esParaMayores60) {
					return carg.vacuna;
				}
				if (!persona.esMayor60()) {
					return carg.vacuna;
				}
			}
		}
		// Si no lo puedo vacunar por no tener mas vacunas, null
		return null;
	}

<<<<<<< HEAD
	public Turno buscarTurnoONull(Integer DNIAVacunar) {
		for (Turno tur : listadoTurnos) {
			if (tur.persona.DNI.equals(DNIAVacunar)) {
				return tur;
=======
	public void verificarDatos(Integer DNIAVacunar, Fecha fechaDada) {
		for (Turno tur : listadoTurnos) {
			if (tur.persona.DNI.equals(DNIAVacunar) && tur.fecha.equals(fechaDada)) {
				tur.seVacuno();
			} else if (!tur.fecha.equals(fechaDada)) {
				cargamentoVacunas.add(new Cargamento(tur.vacuna, 1, Fecha.hoy(), false));
				cantidadVacunasTotales++;
			} else {
				cargamentoVacunas.add(new Cargamento(tur.vacuna, 1, Fecha.hoy(), false));
				cantidadVacunasTotales++;
>>>>>>> d568e250c742c4cb8774cfe776a5879315692a52
			}
		}
		// Si no lo encuentro, devuelvo null
		return null;
	}

	public void verificarDatos(Integer DNIAVacunar, Fecha fechaDada) {
		Turno tur = buscarTurnoONull(DNIAVacunar);
		if (tur == null) {
			throw new RuntimeException("El DNI es incorrecto.");
		}
		if (tur.fecha != fechaDada) {
			cargamentoVacunas.add(new Cargamento(tur.vacuna, 1, tur.fecha));
			cantidadVacunasTotales++;
			throw new RuntimeException("La fecha es incorrecta.");
		}
		tur.seVacuno();
		listadoTurnos.remove(tur);
		vacunados.add(tur);
	}

	public void vacunarInscripto(Integer DNIAVacunar, Fecha fechaDada) {
		verificarDatos(DNIAVacunar, fechaDada);
	}

	public void vacunarInscripto(Integer DNIAVacunar, Fecha fechaDada) {
			verificarDatos(DNIAVacunar, fechaDada);
	}

	public Map<Integer, Vacuna> reporteVacunacion() {
		Map<Integer, Vacuna> listadoVacunados = new HashMap<Integer, Vacuna>();
		for (Turno turno : vacunados) {
			listadoVacunados.put(turno.persona.DNI, turno.vacuna);
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
<<<<<<< HEAD

	public Map<String, Integer> reporteVacunasVencidas() {
		Map<String, Integer> vacVencidas = new HashMap<String, Integer>();
		for (Vacuna vacuna : vacunasVencidas.keySet()) {
=======
	
	public Map<String, Integer> reporteVacunasVencidas() {
		Map<String, Integer>vacVencidas=new HashMap<String, Integer>();
		for(Vacuna vacuna: vacunasVencidas.keySet()) {
>>>>>>> d568e250c742c4cb8774cfe776a5879315692a52
			vacVencidas.put(vacuna.nombre, vacunasVencidas.get(vacuna));
		}
		return vacVencidas;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Tu centro vacunatorio designado es: " + nombreCentro.toString();
	}
}