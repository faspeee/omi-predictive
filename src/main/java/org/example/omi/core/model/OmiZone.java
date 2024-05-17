package org.example.omi.core.model;

import org.example.omi.core.fileoperation.FileObject;


public final class OmiZone implements FileObject {
    private final String areaTerritoriale;
    private final String regione;
    private final String prov;
    private final String comuneISTAT;
    private final String comuneCat;
    private final String sez;
    private final String comuneAmm;
    private final String comuneDescrizione;
    private final String fascia;
    private final String zonaDescr;
    private final String zona;
    private final String linkZona;
    private final String codTipPrev;
    private final String descrTipPrev;
    private final String statoPrev;
    private final String microzona;

    public OmiZone(String areaTerritoriale, String regione, String prov, String comuneISTAT, String comuneCat, String sez,
                   String comuneAmm, String comuneDescrizione, String fascia, String zonaDescr, String zona, String linkZona,
                   String codTipPrev, String descrTipPrev, String statoPrev, String microzona) {
        this.areaTerritoriale = areaTerritoriale;
        this.regione = regione;
        this.prov = prov;
        this.comuneISTAT = comuneISTAT;
        this.comuneCat = comuneCat;
        this.sez = sez;
        this.comuneAmm = comuneAmm;
        this.comuneDescrizione = comuneDescrizione;
        this.fascia = fascia;
        this.zonaDescr = zonaDescr;
        this.zona = zona;
        this.linkZona = linkZona;
        this.codTipPrev = codTipPrev;
        this.descrTipPrev = descrTipPrev;
        this.statoPrev = statoPrev;
        this.microzona = microzona;
    }

    public String getAreaTerritoriale() {
        return areaTerritoriale;
    }

    public String getRegione() {
        return regione;
    }

    public String getProv() {
        return prov;
    }

    public String getComuneISTAT() {
        return comuneISTAT;
    }

    public String getComuneCat() {
        return comuneCat;
    }

    public String getSez() {
        return sez;
    }

    public String getComuneAmm() {
        return comuneAmm;
    }

    public String getComuneDescrizione() {
        return comuneDescrizione;
    }

    public String getFascia() {
        return fascia;
    }

    public String getZonaDescr() {
        return zonaDescr;
    }

    public String getZona() {
        return zona;
    }

    public String getLinkZona() {
        return linkZona;
    }

    public String getCodTipPrev() {
        return codTipPrev;
    }

    public String getDescrTipPrev() {
        return descrTipPrev;
    }

    public String getStatoPrev() {
        return statoPrev;
    }

    public String getMicrozona() {
        return microzona;
    }

    @Override
    public String toString() {
        return "OmiZone{" +
                "areaTerritoriale='" + areaTerritoriale + '\'' +
                ", regione='" + regione + '\'' +
                ", prov='" + prov + '\'' +
                ", comuneISTAT='" + comuneISTAT + '\'' +
                ", comuneCat='" + comuneCat + '\'' +
                ", sez='" + sez + '\'' +
                ", comuneAmm='" + comuneAmm + '\'' +
                ", comuneDescrizione='" + comuneDescrizione + '\'' +
                ", fascia='" + fascia + '\'' +
                ", zonaDescr='" + zonaDescr + '\'' +
                ", zona='" + zona + '\'' +
                ", linkZona='" + linkZona + '\'' +
                ", codTipPrev='" + codTipPrev + '\'' +
                ", descrTipPrev='" + descrTipPrev + '\'' +
                ", statoPrev='" + statoPrev + '\'' +
                ", microzona='" + microzona + '\'' +
                '}';
    }

    public static class OmiZoneBuilder {
        private String areaTerritoriale;
        private String regione;
        private String prov;
        private String comuneISTAT;
        private String comuneCat;
        private String sez;
        private String comuneAmm;
        private String comuneDescrizione;
        private String fascia;
        private String zonaDescr;
        private String zona;
        private String linkZona;
        private String codTipPrev;
        private String descrTipPrev;
        private String statoPrev;
        private String microzona;

        public OmiZoneBuilder areaTerritoriale(String areaTerritoriale) {
            this.areaTerritoriale = areaTerritoriale;
            return this;
        }

        public OmiZoneBuilder regione(String regione) {
            this.regione = regione;
            return this;
        }

        public OmiZoneBuilder prov(String prov) {
            this.prov = prov;
            return this;
        }

        public OmiZoneBuilder comuneISTAT(String comuneISTAT) {
            this.comuneISTAT = comuneISTAT;
            return this;
        }

        public OmiZoneBuilder comuneCat(String comuneCat) {
            this.comuneCat = comuneCat;
            return this;
        }

        public OmiZoneBuilder sez(String sez) {
            this.sez = sez;
            return this;
        }

        public OmiZoneBuilder comuneAmm(String comuneAmm) {
            this.comuneAmm = comuneAmm;
            return this;
        }

        public OmiZoneBuilder comuneDescrizione(String comuneDescrizione) {
            this.comuneDescrizione = comuneDescrizione;
            return this;
        }

        public OmiZoneBuilder fascia(String fascia) {
            this.fascia = fascia;
            return this;
        }

        public OmiZoneBuilder zonaDescr(String zonaDescr) {
            this.zonaDescr = zonaDescr;
            return this;
        }

        public OmiZoneBuilder zona(String zona) {
            this.zona = zona;
            return this;
        }

        public OmiZoneBuilder linkZona(String linkZona) {
            this.linkZona = linkZona;
            return this;
        }

        public OmiZoneBuilder codTipPrev(String codTipPrev) {
            this.codTipPrev = codTipPrev;
            return this;
        }

        public OmiZoneBuilder descrTipPrev(String descrTipPrev) {
            this.descrTipPrev = descrTipPrev;
            return this;
        }

        public OmiZoneBuilder statoPrev(String statoPrev) {
            this.statoPrev = statoPrev;
            return this;
        }

        public OmiZoneBuilder microzona(String microzona) {
            this.microzona = microzona;
            return this;
        }

        public OmiZone build() {
            return new OmiZone(areaTerritoriale, regione, prov, comuneISTAT, comuneCat, sez,
                    comuneAmm, comuneDescrizione, fascia, zonaDescr, zona, linkZona,
                    codTipPrev, descrTipPrev, statoPrev, microzona);
        }

    }
}
