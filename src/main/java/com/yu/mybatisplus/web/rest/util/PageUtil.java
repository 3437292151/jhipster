package com.yu.mybatisplus.web.rest.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageUtil {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static <E> Page<E> pageableToPage(Pageable pageable) {
        Page<E> eiPage = new Page<E>(pageable.getPageNumber(), pageable.getPageSize());
        if (pageable.getSort() != null && !org.apache.commons.lang3.StringUtils.equals(pageable.getSort().toString(), "UNSORTED")){
            List<OrderItem> orderItemList = new ArrayList<>();
            String[] split = pageable.getSort().toString().split(",");
            for (String str : split){
                OrderItem orderItem = new OrderItem();
                String[] order = str.split(": ");
                orderItem.setColumn(humpToLine(order[0]));
                if (org.apache.commons.lang3.StringUtils.equals(order[1].toLowerCase(), "desc")){
                    orderItem.setAsc(false);
                }
                orderItemList.add(orderItem);
            }
            eiPage.setOrders(orderItemList);
        }
        return eiPage;
    }

    public static String humpToLine(String humStr){
        Matcher humpMatcher = humpPattern.matcher(humStr);
        StringBuffer sb = new StringBuffer();
        while(humpMatcher.find()){
            humpMatcher.appendReplacement(sb, "_" + humpMatcher.group(0).toLowerCase());
        }
        humpMatcher.appendTail(sb);
        return sb.toString();
    }
}
