package com.yanerwu.service;

import com.yanerwu.common.DbUtilsTemplate;
import com.yanerwu.entity.Information;
import com.yanerwu.phone.PhoneNumberGeo;
import com.yanerwu.phone.PhoneNumberInfo;
import com.yanerwu.vo.Bidder;
import com.yanerwu.vo.Borrow;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zuz
 * @Date 2017/9/1 11:19
 * @Description
 */
@Service
public class InformationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private DbUtilsTemplate rawTemplate;
    @Autowired
    private DbUtilsTemplate bookTemplate;
    private PhoneNumberGeo phoneNumberGeo;
    private ExecutorService exec;

    public InformationService() {
        phoneNumberGeo = new PhoneNumberGeo();
    }

    public void run(String startDate,String endDate) {
        exec = Executors.newFixedThreadPool(10);
        for (Map<String, Object> platMap : rawTemplate.find("select * from ProductData.shuju_plat_config where collect_method in ('接口','API') and plat_id not in ('niwodai','yooli','ppdai','iqianjin','lufax','4891','ppmoney','koudailc','jimubox','1309','2090','dianrong','34','my089')")) {
            exec.execute(() -> {
                long l = System.currentTimeMillis();
                RowProcessor processor = new BasicRowProcessor(new GenerousBeanProcessor());
                String platId = String.valueOf(platMap.get("plat_id"));
                MDC.put("book", platId);
                String borrowerSql = String.format("select * from borrow_list_%s where success_time_date>='%s' and success_time_date<'%s'", platId, startDate,endDate);
                List<Borrow> borrows = rawTemplate.find(Borrow.class, borrowerSql, processor);
                for (Borrow b : borrows) {
                    try {
                        String expireDate = "";
                        synchronized (this) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.MONTH, b.getLoanPeriod().intValue());
                            expireDate =df.format(calendar.getTime());
                        }
                        //bid
                        String bidderSql = String.format("select * from bid_record_%s where char_length(bidder_name)=11 and left(bidder_name,1)=1 and loan_id='%s'", platId,b.getLoanId());
                        List<Bidder> bidders = rawTemplate.find(Bidder.class, bidderSql, processor);
                        List<Information> informations = new ArrayList<>();
                        for (Bidder bid : bidders) {
                            PhoneNumberInfo p = null;
                            synchronized (this) {
                                p = phoneNumberGeo.lookup(bid.getBidderName());
                            }
                            if (null == p) {
                                continue;
                            }
                            Information i = new Information();
                            i.setSourceName(platId);
                            i.setAreaCode(p.getAreaCode());
                            i.setCity(p.getCity());
                            i.setPhone(p.getPhoneNumber());
                            i.setProvince(p.getProvince());
                            i.setType(2);
                            i.setZipCode(p.getZipCode());
                            i.setStartDate(b.getSuccessTimeDate());
                            i.setExpireDate(expireDate);
                            i.setAmount(bid.getAmountBid());
                            i.setLoanId(bid.getLoanId());
                            informations.add(i);
                        }
                        if (informations.size() > 0) {
                            bookTemplate.insert(informations);
                        }

                        //bor
                        if (!(b.getBorrower().length() == 11 && b.getBorrower().startsWith("1"))) {
                            continue;
                        }
                        if (!NumberUtils.isCreatable(b.getBorrower())) {
                            continue;
                        }
                        PhoneNumberInfo p = null;
                        synchronized (this) {
                            p = phoneNumberGeo.lookup(b.getBorrower());
                        }
                        if (null != p) {
                            Information bor = new Information();
                            bor.setSourceName(platId);
                            bor.setAreaCode(p.getAreaCode());
                            bor.setCity(p.getCity());
                            bor.setPhone(p.getPhoneNumber());
                            bor.setInterestRate(b.getInterestRate());
                            bor.setUseFor(b.getUseFor());
                            bor.setStartDate(b.getSuccessTimeDate().substring(0, 10));
                            bor.setExpireDate(expireDate);
                            bor.setProvince(p.getProvince());
                            bor.setType(1);
                            bor.setZipCode(p.getZipCode());
                            bor.setAmount(b.getAmount());
                            bor.setLoanId(b.getLoanId());
                            bookTemplate.insert(bor);
                        }
                        Thread.sleep(100);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
                long t = (System.currentTimeMillis() - l) / 1000;
                if(t>=10){
                    logger.info("处理完毕,耗时{}秒", (System.currentTimeMillis() - l) / 1000);
                }
            });
        }
        exec.shutdown();
        while (true) {
            if (exec.isTerminated()) {
                logger.info("=================> 处理结束");
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
}
