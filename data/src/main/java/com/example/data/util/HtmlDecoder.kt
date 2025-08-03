package com.example.data.util

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object HtmlDecoder {
    
    fun decodeHtmlEntities(text: String): String {
        return text
            .replace("&quot;", "\"")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#039;", "'")
            .replace("&apos;", "'")
            .replace("&ldquo;", """)
            .replace("&rdquo;", """)
            .replace("&lsquo;", "'")
            .replace("&rsquo;", "'")
            .replace("&hellip;", "...")
            .replace("&mdash;", "—")
            .replace("&ndash;", "–")
    }
} 