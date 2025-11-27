package carpincha.aCore.valueObject;

public enum CategoryType {

    WISDOM("\uD83D\uDC3E", "#BD10E0", "Carpincho"),  // Morado- sabiduría
    WELLBEING("\uD83D\uDC0A", "#7ED321", "Yacaré"),// Verde - bienestar
    ENERGY("\uD83D\uDC06", "#F5A623", "Jaguar"),// Naranja - energía
    SELF_CARE("\uD83E\uDD94", "#4A90E2", "Tatú"),// Azul - autocuidado
    OTHER("\uD83E\uDDA1", "#9B9B9B", "Coatí");// Gris - genérico

    private final String animalEmoji;
    private final String colorHex;
    private final String animalName;

    CategoryType(String animalEmoji, String colorHex, String animalName) {
        this.animalEmoji = animalEmoji;
        this.colorHex = colorHex;
        this.animalName = animalName;
    }
}
