package com.mercant.real.estate.municipality.webinformation;

import com.mercant.real.estate.municipality.configuration.WebClientVerticle;

public final class GoogleApi {
    private final WebClientVerticle webClientVerticle;

    public GoogleApi(WebClientVerticle webClientVerticle) {
        this.webClientVerticle = webClientVerticle;
    }

    public void getLatitudeLongitudeAndAltitude() {
        webClientVerticle.getWebClient().get("https://maps.googleapis.com/maps/api/geocode/json?address=Toledo&region=es&key=AIzaSyCoZjQD3X4V-58k8uvhn7VzkqBPze2Z4nE")
                .send()
                .map(bufferHttpResponse -> bufferHttpResponse.bodyAsString());
    }
}
