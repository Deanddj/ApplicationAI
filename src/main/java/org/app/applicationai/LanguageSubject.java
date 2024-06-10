package org.app.applicationai;

import java.util.ArrayList;
import java.util.List;

public class LanguageSubject {
    private List<LanguageObserver> observers = new ArrayList<>();
    private String currentLanguage = "Nederlands";

    public void addObserver(LanguageObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String language) {
        currentLanguage = language;
        for (LanguageObserver observer : observers) {
            observer.applyLanguageChanges(language);
        }
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(String language) {
        notifyObservers(language);
    }
}
