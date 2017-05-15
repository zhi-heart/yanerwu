package com.yanerwu.action;

import com.yanerwu.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Zuz
 * @Date 2017/5/15 15:10
 * @Description
 */
@Controller
public class OpenAction {

    @Autowired
    protected HttpServletResponse response;

    @ResponseBody
    @RequestMapping("/open/movie-word.html")
    public String movieWord() {
        StringBuffer sb = new StringBuffer();
        for (String s : Cache.movieWordSet) {
            sb.append(s.trim());
            sb.append("\n");
        }

        String dateStr = new SimpleDateFormat("yyyyMMddHH").format(new Date());

        response.addHeader("Last-Modified", dateStr);
        response.addHeader("ETag", dateStr);

        return sb.toString();
    }
}
