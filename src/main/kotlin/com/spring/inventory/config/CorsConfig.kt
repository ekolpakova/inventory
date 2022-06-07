package com.spring.inventory.config

import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse


@Component
class CorsConfig: Filter {
    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        val response = res as (HttpServletResponse)
        response.setHeader("Access-Control-Allow-Origin", "https://inventory-react.netlify.app")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, UPDATE")
        response.setHeader("Access-Control-Max-Age", "3600")
        //response.setHeader("Access-Control-Allow-Headers", "x-requested-with")
        response.setHeader("Access-Control-Allow-Headers", "Authorization")
        chain!!.doFilter(req, res)
    }
}
