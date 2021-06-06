package centroVacunacion;

public class Persona {

	boolean personaTieneEnfermedad;
	boolean personaTrabajaSalud;
	Integer DNI;
	private Fecha fechaNacimiento;

	public Persona(Integer DNI, Fecha fechaNacimiento, boolean personaTieneEnfermedad, boolean personaTrabajaSalud) {
		if (esMayor18(fechaNacimiento)) {
			this.DNI = DNI;
			this.fechaNacimiento = fechaNacimiento;
			this.personaTieneEnfermedad = personaTieneEnfermedad;
			this.personaTrabajaSalud = personaTrabajaSalud;
		} else {
			throw new RuntimeException("La persona es menor de 18 años e inadmisible para vacunar.");
		}
	}

	//Método utilizado para ver con la fecha de nacimiento de la persona si esta es mayor a 18.
	public boolean esMayor18(Fecha fechaNacimiento) {
		return (Fecha.diferenciaAnios(Fecha.hoy(), fechaNacimiento) >= 18);
	}
	
	//Método utilizado para ver con la fecha de nacimiento de la persona si esta es mayor a 60.
	public boolean esMayor60() {
		return (Fecha.diferenciaAnios(Fecha.hoy(), fechaNacimiento) >= 60);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DNI.toString();
	}
}
