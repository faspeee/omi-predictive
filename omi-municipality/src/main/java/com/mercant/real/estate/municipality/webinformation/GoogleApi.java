package com.mercant.real.estate.municipality.webinformation;

import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.model.LatitudeLongitude;
import com.mercant.real.estate.municipality.model.LatitudeLongitudeAltitude;
import com.mercant.real.estate.municipality.utils.GlobalConstants;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpRequest;

import java.util.Optional;

import static com.mercant.real.estate.municipality.utils.Constant.API_KEY;

/**
 * Provides methods to interact with the Google Maps API for geocoding and elevation data.
 *
 * <p>This class fetches latitude, longitude, and altitude information for specified locations
 * by making HTTP requests to the Google Maps API. It uses a {@link WebClientVerticle} for
 * asynchronous HTTP communication and provides utility methods for normalizing strings and
 * handling default JSON responses.
 */
public final class GoogleApi {
    private final WebClientVerticle webClientVerticle;

    /**
     * Constructs an instance of {@link GoogleApi} with a specified {@link WebClientVerticle}.
     *
     * @param webClientVerticle the {@link WebClientVerticle} used for HTTP communication
     */
    public GoogleApi(WebClientVerticle webClientVerticle) {
        this.webClientVerticle = webClientVerticle;
    }

    /**
     * Normalizes a string by replacing whitespace with URL-encoded spaces ("%20").
     *
     * @param string the string to be normalized
     * @return the normalized string
     */
    private static String normalizeString(String string) {
        return string.replaceAll("\\s", "%20");
    }

    /**
     * Provides a default {@link JsonObject} representing a location with zero latitude and longitude.
     *
     * @return a {@link JsonObject} with default latitude and longitude values
     */
    private static JsonObject defaultJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("lat", 0.0);
        jsonObject.put("lng", 0.0);
        return jsonObject;
    }

    /**
     * Retrieves latitude, longitude, and altitude for a given city and region.
     *
     * <p>This method first fetches the latitude and longitude for the specified city and region using
     * {@link #getLatitudeLongitude(String, String)}. It then retrieves the altitude for the obtained
     * coordinates using {@link #getAltitude(double, double)}. The data is combined into a
     * {@link LatitudeLongitudeAltitude} object.
     *
     * @param cityName the name of the city
     * @param cityCode a unique code identifying the city
     * @param region   the region where the city is located
     * @return a {@link Uni} containing a {@link LatitudeLongitudeAltitude} object with the city's geocoding data
     */
    public Uni<LatitudeLongitudeAltitude> getLatitudeLongitudeAndAltitude(String cityName, int cityCode, String region) {
        return getLatitudeLongitude(cityName, region)
                .flatMap(latitudeLongitude -> getAltitude(latitudeLongitude.latitude(), latitudeLongitude.longitude())
                        .map(altitude -> new LatitudeLongitudeAltitude(cityName, cityCode, latitudeLongitude.latitude(),
                                latitudeLongitude.longitude(), altitude)));
    }

    private HttpRequest<Buffer> createRequestTemplate(String url) {
        return webClientVerticle.getWebClient()
                .getAbs(url);
    }

    /**
     * Retrieves the latitude and longitude for a given city and region using the Google Geocoding API.
     *
     * <p>This method sends an HTTP GET request to the Google Geocoding API with the city and region as parameters.
     * The response is parsed to extract the latitude and longitude from the JSON response. If no results are found,
     * default coordinates (latitude: 0.0, longitude: 0.0) are returned.
     *
     * @param cityName the name of the city
     * @param region   the region where the city is located
     * @return a {@link Uni} containing a {@link LatitudeLongitude} object with the city's latitude and longitude
     */
    public Uni<LatitudeLongitude> getLatitudeLongitude(String cityName, String region) {
        return createRequestTemplate("https://maps.googleapis.com/maps/api/geocode/json?address=" + normalizeString(cityName)
                + "&region=" + normalizeString(region)
                + "&key=AIzaSyBeg1G6SQRXAqCT6vU7BnD0OlpEBBJJjxo")
                .send()
                .map(bufferHttpResponse -> {
                    JsonObject jsonObject = bufferHttpResponse.bodyAsJsonObject();
                    JsonObject latLong = Optional.ofNullable(jsonObject.getJsonArray("results"))
                            .filter(jsonArray -> !jsonArray.isEmpty())
                            .map(jsonArray -> jsonArray
                                    .getJsonObject(0)
                                    .getJsonObject("geometry")
                                    .getJsonObject("location"))
                            .orElseGet(GoogleApi::defaultJsonObject);
                    return new LatitudeLongitude(latLong.getDouble("lat"), latLong.getDouble("lng"));
                });
    }

    /**
     * Retrieves the altitude for specified latitude and longitude coordinates using the Google Elevation API.
     *
     * <p>This method sends an HTTP GET request to the Google Elevation API with the coordinates as parameters.
     * The response is parsed to extract the elevation value from the JSON response. If no results are found,
     * a default altitude of 0.0 is returned.
     *
     * @param lat the latitude of the location
     * @param lng the longitude of the location
     * @return a {@link Uni} containing the altitude of the location
     */
    public Uni<Double> getAltitude(double lat, double lng) {
        return createRequestTemplate("https://maps.googleapis.com/maps/api/elevation/json?locations=" + lat + "%2C" + lng
                + "&key=" + GlobalConstants.getInstance().getConstant(API_KEY.text()))
                .send()
                .map(bufferHttpResponse -> {
                    JsonObject jsonObject = bufferHttpResponse.bodyAsJsonObject();
                    if (jsonObject.getJsonArray("results").size() > 1) {
                        System.out.println("");
                    }
                    return Optional.ofNullable(jsonObject.getJsonArray("results"))
                            .filter(jsonArray -> !jsonArray.isEmpty())
                            .map(jsonArray -> jsonArray
                                    .getJsonObject(0)
                                    .getDouble("elevation"))
                            .orElse(0.0);
                });
    }
}
