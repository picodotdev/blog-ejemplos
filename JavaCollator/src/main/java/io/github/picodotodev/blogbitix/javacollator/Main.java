package io.github.picodotodev.blogbitix.javacollator;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Main {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List cadenas = List.of("Cantabria", "Álava", "Alava", "alava");
        
        String[] sortedNoCollator = (String[]) cadenas.toArray(new String[0]);
        List sortedPrimaryCollator = new ArrayList(cadenas);
        List sortedSecondaryCollator = new ArrayList(cadenas);
        List sortedTertiaryCollator = new ArrayList(cadenas);

        Collator primaryCollator = Collator.getInstance(new Locale("es"));
        Collator secondaryCollator = Collator.getInstance(new Locale("es"));
        Collator tertiaryCollator = Collator.getInstance(new Locale("es"));

        primaryCollator.setStrength(Collator.PRIMARY);
        secondaryCollator.setStrength(Collator.SECONDARY);
        tertiaryCollator.setStrength(Collator.TERTIARY);

        Arrays.sort(sortedNoCollator);
        sortedPrimaryCollator.sort(primaryCollator);
        sortedSecondaryCollator.sort(secondaryCollator);
        sortedTertiaryCollator.sort(tertiaryCollator);

        System.out.printf("Lista cadenas:                             %s%n", cadenas);        
        System.out.printf("Ordenación sin clase Collator:             %s%n", Arrays.asList(sortedNoCollator));        
        System.out.printf("Ordenación con clase Collator (primary):   %s%n", sortedPrimaryCollator);
        System.out.printf("Ordenación con clase Collator (secondary): %s%n", sortedSecondaryCollator);
        System.out.printf("Ordenación con clase Collator (tertiary):  %s%n", sortedTertiaryCollator);
    }
}
