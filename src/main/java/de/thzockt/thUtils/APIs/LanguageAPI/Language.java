package de.thzockt.thUtils.APIs.LanguageAPI;

public enum Language {
    GERMAN("Deutsch"),
    ENGLISH("English");

    private String name;
    Language(String n) {
        name = n;
    }
    public String getName() {
        return name;
    }
}