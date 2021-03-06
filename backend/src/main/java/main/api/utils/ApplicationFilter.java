package main.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static com.google.common.base.Predicates.*;
import static springfox.documentation.builders.PathSelectors.*;

@Configuration
public class ApplicationFilter implements Filter {

    @SuppressWarnings("unchecked")
    private static final Predicate<String> SWAGGER_URL = or(
        ant("/v2/api-docs"),
        ant("/swagger-resources"),
        ant("/swagger-resources/**"),
        ant("/configuration/ui"),
        ant("/configuration/security"),
        ant("/swagger-ui.html"),
        ant("/webjars/**")
    );

    private static RandomGenerator generator = new RandomGenerator(20);
    private static HashMap<Integer,String> tokens = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (
            !request.getRequestURI().equals("/users/register") &&
                !request.getRequestURI().equals("/users/login") &&
                !SWAGGER_URL.apply(request.getRequestURI())
        ) {
            String token = request.getParameter("token");
            int id;
            if(token == null || token.isEmpty() || (id = validateToken(token)) == -1) {
                supportException((HttpServletResponse) servletResponse);
                return;
            }
            request.setAttribute("userIdToken",id);
        }
        filterChain.doFilter(request,servletResponse);
    }

    private void supportException(HttpServletResponse response) throws IOException {
        ApplicationException ex = new ApplicationException(ExceptionCode.USER_NOT_LOGGED);
        response.setStatus(ex.getCode().getStatus());
        response.getWriter().write(new ObjectMapper().writeValueAsString(ex));
    }

    private int validateToken(String token) {
        int id = Integer.parseInt(token.split(">")[0]);
        if(!tokens.containsKey(id) || !tokens.get(id).equals(token))
            return -1;
        return id;
    }

    public static void removeToken(int id){
        tokens.remove(id);
    }

    @Override
    public void destroy() {}

    public static synchronized String generateToken(int id){
        if (tokens.containsKey(id)) {
            return tokens.get(id);
        } else {
            String token = id + ">" + generator.nextValue();
            tokens.put(id, token);
            return token;
        }
    }


    private static class RandomGenerator {

        /**
         * Generate a random string.
         */
        public String nextValue() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        static final String lower = upper.toLowerCase(Locale.ROOT);

        static final String digits = "0123456789";

        static final String alphanum = upper + lower + digits;

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        RandomGenerator(int length, Random random, String symbols) {
            if (length < 1) throw new IllegalArgumentException();
            if (symbols.length() < 2) throw new IllegalArgumentException();
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }

        /**
         * Create an alphanumeric string generator.
         */
        RandomGenerator(int length, Random random) {
            this(length, random, alphanum);
        }

        RandomGenerator(int length) {
            this(length, new SecureRandom());
        }

        public RandomGenerator() {
            this(21);
        }

    }
}
