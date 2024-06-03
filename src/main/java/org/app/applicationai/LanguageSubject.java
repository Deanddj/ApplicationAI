package org.app.applicationai;

import java.util.ArrayList;
import java.util.List;

public class LanguageSubject {
    private List<LanguageObserver> observers = new ArrayList<>();
    private String currentLanguage = "Nederlands"; // Default taal

    public void addObserver(LanguageObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String language) {
        currentLanguage = language; // Sla de nieuwe taal op
        for (LanguageObserver observer : observers) {
            observer.applyLanguageChanges(language);
        }
    }

    // Methode om de huidige taal op te halen
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    // Methode om de taal in te stellen en alle observers te notificeren
    public void setCurrentLanguage(String language) {
        notifyObservers(language);
    }
}
