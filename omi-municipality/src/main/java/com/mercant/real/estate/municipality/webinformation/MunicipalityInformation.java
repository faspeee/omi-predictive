package com.mercant.real.estate.municipality.webinformation;

import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.model.MunicipalityModel;
import io.smallrye.mutiny.Uni;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.mercant.real.estate.municipality.utils.Constant.URL_CURRENT_MUNICIPALITY;
import static com.mercant.real.estate.municipality.utils.Constant.URL_OLD_MUNICIPALITY;

public final class MunicipalityInformation {
    private final WebClientVerticle webClientVerticle;

    public MunicipalityInformation(WebClientVerticle webClientVerticle) {
        this.webClientVerticle = webClientVerticle;
    }

    public static List<MunicipalityModel> readExcelFile(ZipInputStream file) {
        try (XSSFWorkbook wb = new XSSFWorkbook(file)) {
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            //iterating over excel file
            Iterable<Row> iterable = sheet::rowIterator;
            return StreamSupport.stream(iterable.spliterator(), false)
                    .skip(2)
                    .map(MunicipalityInformation::createMunicipalityModel)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    private static MunicipalityModel createMunicipalityModel(Row row) {
        return MunicipalityModel.builder()
                .regionCode(row.getCell(0).getStringCellValue())
                .provinceCode(row.getCell(1).getStringCellValue())
                .municipalityCode(row.getCell(1).getStringCellValue())
                .municipalitySigle(row.getCell(1).getStringCellValue())
                .municipalityName(row.getCell(6).getStringCellValue())
                .regionName(row.getCell(1).getStringCellValue())
                .cadastralCode(row.getCell(1).getStringCellValue())
                .territorialUnitType(row.getCell(1).getStringCellValue())
                .capitalsMunicipality(row.getCell(11).getStringCellValue())
                .build();
    }

    private static List<MunicipalityModel> getZipInputStream(ByteArrayInputStream fis) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                if (ze.getName().endsWith(".xlsx")) {
                    String fileName = ze.getName();
                    return readExcelFile(zis);
                }//close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            return Collections.emptyList();
        }
    }

    public Uni<List<MunicipalityModel>> readCurrentMunicipalities() {
        return webClientVerticle.getWebClient().getAbs(URL_CURRENT_MUNICIPALITY)
                .putHeader("content-type", "application/zip")
                .send()
                .map(bufferHttpResponse -> {
                    //buffer for read and write data to file
                    try (ByteArrayInputStream fis = new ByteArrayInputStream(bufferHttpResponse.body().getBytes())) {
                        return getZipInputStream(fis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return Collections.emptyList();
                });
    }

    public Uni<String> readOldMunicipalities() {
        return webClientVerticle.getWebClient().get(URL_OLD_MUNICIPALITY)
                .send()
                .map(bufferHttpResponse -> "");//TODO:COMPLETE THIS METHOD
    }
}
