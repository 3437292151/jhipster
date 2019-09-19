package com.yu.mybatisplus.web.rest.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">GitHub API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class MybatisPaginationUtil {

    private MybatisPaginationUtil() {
    }

    public static <T> HttpHeaders generatePaginationHttpHeaders(IPage<T> pageInfo, String baseUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(pageInfo.getTotal()));
        String link = "";
        if ((pageInfo.getCurrent()) < pageInfo.getPages()) {
            link = "<" + generateUri(baseUrl, pageInfo.getCurrent() + 1, pageInfo.getCurrent()) + ">; rel=\"next\",";
        }
        // prev link
        if ((pageInfo.getCurrent()) > 1) {
            link += "<" + generateUri(baseUrl, pageInfo.getCurrent() - 1, pageInfo.getCurrent()) + ">; rel=\"prev\",";
        }
        // last and first link
        long lastPage = 1;
        if (pageInfo.getPages() > 1) {
            lastPage = pageInfo.getPages();
        }
        link += "<" + generateUri(baseUrl, lastPage, pageInfo.getSize()) + ">; rel=\"last\",";
        link += "<" + generateUri(baseUrl, 1, pageInfo.getSize()) + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;

    }

    private static String generateUri(String baseUrl, long page, long size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }
}
