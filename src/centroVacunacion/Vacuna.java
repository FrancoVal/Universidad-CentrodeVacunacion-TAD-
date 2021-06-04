package centroVacunacion;

abstract class Vacuna {
	String nombre;
	int cantidad, grados;
	boolean esParaMayores60;
	
	@Override
	public String toString() {
		return "Vacuna = " + nombre.toString()+"\n";
	}

	Vacuna(String nombre, int grados, boolean esParaMayores60) {
		this.nombre = nombre;
		this.grados = grados;
		this.esParaMayores60=esParaMayores60;
	}
}

class AstraZeneca extends Vacuna {
	AstraZeneca() {
		super("AstraZeneca", 3,false);
	}

}

class Sputnik extends Vacuna {
	Sputnik() {
		super("Sputnik", 3,true);
	}

}

class Moderna extends Vacuna {
	Moderna() {
		super("Moderna", -18,false);
	}
}

class Sinopharm extends Vacuna {
	Sinopharm() {
		super("Sinopharm", 3,false);
	}
}

class Pfizer extends Vacuna {
	Pfizer() {
		super("Pfizer", -18,true);
	}
}