package com.ducami.ducamiproject.domain.admin.log.resolver;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

public abstract class DefaultActivityResolver implements LogActivityResolver {
    protected final ExpressionParser parser = new SpelExpressionParser();
    protected final ParserContext parserContext = new TemplateParserContext("{", "}");

    @Override
    public String resolve(Map<String, Object> params, String template) {
        StandardEvaluationContext context = new StandardEvaluationContext(params);
        context.addPropertyAccessor(new MapAccessor());
        try {
            return parser.parseExpression(template, parserContext).getValue(context, String.class);
        }
        catch (Exception e) {
            return template;
        }
    }


}
