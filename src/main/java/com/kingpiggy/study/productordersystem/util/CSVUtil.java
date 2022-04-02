package com.kingpiggy.study.productordersystem.util;

import com.kingpiggy.study.productordersystem.data.vo.ProductVO;
import com.opencsv.CSVReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CSVUtil {

    private static final String CSV_PRODUCT_ITEMS_PATH = "./csv/product_items.csv";
    private static final int CSV_HEADER = 1;

    public List<ProductVO> getProductVOListByCSV() throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(CSV_PRODUCT_ITEMS_PATH));
        CSVReader csvReader = new CSVReader(reader);
        csvReader.skip(CSV_HEADER);

        List<ProductVO> voList = new ArrayList<>();

        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            voList.add(ProductVO.builder()
                .productNo(Integer.valueOf(nextRecord[0]))
                .name(nextRecord[1])
                .price(Integer.valueOf(nextRecord[2]))
                .stock(Integer.valueOf(nextRecord[3]))
                .build());
        }

        return voList;
    }

}
