package com.kingpiggy.study.productordersystem.application;

import static com.kingpiggy.study.productordersystem.util.StringUtil.isNumeric;

import com.kingpiggy.study.productordersystem.service.OrderService;
import com.kingpiggy.study.productordersystem.service.ProductService;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("!test")
@RequiredArgsConstructor
@Component
public class OrderSystemApplication implements CommandLineRunner {

    private final ConfigurableApplicationContext context;

    private final ProductService productService;
    private final OrderService orderService;

    private static final String WORD_QUIT_CASE_1 = "q";
    private static final String WORD_QUIT_CASE_2 = "quit";
    private static final String WORD_EMPTY = " ";
    private static final String WORD_ORDER_CASE_1 = "o";
    private static final String WORD_ORDER_CASE_2 = "order";

    private static final Map<Integer, Integer> orderMap = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        readProductCSV();
        receiveCommand();
    }

    /**
     * read product csv file and insert to product table
     *
     * @author KingPiggy
     * @throws Exception
     */
    public void readProductCSV() throws Exception {
        log.info("[read-product-csv] start");
        productService.saveAllProductByVOList();
        log.info("[read-product-csv] end");
    }

    /**
     * Receive command
     *
     * @author KingPiggy
     */
    public void receiveCommand() {
        log.info("[receive-command] start");
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("??????(o[order]: , q[quit]: ) : ");
            String word = in.nextLine();
            if (word.equals(WORD_QUIT_CASE_1) || word.equals(WORD_QUIT_CASE_2)) {
                quitApplication();
            }

            if (word.equals(WORD_ORDER_CASE_1) || word.equals(WORD_ORDER_CASE_2)) {
                Map<Integer, Integer> productMap = productService.getAllProduct();

                while(true) {
                    System.out.print("???????????? : ");
                    String productNoWord = in.nextLine();

                    if(!isNumeric(productNoWord) && !productNoWord.equals(WORD_EMPTY)) {
                        System.out.println("????????? ????????????(??????)??? ??????????????????.");
                        continue;
                    }

                    if(isNumeric(productNoWord)  && !productNoWord.equals(WORD_EMPTY)) {
                        if(productMap.get(Integer.valueOf(productNoWord)) == null) {
                            System.out.println("????????? ???????????? ????????????.");
                            continue;
                        }
                    }

                    System.out.print("?????? : ");
                    String quantityWord = in.nextLine();
                    if(productNoWord.equals(WORD_EMPTY) && quantityWord.equals(WORD_EMPTY)) {
                        try{
                            Long ordersId = orderService.order(orderMap);
                            orderService.printOrderHistory(ordersId);
                        } catch (Exception e) {
                            log.error("[error]", e);
                        }
                        orderMap.clear();
                        break;
                    }

                    if(productNoWord.equals(WORD_EMPTY)) {
                        System.out.println("????????????(??????)??? ???????????? ????????????.");
                        continue;
                    }

                    if(!isNumeric(quantityWord)) {
                        System.out.println("????????? ??????(??????)??? ??????????????????.");
                        continue;
                    }

                    if(isNumeric(quantityWord)) {
                        Integer stock = productMap.get(Integer.valueOf(productNoWord));
                        if (stock < Integer.parseInt(quantityWord)) {
                            System.out.println("SoldOutException ??????. ????????? ???????????? ??????????????? ?????????.");
                            continue;
                        }
                    }

                    Integer productNo = Integer.valueOf(productNoWord);
                    Integer orderQuantity = Integer.valueOf(quantityWord);
                    Integer quantity = orderMap.getOrDefault(productNo, 0);
                    orderMap.put(productNo, quantity + orderQuantity);
                }
            }
        }
    }

    public void quitApplication() {
        System.out.println("???????????? ?????? ???????????????.");
        log.info("[receive-command] end");
        System.exit(SpringApplication.exit(context));
    }

}
