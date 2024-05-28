package com.task01;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.RetentionSetting;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import javax.ws.rs.Path;
import java.util.LinkedHashMap;
import java.util.Map;


@Path("hello")
@LambdaUrlConfig(
        authType = AuthType.NONE,
        invokeMode = InvokeMode.BUFFERED
)
@LambdaHandler(lambdaName = "hello_world",
        roleName = "hello_world-role",
        isPublishVersion = true,
        logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class HelloWorld implements RequestHandler<LinkedHashMap<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(LinkedHashMap<String, Object> request, Context context) {
        String rawPath = (String) request.get("rawPath");

        if ("/hello".equals(rawPath) || "//hello".equals(rawPath)) {
            return Map.of(
                    "statusCode", 200,
                    "body", Map.of(
                            "statusCode", 200,
                            "message", "Hello from Lambda"
                    )
            );
        } else {
            return Map.of(
                    "statusCode", 404,
                    "body", Map.of(
                            "message", "Bad request syntax or unsupported method. Request path: " + request.get(rawPath) + " . HTTP method: GET"
                    )
            );
        }
    }
}
