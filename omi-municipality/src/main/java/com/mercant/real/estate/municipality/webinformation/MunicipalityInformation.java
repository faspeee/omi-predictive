package com.mercant.real.estate.municipality.webinformation;

import com.mercant.real.estate.municipality.builder.ConstructObjectFromFile;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.model.MunicipalityModel;
import com.mercant.real.estate.municipality.model.OldMunicipalityModel;
import com.mercant.real.estate.municipality.utils.functional.Supplier;
import io.smallrye.mutiny.Uni;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.mercant.real.estate.core.util.ConstantSeparator.SEMICOLON;
import static com.mercant.real.estate.municipality.utils.Constant.*;
import static com.mercant.real.estate.municipality.utils.IntConstant.LINE_TO_SKIP_CURRENT_MUNICIPALITY;
import static com.mercant.real.estate.municipality.utils.IntConstant.LINE_TO_SKIP_OLD_MUNICIPALITY;

/**
 * The {@code MunicipalityInformation} class is responsible for reading, processing, and mapping municipality data
 * from compressed zip files, specifically containing CSV and Excel data formats. This class supports reading both
 * current and old municipality data, constructing relevant models for the data, and aggregating the data into maps.
 * The data is fetched from remote endpoints using the {@code WebClientVerticle} for asynchronous processing.
 * <p>
 * This class processes files in a ZIP format and supports CSV and Excel file formats. It provides functionality to:
 * 1. Read and parse data from ZIP files containing municipality information.
 * 2. Map CSV and Excel data to specific municipality models.
 * 3. Aggregate the municipality data into Maps for further processing.
 * <p>
 * It employs functional programming patterns and uses Java Streams for efficient data transformation and processing.
 *
 * <p><b>Key Functions:</b>
 * <ul>
 *   <li>Read data from CSV and Excel files inside ZIP archives.</li>
 *   <li>Map rows of data to models such as {@code MunicipalityModel} and {@code OldMunicipalityModel}.</li>
 *   <li>Aggregate the data into Maps, keyed by municipality codes.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * WebClientVerticle webClientVerticle = new WebClientVerticle();
 * MunicipalityInformation municipalityInfo = new MunicipalityInformation(webClientVerticle);
 * Map<String, MunicipalityModel> currentMunicipalities = municipalityInfo.readCurrentMunicipalities().await();
 * Map<String, Set<OldMunicipalityModel>> oldMunicipalities = municipalityInfo.readOldMunicipalities().await();
 * </pre>
 */
public final class MunicipalityInformation {

    /**
     * A predicate used to filter lines in CSV files, ensuring that the line is valid by checking if
     * specific fields (index 7 and 3) are not blank.
     */
    private static final Predicate<String> LINE_FILTER = line -> !line.split(SEMICOLON.separator())[7].isBlank() &&
            !line.split(SEMICOLON.separator())[3].isBlank();

    /**
     * A function that reads and processes CSV data containing {@code OldMunicipalityModel} objects from a {@link ZipInputStream}.
     */
    private static final BiFunction<ZipInputStream, ConstructObjectFromFile<OldMunicipalityModel>, Set<OldMunicipalityModel>> functionReadCsv = MunicipalityInformation::readCsvFile;

    /**
     * A function that reads and processes CSV data containing {@code MunicipalityModel} objects from a {@link ZipInputStream}.
     */
    private static final BiFunction<ZipInputStream, ConstructObjectFromFile<MunicipalityModel>, Set<MunicipalityModel>> functionReadCsv2 = MunicipalityInformation::readCsvFile;

    /**
     * The WebClientVerticle instance used for making HTTP requests to fetch municipality data.
     */
    private final WebClientVerticle webClientVerticle;

    /**
     * Constructs a new instance of {@code MunicipalityInformation} using the provided {@code WebClientVerticle}.
     *
     * @param webClientVerticle the {@code WebClientVerticle} instance to use for HTTP requests.
     */
    public MunicipalityInformation(WebClientVerticle webClientVerticle) {
        this.webClientVerticle = webClientVerticle;
    }

    /**
     * Reads and processes an Excel file from the provided {@link ZipInputStream}, mapping each row to a model
     * using the provided function.
     *
     * @param file     the {@link ZipInputStream} containing the Excel file.
     * @param function the function used to map each row in the Excel sheet to an object of type {@code T}.
     * @param <T>      the type of object to map the rows to (e.g., {@code MunicipalityModel}).
     * @return a {@link Set} containing the objects mapped from the rows in the Excel sheet.
     */
    public static <T> Set<T> readExcelFile(ZipInputStream file, Function<Row, T> function) {
        try (XSSFWorkbook wb = new XSSFWorkbook(file)) {
            XSSFSheet sheet = wb.getSheetAt(0); // Retrieve the first sheet from the Excel workbook
            // Create an iterable for all rows in the sheet
            Iterable<Row> iterable = sheet::rowIterator;
            return StreamSupport.stream(iterable.spliterator(), false)
                    .skip(2) // Skip the first two rows (headers)
                    .map(function) // Map each row to a municipality model
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            e.printStackTrace(); // Log any exception encountered while processing the file
        }
        return Collections.emptySet(); // Return an empty set if an error occurs
    }

    /**
     * Reads and processes a CSV file from the provided {@link ZipInputStream}, mapping each line to an object
     * using the {@code ConstructObjectFromFile} utility.
     *
     * @param file                    the {@link ZipInputStream} containing the CSV file.
     * @param constructObjectFromFile an instance of {@code ConstructObjectFromFile} that provides the
     *                                line filter, object constructor, and lines to skip.
     * @param <T>                     the type of object to map the rows to (e.g., {@code OldMunicipalityModel}).
     * @return a {@link Set} containing the objects mapped from the CSV lines.
     */
    public static <T> Set<T> readCsvFile(ZipInputStream file, ConstructObjectFromFile<T> constructObjectFromFile) {
        try (BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(file))) {
            return bufferedInputStream.lines()
                    .skip(constructObjectFromFile.getLinesToSkip()) // Skip header lines as defined by the filter
                    .filter(constructObjectFromFile.getLineFilter()) // Apply the filter to ensure data validity
                    .map(constructObjectFromFile.getConstructObject()) // Map each line to a municipality model
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception encountered while processing the file
        }
        return Collections.emptySet(); // Return an empty set if an error occurs
    }

    /**
     * Aggregates a set of objects into a map, using a specified key extractor function.
     *
     * @param set the set of objects to aggregate.
     * @param key the function used to extract a key from each object.
     * @param <T> the type of the objects in the set.
     * @param <K> the type of the key to use for the map.
     * @param <A> the type of the intermediate accumulator in the collector.
     * @param <D> the type of the result (in this case, a map).
     * @return a {@link Map} where the keys are extracted from the set using the {@code key} function,
     * and the values are aggregated as defined by the {@code downstream} collector.
     */
    private static <T, K, A, D> Map<K, D> aggregateStreamToMap(Set<T> set, Function<T, K> key, Collector<? super T, A, D> downstream) {
        return set.stream()
                .collect(Collectors.groupingBy(key, downstream));
    }

    /**
     * Aggregates a set of objects into a map, using a specified key extractor function.
     *
     * @param set the set of objects to aggregate.
     * @param key the function used to extract a key from each object.
     * @param <T> the type of the objects in the set.
     * @param <K> the type of the key to use for the map.
     * @return a {@link Map} where the keys are extracted from the set using the {@code key} function,
     * and the values are the objects themselves.
     */
    private static <T, K> Map<K, T> aggregateStreamToMap(Set<T> set, Function<T, K> key) {
        return set.stream()
                .collect(Collectors.toMap(key, Function.identity()));
    }

    /**
     * Creates a {@code MunicipalityModel} from an Excel {@link Row} by mapping each cell to the respective fields.
     *
     * @param row the {@link Row} to create the model from.
     * @return a new {@link MunicipalityModel} object constructed from the row data.
     */
    private static MunicipalityModel createMunicipalityModel(Row row) {
        return MunicipalityModel.builder()
                .regionCode(row.getCell(0).getStringCellValue())
                .provinceCode(row.getCell(2).getStringCellValue())
                .municipalityCode(String.format("%.0f", row.getCell(15).getNumericCellValue()))
                .municipalitySigle(row.getCell(3).getStringCellValue())
                .municipalityName(row.getCell(6).getStringCellValue())
                .regionName(row.getCell(10).getStringCellValue())
                .cadastralCode(row.getCell(19).getStringCellValue())
                .territorialUnitType(row.getCell(1).getStringCellValue())
                .capitalsMunicipality(row.getCell(11).getStringCellValue())
                .build();
    }

    /**
     * Creates a {@code MunicipalityModel} from a CSV line, parsing each field from the comma-separated values.
     *
     * @param row the CSV line to create the model from.
     * @return a new {@link MunicipalityModel} object constructed from the CSV data.
     */
    private static MunicipalityModel createMunicipalityModel(String row) {
        final String[] splitWords = row.split(SEMICOLON.separator());
        return MunicipalityModel.builder()
                .regionCode(splitWords[0])
                .provinceCode(splitWords[2])
                .municipalityCode(splitWords[15])
                .municipalitySigle(splitWords[3])
                .municipalityName(splitWords[6])
                .regionName(splitWords[10])
                .cadastralCode(splitWords[19])
                .territorialUnitType(splitWords[1])
                .capitalsMunicipality(splitWords[11])
                .build();
    }

    /**
     * Creates an {@code OldMunicipalityModel} from an Excel {@link Row} by mapping each cell to the respective fields.
     *
     * @param row the {@link Row} to create the model from.
     * @return a new {@link OldMunicipalityModel} object constructed from the row data.
     */
    private static OldMunicipalityModel createOldMunicipalityModel(Row row) {
        return OldMunicipalityModel.builder()
                .year(Integer.parseInt(row.getCell(0).getStringCellValue()))
                .municipalityCode(row.getCell(3).getStringCellValue())
                .municipalityName(row.getCell(4).getStringCellValue())
                .newMunicipalityCode(row.getCell(7).getStringCellValue())
                .build();
    }

    /**
     * Creates an {@code OldMunicipalityModel} from a CSV line, parsing each field from the comma-separated values.
     *
     * @param row the CSV line to create the model from.
     * @return a new {@link OldMunicipalityModel} object constructed from the CSV data.
     */
    private static OldMunicipalityModel createOldMunicipalityModel(String row) {
        final String[] rowSplit = row.split(SEMICOLON.separator());
        return OldMunicipalityModel.builder()
                .year(Integer.parseInt(rowSplit[0]))
                .municipalityCode(Integer.valueOf(rowSplit[3]).toString()) // this line can be string?
                .municipalityName(rowSplit[4])
                .newMunicipalityCode(Integer.valueOf(rowSplit[7]).toString())// this line can be string?
                .build();
    }

    /**
     * Processes a ZIP input stream containing municipality data files and extracts the relevant information
     * based on the provided {@code ConstructObjectFromFile} configuration.
     *
     * @param fis                     the byte array input stream containing the ZIP file.
     * @param constructObjectFromFile a configuration that provides methods for handling file extraction,
     *                                object construction, and filtering.
     * @param <L>                     the type of the model objects to create (e.g., {@code MunicipalityModel}).
     * @return a {@link Set} of models constructed from the files in the ZIP archive.
     * @throws IOException if an error occurs while reading the ZIP input stream.
     */
    private static <L> Set<L> getZipInputStream(ByteArrayInputStream fis, ConstructObjectFromFile<L> constructObjectFromFile) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(fis, StandardCharsets.ISO_8859_1)) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.getName().endsWith(constructObjectFromFile.getFileExtension())) {
                    return constructObjectFromFile.getFunctionRead().apply(zipInputStream, constructObjectFromFile); // Process the file
                }
                zipInputStream.closeEntry(); // Close the current ZIP entry
                zipEntry = zipInputStream.getNextEntry(); // Move to the next entry
            }
            return Collections.emptySet(); // Return an empty set if the target file is not found
        }
    }

    /**
     * Creates a configuration object for processing old municipality data from a CSV file in a ZIP archive.
     * <p>
     * This method returns a {@link ConstructObjectFromFile} object that specifies how the old municipality data should
     * be processed. It provides the following:
     * - A function to map each line of the CSV file to an {@link OldMunicipalityModel}.
     * - A filter to ensure data validity (e.g., ensuring necessary fields are not blank).
     * - The number of lines to skip at the beginning of the file (for header rows).
     * - The file extension to identify the relevant CSV file inside the ZIP archive.
     * <p>
     * The configuration returned by this method is used by {@link MunicipalityInformation#getZipInputStream(ByteArrayInputStream, ConstructObjectFromFile)}
     * to process the CSV data and create a set of {@link OldMunicipalityModel} objects.
     *
     * @return a {@link ConstructObjectFromFile<OldMunicipalityModel>} that contains the configuration for processing the old municipality data CSV.
     */
    private static ConstructObjectFromFile<OldMunicipalityModel> constructOldObjectFromFile() {
        return ConstructObjectFromFile.<OldMunicipalityModel>builder()
                // Specifies how to construct an OldMunicipalityModel from a row of the CSV file
                .constructObject(MunicipalityInformation::createOldMunicipalityModel)
                // Function to read the CSV and apply the construction logic for OldMunicipalityModel
                .functionRead(functionReadCsv)
                // A predicate to filter out invalid or incomplete lines from the CSV (lines where certain fields are blank)
                .lineFilter(LINE_FILTER)
                // Defines the number of header lines to skip in the CSV file (usually the first 2)
                .linesToSkip(LINE_TO_SKIP_OLD_MUNICIPALITY.value())
                // The file extension that identifies the relevant CSV file inside the ZIP archive (in this case, .csv)
                .fileExtension(CSV_END_FILE.text())
                .build();
    }

    /**
     * Creates and returns a {@link ConstructObjectFromFile} configuration for processing and parsing current
     * municipality data from a CSV file within a ZIP archive.
     * <p>
     * This method builds the configuration required to read a CSV file, apply the necessary parsing logic,
     * and filter out invalid lines to construct {@link MunicipalityModel} objects. The configuration includes:
     *
     * <ul>
     *     <li>A function to map each CSV row into a {@link MunicipalityModel} object.</li>
     *     <li>A function to read the CSV file and apply the object construction logic for the current municipalities.</li>
     *     <li>A predicate to filter out lines that contain missing or invalid data (e.g., blank fields).</li>
     *     <li>The number of header rows to skip (usually the first two rows in CSV files).</li>
     *     <li>The file extension to look for when identifying the relevant file inside the ZIP archive (e.g., ".csv").</li>
     * </ul>
     * <p>
     * This configuration is then used by methods like {@link MunicipalityInformation#getZipInputStream(ByteArrayInputStream, ConstructObjectFromFile)}
     * to process and parse the actual data from the ZIP file.
     *
     * @return A fully configured {@link ConstructObjectFromFile} instance for reading and processing the current municipality data from a CSV file.
     */
    private static ConstructObjectFromFile<MunicipalityModel> constructObjectFromFile() {
        return ConstructObjectFromFile.<MunicipalityModel>builder()
                // Specifies how to construct a MunicipalityModel object from a CSV row
                .constructObject(MunicipalityInformation::createMunicipalityModel)
                // Defines the function to read the CSV file and apply the object construction logic
                .functionRead(functionReadCsv2)
                // Specifies the predicate that filters out invalid lines, e.g., lines with missing required fields
                .linesToSkip(LINE_TO_SKIP_CURRENT_MUNICIPALITY.value()) // Skip the first few lines (headers)
                // Defines the file extension for the relevant file inside the ZIP archive (in this case, .csv)
                .fileExtension(CSV_END_FILE.text())
                .build();
    }


    /**
     * Reads current municipality data from a remote server, fetches the data in a ZIP file format,
     * processes the CSV file inside the ZIP, and returns a map of municipality codes to corresponding
     * {@code MunicipalityModel} objects.
     *
     * @return a {@code Uni<Map<String, MunicipalityModel>>} representing a map of municipality codes to municipality models.
     */
    public Uni<Map<String, MunicipalityModel>> readCurrentMunicipalities() {
        return webClientVerticle.getWebClient().getAbs(URL_CURRENT_MUNICIPALITY.text())
                .putHeader("content-type", "application/zip")
                .send()
                .map(bufferHttpResponse -> {
                    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferHttpResponse.body().getBytes())) {
                        Supplier<Set<MunicipalityModel>> municipalityStream = () -> getZipInputStream(byteArrayInputStream, constructObjectFromFile());
                        return aggregateStreamToMap(municipalityStream.get(), MunicipalityModel::municipalityCode);
                    } catch (IOException e) {
                        e.printStackTrace(); // Log any exception encountered
                    }
                    return Collections.emptyMap(); // Return an empty map if an error occurs
                });
    }

    /**
     * Reads old municipality data from a remote server, fetches the data in a ZIP file format,
     * processes the CSV file inside the ZIP, and returns a map of municipality codes to sets of
     * {@code OldMunicipalityModel} objects.
     *
     * @return a {@code Uni<Map<String, Set<OldMunicipalityModel>>>} representing a map of municipality codes to sets of old municipality models.
     */
    public Uni<Map<String, Set<OldMunicipalityModel>>> readOldMunicipalities() {
        return webClientVerticle.getWebClient().getAbs(URL_OLD_MUNICIPALITY.text())
                .putHeader("content-type", "application/zip")
                .send()
                .map(bufferHttpResponse -> {
                    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferHttpResponse.body().getBytes())) {
                        Supplier<Set<OldMunicipalityModel>> oldMunicipalityStream = () -> getZipInputStream(byteArrayInputStream, constructOldObjectFromFile());
                        return aggregateStreamToMap(oldMunicipalityStream.get(), OldMunicipalityModel::newMunicipalityCode, Collectors.mapping(value -> value, Collectors.toSet()));
                    } catch (IOException e) {
                        e.printStackTrace(); // Log any exception encountered
                    }
                    return Collections.emptyMap(); // Return an empty map if an error occurs
                });
    }
}
