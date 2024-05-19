package com.mercant.real.estate.core.model.genericmodel;

import com.mercant.real.estate.core.fileoperation.contract.FileObject;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class OmiValue implements FileObject {
    private final String areaTerritoriale;
    private final String regione;
    private final String prov;
    private final String comuneISTAT;
    private final String comuneCat;
    private final String sez;
    private final String comuneAmm;
    private final String comuneDescrizione;
    private final String fascia;
    private final String zona;
    private final String linkZona;
    private final String codTip;
    private final String descrTipologia;
    private final String stato;
    private final String statoPrev;
    private final String comprMin;
    private final String comprMax;
    private final String supNLCompr;
    private final String locMin;
    private final String locMax;
    private final String supNLLoc;
    private final LocalDateTime localDateTime;

    private OmiValue(String areaTerritoriale, String regione, String prov, String comuneISTAT, String comuneCat, String sez,
                     String comuneAmm, String comuneDescrizione, String fascia, String zona, String linkZona, String codTip,
                     String descrTipologia, String stato, String statoPrev, String comprMin, String comprMax, String supNLCompr,
                     String locMin, String locMax, String supNLLoc, LocalDateTime localDate) {
        this.areaTerritoriale = areaTerritoriale;
        this.regione = regione;
        this.prov = prov;
        this.comuneISTAT = comuneISTAT;
        this.comuneCat = comuneCat;
        this.sez = sez;
        this.comuneAmm = comuneAmm;
        this.comuneDescrizione = comuneDescrizione;
        this.fascia = fascia;
        this.zona = zona;
        this.linkZona = linkZona;
        this.codTip = codTip;
        this.descrTipologia = descrTipologia;
        this.stato = stato;
        this.statoPrev = statoPrev;
        this.comprMin = comprMin;
        this.comprMax = comprMax;
        this.supNLCompr = supNLCompr;
        this.locMin = locMin;
        this.locMax = locMax;
        this.supNLLoc = supNLLoc;
        this.localDateTime = localDate;
    }

    @Override
    public String toString() {
        return "ValueOmi{" +
                "areaTerritoriale='" + areaTerritoriale + '\'' +
                ", regione='" + regione + '\'' +
                ", prov='" + prov + '\'' +
                ", comuneISTAT='" + comuneISTAT + '\'' +
                ", comuneCat='" + comuneCat + '\'' +
                ", sez='" + sez + '\'' +
                ", comuneAmm='" + comuneAmm + '\'' +
                ", comuneDescrizione='" + comuneDescrizione + '\'' +
                ", fascia='" + fascia + '\'' +
                ", zona='" + zona + '\'' +
                ", linkZona='" + linkZona + '\'' +
                ", codTip='" + codTip + '\'' +
                ", descrTipologia='" + descrTipologia + '\'' +
                ", stato='" + stato + '\'' +
                ", statoPrev='" + statoPrev + '\'' +
                ", comprMin='" + comprMin + '\'' +
                ", comprMax='" + comprMax + '\'' +
                ", supNLCompr='" + supNLCompr + '\'' +
                ", locMin='" + locMin + '\'' +
                ", locMax='" + locMax + '\'' +
                ", supNLLoc='" + supNLLoc + '\'' +
                '}';
    }

    public static final class ValueOmiBuilder {
        private String areaTerritoriale;
        private String regione;
        private String prov;
        private String comuneISTAT;
        private String comuneCat;
        private String sez;
        private String comuneAmm;
        private String comuneDescrizione;
        private String fascia;
        private String zona;
        private String linkZona;
        private String codTip;
        private String descrTipologia;
        private String stato;
        private String statoPrev;
        private String comprMin;
        private String comprMax;
        private String supNLCompr;
        private String locMin;
        private String locMax;
        private String supNLLoc;

        private LocalDateTime localDate;

        public ValueOmiBuilder comuneISTAT(String comuneISTAT) {
            this.comuneISTAT = comuneISTAT;
            return this;
        }

        public ValueOmiBuilder comuneCat(String comuneCat) {
            this.comuneCat = comuneCat;
            return this;
        }

        public ValueOmiBuilder sez(String sez) {
            this.sez = sez;
            return this;
        }

        public ValueOmiBuilder comuneAmm(String comuneAmm) {
            this.comuneAmm = comuneAmm;
            return this;
        }

        public ValueOmiBuilder comuneDescrizione(String comuneDescrizione) {
            this.comuneDescrizione = comuneDescrizione;
            return this;
        }

        public ValueOmiBuilder fascia(String fascia) {
            this.fascia = fascia;
            return this;
        }

        public ValueOmiBuilder zona(String zona) {
            this.zona = zona;
            return this;
        }

        public ValueOmiBuilder linkZona(String linkZona) {
            this.linkZona = linkZona;
            return this;
        }

        public ValueOmiBuilder codTip(String codTip) {
            this.codTip = codTip;
            return this;
        }

        public ValueOmiBuilder descrTipologia(String descrTipologia) {
            this.descrTipologia = descrTipologia;
            return this;
        }

        public ValueOmiBuilder stato(String stato) {
            this.stato = stato;
            return this;
        }

        public ValueOmiBuilder statoPrev(String statoPrev) {
            this.statoPrev = statoPrev;
            return this;
        }

        public ValueOmiBuilder comprMin(String comprMin) {
            this.comprMin = comprMin;
            return this;
        }

        public ValueOmiBuilder comprMax(String comprMax) {
            this.comprMax = comprMax;
            return this;
        }

        public ValueOmiBuilder supNLCompr(String supNLCompr) {
            this.supNLCompr = supNLCompr;
            return this;
        }

        public ValueOmiBuilder locMin(String locMin) {
            this.locMin = locMin;
            return this;
        }

        public ValueOmiBuilder locMax(String locMax) {
            this.locMax = locMax;
            return this;
        }

        public ValueOmiBuilder supNLLoc(String supNLLoc) {
            this.supNLLoc = supNLLoc;
            return this;
        }

        public ValueOmiBuilder areaTerritoriale(String areaTerritoriale) {
            this.areaTerritoriale = areaTerritoriale;
            return this;
        }

        public ValueOmiBuilder prov(String prov) {
            this.prov = prov;
            return this;
        }

        public ValueOmiBuilder regione(String regione) {
            this.regione = regione;
            return this;
        }

        public ValueOmiBuilder localDate(LocalDateTime localDate) {
            this.localDate = localDate;
            return this;
        }

        public OmiValue build() {
            return new OmiValue(areaTerritoriale, regione, prov, comuneISTAT, comuneCat, sez, comuneAmm, comuneDescrizione,
                    fascia, zona, linkZona, codTip, descrTipologia, stato, statoPrev, comprMin, comprMax, supNLCompr,
                    locMin, locMax, supNLLoc, localDate);
        }
    }

}
