package centroVacunacion;

abstract class Vacuna {
	String nombre;
	int cantidad, grados;

	@Override
	public String toString() {
		return "Vacuna = " + nombre.toString()+"\n";
	}

	Vacuna(String nombre, int grados) {
		this.nombre = nombre;
		this.grados = grados;
	}
}

class AstraZeneca extends Vacuna {
	AstraZeneca() {
		super("AstraZeneca", 3);
	}

}

class Sputnik extends Vacuna {
	Sputnik() {
		super("Sputnik", 3);
	}

}

class Moderna extends Vacuna {
	Moderna() {
		super("Moderna", -18);
	}
}

class Sinopharm extends Vacuna {
	Sinopharm() {
		super("Sinopharm", 3);
	}
}

class Pfizer extends Vacuna {
	Pfizer() {
		super("Pfizer", -18);
	}
}