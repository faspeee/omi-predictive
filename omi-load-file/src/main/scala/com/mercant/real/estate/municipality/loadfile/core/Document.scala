package com.mercant.real.estate.municipality.loadfile.core

import com.mercant.real.estate.municipality.loadfile.configuration.SparkConfiguration.spark
import org.apache.spark.sql.Dataset

import scala.reflect.runtime.universe.TypeTag

class Document {
  def readDocumentFromPath[T <: Product : TypeTag](path: String): Dataset[T] = {
    import spark.implicits._
    spark.read.option("delimiter", ";").option("header", value = true).csv(path).show()
    spark.read.options(Map("delimiter" -> ";", "header" -> "true", "lineSep" -> "\r")).csv(path).as[T]
  }
}
