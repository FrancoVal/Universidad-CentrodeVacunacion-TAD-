package centroVacunacion;

public class Persona {

	boolean personaTieneEnfermedad;
	boolean personaTrabajaSalud;
	Integer DNI;
	Fecha fechaNacimiento;
	Fecha fechaHoy;

	public Persona(Integer DNI, Fecha fechaNacimiento, boolean personaTieneEnfermedad, boolean personaTrabajaSalud) {
		if (esMayor18()) {
			this.DNI = DNI;
			this.fechaNacimiento = fechaNacimiento;
			this.personaTieneEnfermedad = personaTieneEnfermedad;
			this.personaTrabajaSalud = personaTrabajaSalud;
		} else {
			throw new RuntimeException("La persona es menor de 18 años e inadmisible para vacunar.");
		}
	}
	
	public boolean esMayor18() {
		if (Fecha.diferenciaAnios(fechaHoy, fechaNacimiento) >= 18) {
			return true;
		}
		return false;
	}

	public boolean esMayor60() {
		if (Fecha.diferenciaAnios(fechaHoy, fechaNacimiento) >= 60) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return DNI.toString();
	}
}
