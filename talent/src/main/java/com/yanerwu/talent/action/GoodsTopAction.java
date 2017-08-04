package com.yanerwu.talent.action;

import com.yanerwu.common.BaseAction;
import com.yanerwu.common.Page;
import com.yanerwu.entity.GoodsTop;
import com.yanerwu.talent.service.GoodsTopService;
import com.yanerwu.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zuz
 * @version 1.0
 * @Description 人气商品
 */
@Component
@RequestMapping(value = "/GoodsTop")
public class GoodsTopAction extends BaseAction {

    //默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;
    //navTab rel="GoodsTop"
    private static final String NAV_TAB_REL = "GoodsTop";
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GoodsTopService goodsTopService;

    private GoodsTop goodsTop = new GoodsTop();

    private boolean bool = false;
    private String message = DWZ_STATUS_SUCCESS;

    /**
     * 执行搜索
     *
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String list(GoodsTop goodsTop, Page page) {
        attribute();
        page = goodsTopService.findPage(goodsTop, page);
        request.setAttribute("page", page);
        return "GoodsTop/list";
    }

    //https://detail.tmall.com/item.htm?id=?

    /**
     * 进入更新页面
     *
     * @return
     */
    @RequestMapping(value = "/info.html", method = RequestMethod.GET)
    public String infoShow(GoodsTop goodsTop) {

        goodsTop = goodsTopService.getById(goodsTop);
        request.setAttribute("entity", goodsTop);
        return "GoodsTop/info";
    }

    /**
     * 保存更新
     */
    @ResponseBody
    @RequestMapping(value = "/info.html", method = RequestMethod.POST)
    public String infoEdit(GoodsTop goodsTop) {
        try {
            if (Tools.isEmpty(goodsTop.getId())) {
                goodsTopService.save(goodsTop);
            } else {
                this.goodsTop = goodsTopService.getById(goodsTop);
                this.goodsTop.setGoodsId(goodsTop.getGoodsId());
                this.goodsTop.setTitle(goodsTop.getTitle());
                this.goodsTop.setTitleSimple(goodsTop.getTitleSimple());
                this.goodsTop.setPic(goodsTop.getPic());
                this.goodsTop.setCid(goodsTop.getCid());
                this.goodsTop.setOrgPrice(goodsTop.getOrgPrice());
                this.goodsTop.setPrice(goodsTop.getPrice());
                this.goodsTop.setIsTmall(goodsTop.getIsTmall());
                this.goodsTop.setSalesNum(goodsTop.getSalesNum());
                this.goodsTop.setDsr(goodsTop.getDsr());
                this.goodsTop.setSellerId(goodsTop.getSellerId());
                this.goodsTop.setCommissionJihua(goodsTop.getCommissionJihua());
                this.goodsTop.setCommissionQueqiao(goodsTop.getCommissionQueqiao());
                this.goodsTop.setJihuaLink(goodsTop.getJihuaLink());
                this.goodsTop.setJihuaShenhe(goodsTop.getJihuaShenhe());
                this.goodsTop.setIntroduce(goodsTop.getIntroduce());
                this.goodsTop.setQuanId(goodsTop.getQuanId());
                this.goodsTop.setQuanPrice(goodsTop.getQuanPrice());
                this.goodsTop.setQuanTime(goodsTop.getQuanTime());
                this.goodsTop.setQuanSurplus(goodsTop.getQuanSurplus());
                this.goodsTop.setQuanReceive(goodsTop.getQuanReceive());
                this.goodsTop.setQuanCondition(goodsTop.getQuanCondition());
                this.goodsTop.setQuanMlink(goodsTop.getQuanMlink());
                this.goodsTop.setQuanLink(goodsTop.getQuanLink());
            }
            goodsTopService.update(this.goodsTop);
            this.bool = true;
        } catch (Exception e) {
            message = String.format("%s 错误:[%s]", DWZ_STATUS_ERROR, e.getMessage());
            logger.error("", e);
        } finally {
            //记录日志
            //logUtil.put(request, "人气商品 > 修改", JSON.toJSONString(goodsTop), bool ? 0 : 1);
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, bool);
        }
    }


    /**
     * 删除对象
     */
    @ResponseBody
    @RequestMapping(value = "/delete.html", method = RequestMethod.POST)
    public String delete(Long[] items) {
        try {
            for (int i = 0; i < items.length; i++) {
                goodsTopService.delete(new GoodsTop(items[i]));
            }
            bool = true;
        } catch (Exception e) {
            message = String.format("%s 错误:[%s]", DWZ_STATUS_ERROR, e.getMessage());
            logger.error("", e);
        } finally {
            //返回信息
            return retImessage(bool ? DWZ_STATUS_SUCCESS_CODE : DWZ_STATUS_ERROR_CODE, message, false);
        }
    }
}