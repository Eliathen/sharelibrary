package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Language;

public class LanguageGenerator {

    public static Language getLanguage() {
        Language language = new Language();
        language.setId(1);
        language.setName("English");
        return language;
    }
}
