package com.mercant.real.estate.municipality.webinformation;

import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import io.smallrye.mutiny.Uni;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.mercant.real.estate.municipality.utils.Constant.URL_CURRENT_MUNICIPALITY;
import static com.mercant.real.estate.municipality.utils.Constant.URL_OLD_MUNICIPALITY;

public final class MunicipalityInformation {
    private final WebClientVerticle webClientVerticle;

    public MunicipalityInformation(WebClientVerticle webClientVerticle) {
        this.webClientVerticle = webClientVerticle;
    }

    public static ByteArrayInputStream downloadToExcel2(String file) {
        try {

            InputStream resourceAsStream = new FileInputStream(PROXY_FORMAT_LOCAL);
            Workbook workBook = new XSSFWorkbook(resourceAsStream);
            Sheet sheet = workBook.getSheetAt(0);

            // Data
            int rowIdx = 1;
            for (List<String> stringList : dataList) {
                Row row = sheet.createRow(rowIdx++);
                System.out.println("row = " + row);

                for (int i = 0; i < stringList.size(); i++) {
                    row.createCell(i).setCellValue(stringList.get(i));
                }
            }
            workBook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    private static byte[] getZipInputStream(ByteArrayInputStream fis) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry ze = zis.getNextEntry();
            byte[] allBytes = new byte[0];
            while (ze != null) {

                String fileName = ze.getName();
                allBytes = zis.readAllBytes();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            return allBytes;
        }
    }

    public Uni<String> readCurrentMunicipalities() {
        return webClientVerticle.getWebClient().getAbs(URL_CURRENT_MUNICIPALITY)
                .putHeader("content-type", "application/zip")
                .send()
                .map(bufferHttpResponse -> {
                    //buffer for read and write data to file

                    byte[] buffer = new byte[1024];
                    try (ByteArrayInputStream fis = new ByteArrayInputStream(bufferHttpResponse.body().getBytes())) {
                        byte[] zis = getZipInputStream(fis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                });
    }

    public Uni<String> readOldMunicipalities() {
        return webClientVerticle.getWebClient().get(URL_OLD_MUNICIPALITY)
                .send()
                .map(bufferHttpResponse -> "");//TODO:COMPLETE THIS METHOD
    }
}
