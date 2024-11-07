package com.mercant.real.estate.municipality.loadfile.core

import com.mercant.real.estate.municipality.loadfile.model.OmiValue

object ProcessFile extends Document {
  def loadFile(): Unit = {
    import com.mercant.real.estate.municipality.loadfile.model.OmiValue.barEncoder
    val omiValueDataset = readDocumentFromPath("/Users/fabianaspeeencina/Code/omi-predictive/omi-library/src/main/resources/QI20051/QI_1027287_1_20051_VALORI.csv").as[OmiValue]
    print(omiValueDataset.columns.mkString("Array(", ", ", ")"))
  }
}
