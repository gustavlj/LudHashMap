# LudHashMap
 
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
