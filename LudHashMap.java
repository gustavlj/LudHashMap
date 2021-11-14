/**
 * En implementasjon av en Hash Map med linear probing.
 * 
 * Merk følgende:
 * - Hvor et element settes inn baseres på nøkkelens hashverdi
 * - Hvis plassen som nøkkelen hashet til er opptatt, prøver vi neste plass.
 * - Det vil alltid være nok plass til å sette inn den et sted, ettersom struktur ekspanderer ved innsetting.
 * 
 * - Når vi sletter en entry, så blir den markert som fjernet.
 * - Et element som er markert fjernet kan ikke "gettes" fra strukturen, istedet returneres -1.
 * - Når vi sletter elementer, sjekker vi om vi bør forminske strukturen.
 * - Når vi forminsker (eller ekspanderer) strukturen, blir ikke de element som er markert
 *      for sletting videreført.
 * 
 * - Hvis vi bruker get-metoden for å prøve å finne et element via en nøkkel, 
 *   og plassen som nøkkelen hasher til er tom så kan ikke elementet finnes.
 *   Hvis elementet finnes ville det ligge her, ELLER så ville vi funnet et annet element
 *   og prøvd etterfølgende verdier. 
 */

public class LudHashMap {

    LudHashMapEntry [] array = new LudHashMapEntry [5]; // array starter med lengde 5
    int n = 0; // n er antallet "aktive" elementer i arrayet
    
    /**
     * Et LudHashMapEntry er en "node" i listen
     */
    class LudHashMapEntry {
        String key; // nøkkel til node
        int value; // verdi til node
        boolean removed = false; // hvis noden har blitt slettet / deaktivert eller ikke
        
        LudHashMapEntry (String key, int value) {
            this.key = key;
            this.value = value;
        }
        public String toString() {
            return "[" + key + ": " + value + "]";
        }
    }

    /**
     * Setter inn en ny nøkkel k og verdi v i strukturen.
     * Hvis nøkkel finnes fra før oppdaterers gammel verdi
     * Det vil alltid finnes plass til nye verdier, ettersom strukturen ekspanderer
     * @param k
     * @param v
     */
    public void put(String k, int v) {
        
        int i = getHash(k); // finner hashverdi for gitt nøkkel
        
        LudHashMapEntry newEntry = new LudHashMapEntry(k, v); // nyNode som skal settes inn

        while(true) {
            if (array[i] == null) {  // hvis plassen som nøkkel hasher til er tom, kan ikke element finnes noe sted i arrayet
                array[i] = newEntry; // sett inn nytt element
                n++; // øker antallet aktive elementer i strukturen
                // System.out.println(array[i] + " was added on index " + i);
                break; // bryt loop
                
            } else if (array[i].key.equals(k)) { // plassen som ble hashet til er opptatt med entry med lik nøkkel
                array[i] = newEntry; // erstatter plassen med newEntry
                // System.out.println(array[i] + " was updated");
                break; // bryt loop
                
            } else { // KOLLISJON, plassen som ble hashet til er opptatt, men inneholder annen nøkkel
                i = (i + 1) % array.length; // prøv neste i, men sørg for å ikke vokse større enn antall plasser i array
            }
        }

        // etter innsetting, sjekk om array må ekspandere
        if ((double) n / (double) array.length > 0.6) {
            // System.out.println("Forholdet er " + (double) n / (double) array.length + " expanderer array.");
            expandArray(); // ekspanderer array
        }
    }
    
    /**
     * Fjerner en nøkkel
     * En fjernet nøkkel ligger kvar som deaktivert, men blir fjernet ved neste ekspansjon eller forminsking
     * @param k
     */
    public void remove (String k) {
        
        int i = getHash(k); // finner nøkkelens hashverdi

        // System.out.println("Key '" + k + "' was hashed to '" + i + "'");
        
        while(true) {
            if (array[i] == null) { // hvis plassen som ble hashet til er tom, kan ikke dette element finnes noe sted i arrayet 
                return; 
            } else if (array[i].key.equals(k)) {  // plassen i arrayet er ikke-tom og nøkkel stemmer
                if (array[i].removed) { // node er allerede markert for sletting, gjør ingenting
                    // System.out.println(array[i] + " er allerede markert for sletting");
                    return;
                } else { // node er ikke markert for sletting
                    // System.out.println(array[i] + " ble markert for sletting");
                    n--; // minske n, siden vi har en mindre aktiv node
                    array[i].removed = true; // marker for sletting
                    break;
                }
            } 
            i = (i+1) % array.length; // hvis plassen var tom, eller noden som lå der hadde annen nøkkel, prøv neste
        }

        // sjekk om vi bør forminske array
        if ((double) n / (double) array.length < 0.3) {
            // System.out.println("Forholdet er " + (double) n / (double) array.length + ", kryper array.");
            shrinkArray();
        }
    }

    /**
     * Tar en streng s og gir tilbake en hash for strengen basert på formel foreslått på forelesing
     * @param s
     * @return int hash av streng
     */
    private int getHash(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            int kASCII = (int) s.charAt(i); // String.charAt(i) returnerer en char, som ved cast til int gir ASCII 
            h = Math.abs(31 * h + kASCII); // hashverdien forandres med ulik magnitud for hvert tegn
            // System.out.println("h er naa " + h);
        }
        h = h % array.length; // verdien tas modulo lengde på array
        return h;
    }
    
    public int get(String k) {
        
        int i = getHash(k); // finner nøkkelens hashverdi

        // System.out.println("Trying to find '" + k + "' from hash '" + i + "'");
        
        while(true) {
            
            /**
             * Hvis plassen vi hashet til er tom, så kan ikke elementet finnes noen annen plass i arrayet heller.
             * Begrunnelse:
             * Hvis vi puttet inn k før, så ville det vært her, 
             * eller så ville et annet element på denne plassen "dyttet" k bortover i listen.
             * Uansett måtte denne plassen ha vært opptatt, så hvis den ikke er opptatt, finnes ikke k.
             */
            if (array[i] == null) {
                return -1; // returnerer -1
            }
            if (array[i].key.equals(k)) { // hvis vi fant riktig node
                if (array[i].removed) { // og den var markert for sletting, returner -1
                    return -1;
                } else {
                    return array[i].value; // og den fantes, returner verdi
                }
            }
            i = (i+1) % array.length;
        }
    }

    private void expandArray() {
        LudHashMapEntry [] newArray = new LudHashMapEntry [array.length * 2]; // lager ny større array
        LudHashMapEntry [] tmp = array; // lagrer gammel array i tmp
        array = newArray; // peker om hovedpeker til nyArray
        for (LudHashMapEntry entry : tmp) { // løper over array
            if (entry != null && !entry.removed) { // hvis plass ikke er tom, eller element markert for sletting
                put(entry.key, entry.value); // legg til element i ny array
            } 
        }
        n = 0; // nullstiller telleren i strukturen
        
        // System.out.println("Array was expanded to " + array.length);
        
    }

    private void shrinkArray() {
        LudHashMapEntry [] newArray = new LudHashMapEntry [array.length / 2]; // lager mindre array
        LudHashMapEntry [] tmp = array;
        array = newArray;
        n = 0; // resetting counter
        for (LudHashMapEntry entry : tmp) {
            if (entry != null && !entry.removed) put(entry.key, entry.value);
        }
        
        // System.out.println("Array was shrunk to " + array.length);   
    }
    
    public void printEntries() {
        System.out.println("\nEntries:");
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && !array[i].removed) System.out.println(array[i]);
        }
    }

    public void printAllEntries() {
        System.out.println("\nAll entries:");
        for (int i = 0; i < array.length; i++) {
            String repr = i + " " + array[i];
            if (array[i] != null && array[i].removed) repr += "(skal slettes)";
            System.out.println(repr);
        }
    } 
    public boolean contains(String key) {
        return get(key) != -1; // get method returns -1 if it cannot find key.
    }
}
