package carpincha.aCore.valueObject;

public enum CategoryType {

    WISDOM( "#BD10E0", "Carpincho"),  // Morado- sabiduría
    WELLBEING( "#7ED321", "Yacaré"),// Verde - bienestar
    ENERGY("#F5A623", "Jaguar"),// Naranja - energía
    SELF_CARE("#4A90E2", "Tatú"),// Azul - autocuidado
    OTHER("#9B9B9B", "Coatí");// Gris - genérico

    private final String colorHex;
    private final String animalName;

    CategoryType(String colorHex, String animalName) {
        this.colorHex = colorHex;
        this.animalName = animalName;
    }
}
