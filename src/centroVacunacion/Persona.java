package centroVacunacion;

public class Persona {

	boolean personaTieneEnfermedad;
	boolean personaTrabajaSalud;
	Integer DNI;
	private Fecha fechaNacimiento;

	public Persona(Integer DNI, Fecha fechaNacimiento, boolean personaTieneEnfermedad, boolean personaTrabajaSalud) {
		if (Fecha.diferenciaAnios(Fecha.hoy(), fechaNacimiento) >= 18) {
			this.DNI = DNI;
			this.fechaNacimiento = fechaNacimiento;
			this.personaTieneEnfermedad = personaTieneEnfermedad;
			this.personaTrabajaSalud = personaTrabajaSalud;
		} else {
			throw new RuntimeException("La persona es menor de 18 años e inadmisible para vacunar.");
		}
	}

	public boolean esMayor18() {
		return (Fecha.diferenciaAnios(Fecha.hoy(), fechaNacimiento) >= 18);
	}

	public boolean esMayor60() {
		return (Fecha.diferenciaAnios(Fecha.hoy(), fechaNacimiento) >= 60);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DNI.toString();
	}
}
