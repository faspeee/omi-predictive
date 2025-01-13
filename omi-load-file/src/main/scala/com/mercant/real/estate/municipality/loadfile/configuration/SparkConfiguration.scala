package com.mercant.real.estate.municipality.loadfile.configuration

import org.apache.spark.sql.SparkSession

object SparkConfiguration {
  val spark: SparkSession = SparkSession.builder
    .appName("Simple Application")
    .config("spark.master", "local")
    .getOrCreate()
}
