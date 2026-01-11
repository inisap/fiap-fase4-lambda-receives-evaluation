resource "aws_apigatewayv2_api" "avaliacao_api" {
  name          = "avaliacao-api"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_authorizer" "jwt_authorizer" {
  api_id           = aws_apigatewayv2_api.avaliacao_api.id
  authorizer_type  = "JWT"
  identity_sources = ["$request.header.Authorization"]
  name             = "avaliacao-jwt-authorizer"

  jwt_configuration {
    issuer   = "https://cognito-idp.us-east-1.amazonaws.com/${aws_cognito_user_pool.avaliacao_pool.id}"
    audience = [aws_cognito_user_pool_client.avaliacao_client.id]
  }
}

resource "aws_apigatewayv2_integration" "lambda_integration" {
  api_id                 = aws_apigatewayv2_api.avaliacao_api.id
  integration_type       = "AWS_PROXY"
  integration_uri        = aws_lambda_function.avaliacao.invoke_arn
  payload_format_version = "2.0"
}

resource "aws_apigatewayv2_route" "avaliacoes" {
  api_id    = aws_apigatewayv2_api.avaliacao_api.id
  route_key = "POST /avaliacoes"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"

  authorization_type = "JWT"
  authorizer_id      = aws_apigatewayv2_authorizer.jwt_authorizer.id
}

resource "aws_apigatewayv2_stage" "default" {
  api_id      = aws_apigatewayv2_api.avaliacao_api.id
  name        = "$default"
  auto_deploy = true
}
