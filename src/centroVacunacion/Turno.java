package centroVacunacion;

class Turno {
	Persona persona;
	Vacuna vacuna;
	Fecha fecha;
	boolean personaVacunada;

	public Turno(Persona persona, Vacuna vacuna, Fecha fecha) {
		this.persona = persona;
		this.vacuna = vacuna;
		this.fecha = fecha;
		personaVacunada = false;
	}

	public void seVacuno() {
		personaVacunada = true;
	}
	
	@Override
	public String toString() {
		return "{"+persona.toString()+"}"+"{"+ fecha.toString()+"}"+"{"+vacuna.toString()+"}";
		
	}
}