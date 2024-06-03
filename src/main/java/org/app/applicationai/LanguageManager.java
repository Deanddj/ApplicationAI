package org.app.applicationai;

public class LanguageManager {
    private static LanguageSubject languageSubject = new LanguageSubject();

    private static LanguageManager instance;

    private LanguageManager() {
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public LanguageSubject getLanguageSubject() {
        return languageSubject;
    }
}
