package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpELMessageResolver implements LogMessageResolver {
    protected final ExpressionParser parser = new SpelExpressionParser();
    protected final ParserContext parserContext = new TemplateParserContext("{", "}");

    @Override
    public String render(LogActivityContext logActivityContext) {
        StandardEvaluationContext context = new StandardEvaluationContext(logActivityContext.getParams());
        context.addPropertyAccessor(new MapAccessor());
        try {
            return parser.parseExpression(logActivityContext.getTemplate(), parserContext).getValue(context, String.class);
        }
        catch (Exception e) {
            log.warn(e.getMessage(), e);
            return "";
        }
    }
}
