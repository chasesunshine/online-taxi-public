package com.mashibing.apipassenger.interceptor;

import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * 拦截器代码编写
 *
* @author 马佳健
* @date 2025/1/2
* @description
*/
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 1. 请求头中获取 Authorization
     * 2. 解析token，校验token
     * 3. 按照规则，生成tokenKey
     * 4. 从redis中取出token，校验token
     * 5. 返回拦截器校验token结果
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resutltString = "";

        String token = request.getHeader("Authorization");
        // 解析token
        TokenResult tokenResult = JwtUtils.checkToken(token);

        if (tokenResult == null){
            resutltString = "access token invalid";
            result = false;
        }else{
            // 拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();

            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity, TokenConstants.ACCESS_TOKEN_TYPE);
            // 从redis中取出token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if ((StringUtils.isBlank(tokenRedis))  || (!token.trim().equals(tokenRedis.trim()))){
                resutltString = "access token invalid";
                result = false;
            }
        }

        if (!result){
            try(PrintWriter out = response.getWriter()){
                out.print(JSONObject.fromObject(ResponseResult.fail(resutltString)).toString());
            }catch (Exception e){
                // 记录异常信息或采取其他错误处理措施
                e.printStackTrace();
                // 可能需要设置适当的HTTP状态码来表示错误
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        return result;
    }
}
