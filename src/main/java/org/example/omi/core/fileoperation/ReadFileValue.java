package org.example.omi.core.fileoperation;

import org.example.omi.core.model.OmiValue;

import java.util.List;
import java.util.Optional;

import static org.example.omi.core.util.OperationUtil.getSafeMode;

public final class ReadFileValue extends AbstractFile {
   /* private static void x() {
        Collector<AbstractMap.SimpleImmutableEntry<Integer, FileObject>, ?, Map<Integer, FileObject>> x = Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue);
        Collector<AbstractMap.SimpleImmutableEntry<Integer, FileObject>, ?, Map<Integer, List<FileObject>>> mapCollector =
                Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey, Collectors.mapping(AbstractMap.SimpleImmutableEntry::getValue, Collectors.toList()));


    }*/

    public Optional<List<FileObject>> readFile(String path) {
        return readFile(path, 1);
    }

    @Override
    protected FileObject constructObject(String line) {
        String[] elements = line.split(";");
        /*
        Area_territoriale;Regione;Prov;Comune_ISTAT;Comune_cat;Sez;Comune_amm;Comune_descrizione;Fascia;Zona;LinkZona;
        Cod_Tip;Descr_Tipologia;Stato;Stato_prev;Compr_min;Compr_max;Sup_NL_compr;Loc_min;Loc_max;Sup_NL_loc;
         */
        return new OmiValue.ValueOmiBuilder()
                .areaTerritoriale(getSafeMode(elements, 0))
                .regione(getSafeMode(elements, 1))
                .prov(getSafeMode(elements, 2))
                .comuneISTAT(getSafeMode(elements, 3))
                .comuneCat(getSafeMode(elements, 4))
                .sez(getSafeMode(elements, 5))
                .comuneAmm(getSafeMode(elements, 6))
                .comuneDescrizione(getSafeMode(elements, 7))
                .fascia(getSafeMode(elements, 8))
                .zona(getSafeMode(elements, 9))
                .linkZona(getSafeMode(elements, 10))
                .codTip(getSafeMode(elements, 11))
                .descrTipologia(getSafeMode(elements, 12))
                .stato(getSafeMode(elements, 13))
                .statoPrev(getSafeMode(elements, 14))
                .comprMin(getSafeMode(elements, 15))
                .comprMax(getSafeMode(elements, 16))
                .supNLCompr(getSafeMode(elements, 17))
                .locMin(getSafeMode(elements, 18))
                .locMax(getSafeMode(elements, 19))
                .supNLLoc(getSafeMode(elements, 20))
                .build();
    }
}
