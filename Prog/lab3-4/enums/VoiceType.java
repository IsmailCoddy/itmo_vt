package enums;

public enum VoiceType {
    THIN("тонкий голос"),
    LOUD("громкий голос"),
    CROAK("карканье");

    private final String description;

    VoiceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}