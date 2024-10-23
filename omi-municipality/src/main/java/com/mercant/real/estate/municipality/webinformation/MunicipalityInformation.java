package com.mercant.real.estate.municipality.webinformation;

import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.model.MunicipalityModel;
import com.mercant.real.estate.municipality.model.OldMunicipalityModel;
import io.smallrye.mutiny.Uni;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.mercant.real.estate.municipality.utils.Constant.URL_CURRENT_MUNICIPALITY;
import static com.mercant.real.estate.municipality.utils.Constant.URL_OLD_MUNICIPALITY;

/**
 * MunicipalityInformation is responsible for handling operations related
 * to municipality data, including reading municipality information from
 * Excel files contained within ZIP archives.
 *
 * <p>This class provides methods to extract municipality data from ZIP files,
 * process Excel sheets, and return structured data in the form of
 * {@link MunicipalityModel} and {@link OldMunicipalityModel} objects.</p>
 *
 * <p>It utilizes a {@link WebClientVerticle} instance to make HTTP requests
 * for current and old municipality data.</p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-10-21
 */
public final class MunicipalityInformation {

    private final WebClientVerticle webClientVerticle;

    /**
     * Constructs a MunicipalityInformation instance with the specified
     * WebClientVerticle for making HTTP requests.
     *
     * @param webClientVerticle the WebClientVerticle instance used for HTTP operations.
     */
    public MunicipalityInformation(WebClientVerticle webClientVerticle) {
        this.webClientVerticle = webClientVerticle;
    }

    /**
     * Reads data from an Excel file contained within a ZIP input stream and maps
     * it to a list of objects using the provided mapping function.
     *
     * <p>This method opens the specified ZIP input stream, retrieves the first sheet
     * from the Excel workbook, and processes its rows to create a list of objects
     * of type T. It skips the first two rows, which are typically headers.</p>
     *
     * @param file     the ZIP input stream containing the Excel file.
     * @param function a mapping function that converts a Row into an object of type T.
     * @param <T>      the type of objects returned in the list.
     * @return a list of objects of type T created from the Excel data.
     * Returns an empty list if an error occurs during reading or processing.
     */
    public static <T> List<T> readExcelFile(ZipInputStream file, Function<Row, T> function) {
        try (XSSFWorkbook wb = new XSSFWorkbook(file)) {
            XSSFSheet sheet = wb.getSheetAt(0); // Creating a Sheet object to retrieve data
            // Iterating over the Excel file
            Iterable<Row> iterable = sheet::rowIterator;
            return StreamSupport.stream(iterable.spliterator(), false)
                    .skip(2) // Skip header rows
                    .map(function) // Use the provided function to map rows to objects
                    .toList();
        } catch (Exception e) {
            e.printStackTrace(); // Log exception to console
        }
        return List.of(); // Return an empty list in case of an error
    }

    /**
     * Creates a MunicipalityModel object from a given row in the Excel sheet.
     *
     * <p>This method extracts values from specific cells of the row and constructs
     * a MunicipalityModel object using the extracted data.</p>
     *
     * @param row the row from which to extract municipality data.
     * @return a MunicipalityModel instance populated with data from the row.
     */
    private static MunicipalityModel createMunicipalityModel(Row row) {
        return MunicipalityModel.builder()
                .regionCode(row.getCell(0).getStringCellValue())
                .provinceCode(row.getCell(2).getStringCellValue())
                .municipalityCode(row.getCell(15).getStringCellValue())
                .municipalitySigle(row.getCell(3).getStringCellValue()) //TODO: Fixed index for municipalitySigle, Update: search the municipality code
                .municipalityName(row.getCell(6).getStringCellValue())
                .regionName(row.getCell(10).getStringCellValue())
                .cadastralCode(row.getCell(19).getStringCellValue())
                .territorialUnitType(row.getCell(1).getStringCellValue())
                .capitalsMunicipality(row.getCell(11).getStringCellValue())
                .build();
    }

    /**
     * Creates an OldMunicipalityModel object from a given row in the Excel sheet.
     *
     * <p>This method currently constructs an OldMunicipalityModel object,
     * but it needs to be populated with relevant data.</p>
     *
     * @param row the row from which to extract old municipality data.
     * @return an OldMunicipalityModel instance populated with data from the row.
     */
    private static OldMunicipalityModel createOldMunicipalityModel(Row row) {
        return OldMunicipalityModel.builder()
                .year(Integer.parseInt(row.getCell(0).getStringCellValue()))
                .municipalityCode(row.getCell(3).getStringCellValue())
                .municipalityName(row.getCell(4).getStringCellValue())
                .newMunicipalityCode(Integer.parseInt(row.getCell(7).getStringCellValue()))
                .build();
    }

    /**
     * Extracts and processes the contents of a ZIP input stream to retrieve
     * data from an Excel file, using the provided mapping function.
     *
     * <p>This method reads through the ZIP entries to find an Excel file
     * (with .xlsx extension) and calls the readExcelFile method to process it.
     * If no suitable files are found, it returns an empty list.</p>
     *
     * @param fis      the ByteArrayInputStream representing the ZIP file.
     * @param function a mapping function that converts a Row into an object of type T.
     * @param <T>      the type of objects returned in the list.
     * @return a list of objects of type T extracted from the Excel file.
     * @throws IOException if an I/O error occurs while processing the ZIP file.
     */
    private static <T> List<T> getZipInputStream(ByteArrayInputStream fis, Function<Row, T> function) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(fis)) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.getName().endsWith(".xlsx")) {
                    return readExcelFile(zipInputStream, function); // Read the Excel file and return the data
                }
                zipInputStream.closeEntry(); // Close the current ZIP entry
                zipEntry = zipInputStream.getNextEntry(); // Move to the next entry
            }
            return Collections.emptyList(); // Return an empty list if no .xlsx file is found
        }
    }

    /**
     * Reads current municipalities from an external source via HTTP and processes
     * the response to extract municipality data from a ZIP file containing an Excel sheet.
     *
     * <p>This method makes an asynchronous HTTP GET request to retrieve a ZIP file
     * containing municipality data, then extracts and processes it to return a list
     * of MunicipalityModel instances.</p>
     *
     * @return a Uni containing a list of MunicipalityModel instances representing
     * the current municipalities. Returns an empty list if an error occurs
     * during the process.
     */
    public Uni<List<MunicipalityModel>> readCurrentMunicipalities() {
        return webClientVerticle.getWebClient().getAbs(URL_CURRENT_MUNICIPALITY)
                .putHeader("content-type", "application/zip")
                .send()
                .map(bufferHttpResponse -> {
                    // Buffer for reading and writing data to file
                    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferHttpResponse.body().getBytes())) {
                        return getZipInputStream(byteArrayInputStream, MunicipalityInformation::createMunicipalityModel);
                    } catch (IOException e) {
                        e.printStackTrace(); // Log exception to console
                    }
                    return Collections.emptyList(); // Return an empty list in case of an error
                });
    }

    /**
     * Reads old municipalities from an external source via HTTP and processes
     * the response to extract old municipality data from a ZIP file containing an Excel sheet.
     *
     * <p>This method makes an asynchronous HTTP GET request to retrieve a ZIP file
     * containing old municipality data, then extracts and processes it to return a list
     * of OldMunicipalityModel instances.</p>
     *
     * @return a Uni containing a list of OldMunicipalityModel instances representing
     * the old municipalities. Returns an empty list if an error occurs
     * during the process.
     */
    public Uni<List<OldMunicipalityModel>> readOldMunicipalities() {
        return webClientVerticle.getWebClient().get(URL_OLD_MUNICIPALITY)
                .putHeader("content-type", "application/zip")
                .send()
                .map(bufferHttpResponse -> {
                    // Buffer for reading and writing data to file
                    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferHttpResponse.body().getBytes())) {
                        return getZipInputStream(byteArrayInputStream, MunicipalityInformation::createOldMunicipalityModel);
                    } catch (IOException e) {
                        e.printStackTrace(); // Log exception to console
                    }
                    return Collections.emptyList(); // Return an empty list in case of an error
                });
    }
}

