package com.mercant.real.estate.municipality.loadfile.model

import org.apache.spark.sql.Encoders

import java.time.LocalDateTime

case class OmiValue(areaTerritoriale: String,
                    regione: String,
                    prov: String,
                    comuneISTAT: String,
                    comuneCat: String,
                    sez: String,
                    comuneAmm: String,
                    comuneDescrizione: String,
                    fascia: String,
                    zona: String,
                    linkZona: String,
                    codTip: String,
                    descrTipologia: String,
                    stato: String,
                    statoPrev: String,
                    comprMin: String,
                    comprMax: String,
                    supNLCompr: String,
                    locMin: String,
                    locMax: String,
                    supNLLoc: String,
                    localDateTime: LocalDateTime)

object OmiValue {
  implicit def barEncoder: org.apache.spark.sql.Encoder[OmiValue] = Encoders.kryo[OmiValue]
}