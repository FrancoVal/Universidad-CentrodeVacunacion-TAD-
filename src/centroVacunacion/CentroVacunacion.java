package centroVacunacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CentroVacunacion {
	/*
	 * IREP: cantidadTurnosRealizados <=cantidadVacunasTotales. En listaDeEspera no
	 * puede estar la misma persona que en la lista vacunados.per
	 */

	int capacidadDiariaVacunacion;
	String nombreCentro;

	List<Turno> listadoTurnos = new ArrayList<Turno>();
	List<Persona> listaDeEspera = new ArrayList<Persona>();
	List<Cargamento> cargamentoVacunas = new ArrayList<Cargamento>();
	List<Turno> vacunados = new ArrayList<Turno>();

	Map<Vacuna, Integer> vacunasVencidas = new HashMap<Vacuna, Integer>();

	int cantidadTurnosRealizados = 0;
	int cantidadVacunasTotales = 0;

	public CentroVacunacion(String nombreCentro, int capacidadDiariaVacunacion) {
		this.nombreCentro = nombreCentro;
		this.capacidadDiariaVacunacion = capacidadDiariaVacunacion;
	}

	/*
	 * Método encargado de ingresar vacunas al sistema como "Cargamentos" y de
	 * agregar al contador total de vacunas la cantidad ingresada. Los cargamentos
	 * ingresados se depositan en una lista.
	 */

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

	// Método encargado de agregar al listado de espera nuevas personas.
	public void inscribirPersona(Integer DNI, Fecha fecha, boolean personaTieneEnfermedad,
			boolean personaTrabajaSalud) {
		Persona per = new Persona(DNI, fecha, personaTieneEnfermedad, personaTrabajaSalud);
		if (per.personaTrabajaSalud) {
			listaDeEspera.add(per);
		} else if (per.esMayor60()) {
			listaDeEspera.add(per);
		} else if (per.personaTieneEnfermedad) {
			listaDeEspera.add(per);
		} else
			listaDeEspera.add(per);
	}

	/*
	 * Método encargado de revisar si la fecha de vencimiento del cargamento de
	 * vacunas "X" comparada contra la fecha pasada por parámetro da que estas se
	 * vencieron o no. Si lo hicieron, llamamos al método "seVencio()" que actualiza
	 * su booleano a true.
	 */
	public void revisarVencimiento(Fecha fecha) {
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.fechaVencimiento != null) {
				if (carg.fechaVencimiento.compareTo(fecha) > 0) {
					carg.seVencio();
				}
			}
		}
	}

	/*
	 * Método encargado de primero llamar al método "revisarVencimiento(fecha)" para
	 * ver qué vacunas se vencieron. Luego recorremos el listado de cargamentos y
	 * preguntamos cuales están vencidas para poder almacenarlas en otro listado de
	 * cargamentos vencidos para luego borrarle al cargamento "principal" aquellos
	 * cargamentos vencidos. Además, ponemos en un mapa de vacunas vencidas aquellas
	 * vacunas vencidas con su determinada cantidad.Además, removemos del total de
	 * vacunas la cantidad vencida.
	 * 
	 */
	public void quitarVacunasVencidas(Fecha fecha) {
		revisarVencimiento(fecha);
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

	// Devuelve la cantidad total de vacunas.
	public int vacunasDisponibles() {
		return cantidadVacunasTotales;
	}

	/*
	 * Este método recorre el listado de cargamentos preguntando por aquella que se
	 * llame igual que el nombre pasado por parámetro, si se llama asi sumamos su
	 * cantidad a una variable local y la devolvemos.
	 */

	public int vacunasDisponibles(String nombreVacuna) {
		int cantidadVacunas = 0;
		for (Cargamento carg : cargamentoVacunas) {
			if (carg.vacuna.nombre.equals(nombreVacuna)) {
				cantidadVacunas += carg.cantidad;
			}
		}
		return cantidadVacunas;
	}

	// Remueve del listado de turnos a aquellos que no se presentaron, devolvemos la
	// vacuna al total.
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

	// Método encargado de generar turnos.
	public void generarTurnos(Fecha fecha) {
		generarTurnos(listaDeEspera, fecha, cantidadTurnosRealizados);
	}

	/*
	 * Este método se encarga primero de llamar a dos métodos: quitarVacunasVencidas
	 * y quitarTurnosVencidos para asi no asignar una vacuna vencida y no tener en
	 * el listado de turnos a aquellos que ya se vencieron. Luego preguntamos si la
	 * fecha a la que se va a asignar dicho turno es correcta. Despues de conseguir
	 * la vacuna determinada para dicha persona con getVacuna, nos fijamos que esa
	 * no sea null y si no nos excedimos de la cantidad total de vacunacion del
	 * centro por dia, para luego crear un nuevo turno con esa persona, esa fecha y
	 * la vacuna que se le asignó. Sumamos a la cantidad global de turnos
	 * realizados, lo agregamos al listado de turnos y restamos de la cantidad total
	 * de vacunas.
	 */
	private void generarTurnos(List<Persona> listaDeEspera, Fecha fecha, int cantidadTurnosRealizados) {
		quitarVacunasVencidas(fecha);
		quitarTurnosVencidos(fecha);
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

	// Método encargado de quitar la gente que ya tiene turno de la lista de espera.
	public void eliminarPersonasEnEspera() {
		for (Turno tur : listadoTurnos) {
			this.listaDeEspera.remove(tur.persona);
		}
	}

	/*
	 * Devuelve una vacuna X determinada segun si la persona es mayor y si la vacuna
	 * es para mayores. En otro caso devolvemos otra vacuna.
	 */

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

	// Nos fijamos que la persona del listado de turnos tenga bien pasado por
	// parametro el dni, sino devuelvo null.
	public Turno buscarTurnoONull(Integer DNIAVacunar) {
		for (Turno tur : listadoTurnos) {
			if (tur.persona.DNI.equals(DNIAVacunar)) {
				return tur;
			}
		}
		// Si no lo encuentro, devuelvo null
		return null;
	}

	/*
	 * Preguntamos que el turno no sea null, que la fecha está bien o devolvemos la
	 * vacuna la total, y si no te vacunamos, te sacamos del listado de turnos y te
	 * agregamos al de vacunados.
	 * 
	 */
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

	// Método encargado de llamar a verificarDatos para vacunar al inscrito.
	public void vacunarInscripto(Integer DNIAVacunar, Fecha fechaDada) {
		verificarDatos(DNIAVacunar, fechaDada);
	}

	// Devuelvo un mapa creado por las personas del listado de vacunados con su dni
	// y su vacuna.
	public Map<Integer, Vacuna> reporteVacunacion() {
		Map<Integer, Vacuna> listadoVacunados = new HashMap<Integer, Vacuna>();
		for (Turno turno : vacunados) {
			listadoVacunados.put(turno.persona.DNI, turno.vacuna);
		}
		return listadoVacunados;
	}

	// Devolvemos una lista con turnos para la fecha pasada por parametro
	// unicamente.
	public List<Turno> turnosConFecha(Fecha fecha) {
		List<Turno> turnosConFecha = new ArrayList<Turno>();
		for (Turno tur : listadoTurnos) {
			if (tur.fecha.equals(fecha)) {
				turnosConFecha.add(tur);
			}
		}
		return turnosConFecha;
	}

	// Devuelvo una lista hecha por los dni de las personas en la lista de espera.
	public List<Integer> listaDeEspera() {
		List<Integer> listaDeEsperaPedida = new ArrayList<Integer>();
		for (Persona per : listaDeEspera) {
			listaDeEsperaPedida.add(per.DNI);
		}
		return listaDeEsperaPedida;
	}

	// Devuelvo un mapa con el nombre de la vacuna y su cantidad del mapa de vacunas
	// vencidas.
	public Map<String, Integer> reporteVacunasVencidas() {
		Map<String, Integer> vacVencidas = new HashMap<String, Integer>();
		for (Vacuna vacuna : vacunasVencidas.keySet()) {
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