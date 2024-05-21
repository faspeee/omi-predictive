package com.mercant.real.estate.util;

import com.mercant.real.estate.OmiValue;
import com.mercant.real.estate.OmiZone;
import com.mercant.real.estate.core.model.genericmodel.ValueAndZone;

import java.util.function.Function;

public final class BuilderUtil {
    public static final Function<com.mercant.real.estate.core.model.genericmodel.OmiValue, OmiValue> createOmiValue = omiValue -> OmiValue.newBuilder()
            .setAreaTerritoriale(omiValue.getAreaTerritoriale())
            .setRegione(omiValue.getRegione())
            .setProv(omiValue.getProv())
            .setComuneISTAT(omiValue.getComuneISTAT())
            .setComuneCat(omiValue.getComuneCat())
            .setSez(omiValue.getSez())
            .setComuneAmm(omiValue.getComuneAmm())
            .setComuneDescrizione(omiValue.getComuneDescrizione())
            .setFascia(omiValue.getFascia())
            .setZona(omiValue.getZona())
            .setLinkZona(omiValue.getLinkZona())
            .setCodTip(omiValue.getCodTip())
            .setDescrTipologia(omiValue.getDescrTipologia())
            .setStato(omiValue.getStato())
            .setStatoPrev(omiValue.getStatoPrev())
            .setComprMin(omiValue.getComprMin())
            .setComprMax(omiValue.getComprMax())
            .setSupNLCompr(omiValue.getSupNLCompr())
            .setLocMin(omiValue.getLocMin())
            .setLocMax(omiValue.getLocMax())
            .setSupNLLoc(omiValue.getSupNLLoc())
            .build();
    public static final Function<com.mercant.real.estate.core.model.genericmodel.OmiZone, OmiZone> createOmiZone = omiZone -> OmiZone.newBuilder()
            .setAreaTerritoriale(omiZone.getAreaTerritoriale())
            .setRegione(omiZone.getRegione())
            .setProv(omiZone.getProv())
            .setComuneISTAT(omiZone.getComuneISTAT())
            .setComuneCat(omiZone.getComuneCat())
            .setSez(omiZone.getSez())
            .setComuneAmm(omiZone.getComuneAmm())
            .setComuneDescrizione(omiZone.getComuneDescrizione())
            .setFascia(omiZone.getFascia())
            .setZona(omiZone.getZona())
            .setLinkZona(omiZone.getLinkZona())
            .setCodTipPrev(omiZone.getCodTipPrev())
            .setDescrTipPrev(omiZone.getDescrTipPrev())
            .setStatoPrev(omiZone.getStatoPrev())
            .setMicrozona(omiZone.getMicrozona())
            .build();
    public static final Function<ValueAndZone, OmiZone> createOmiZoneFromValueAndZone = valueAndZone -> createOmiZone.apply(valueAndZone.omiZone());
    public static final Function<ValueAndZone, OmiValue> createOmiValueFromValueAndZone = valueAndZone -> createOmiValue.apply(valueAndZone.omiValue());

    private BuilderUtil() {
    }

}
