package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class InternacionalizacionController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/saludo")
    public String obtenerSaludo(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        // Convertir el encabezado Accept-Language en un Locale
        Locale locale = parseAcceptLanguageHeader(acceptLanguage);
        return messageSource.getMessage("welcome.message", null, locale);
    }

    private Locale parseAcceptLanguageHeader(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isEmpty()) {
            return Locale.getDefault(); // Usar el Locale por defecto si no se proporciona el encabezado
        }

        // Extraer el primer idioma de la lista (por ejemplo, "es-CO" de "es-CO,es-419;q=0.9,es;q=0.8")
        String primaryLanguage = acceptLanguage.split(",")[0];

        // Dividir el idioma y el país (por ejemplo, "es-CO" -> "es" y "CO")
        String[] parts = primaryLanguage.split("-");
        if (parts.length == 1) {
            return new Locale(parts[0]); // Solo idioma (por ejemplo, "es")
        } else {
            return new Locale(parts[0], parts[1]); // Idioma y país (por ejemplo, "es-CO")
        }
    }
}