package org.example.omi.core.fileoperation;

import org.example.omi.core.model.OmiZone;

import java.util.List;
import java.util.Optional;

import static org.example.omi.core.util.OperationUtil.getSafeMode;

public final class ReadFileZone extends AbstractFile {


    public Optional<List<FileObject>> readFile(String path) {
        return readFile(path, 1);
    }

    @Override
    protected FileObject constructObject(String line) {
        String[] elements = line.split(";");
        /*
        Area_territoriale;Regione;Prov;Comune_ISTAT;Comune_cat;Sez;Comune_amm;Comune_descrizione;Fascia;Zona_Descr;
        Zona;LinkZona;Cod_tip_prev;Descr_tip_prev;Stato_prev;Microzona;
         */
        return new OmiZone.OmiZoneBuilder()
                .areaTerritoriale(getSafeMode(elements, 0))
                .regione(getSafeMode(elements, 1))
                .prov(getSafeMode(elements, 2))
                .comuneISTAT(getSafeMode(elements, 3))
                .comuneCat(getSafeMode(elements, 4))
                .sez(getSafeMode(elements, 5))
                .comuneAmm(getSafeMode(elements, 6))
                .comuneDescrizione(getSafeMode(elements, 7))
                .fascia(getSafeMode(elements, 8))
                .zonaDescr(getSafeMode(elements, 9))
                .zona(getSafeMode(elements, 10))
                .linkZona(getSafeMode(elements, 11))
                .codTipPrev(getSafeMode(elements, 12))
                .descrTipPrev(getSafeMode(elements, 13))
                .statoPrev(getSafeMode(elements, 14))
                .microzona(getSafeMode(elements, 15))
                .build();
    }
}
