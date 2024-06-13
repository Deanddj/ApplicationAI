package org.app.applicationai;

public interface LanguageObserver {
    void update(String language);

    String loadLanguage();
}
