output "api_url" {
  value = aws_apigatewayv2_api.avaliacao_api.api_endpoint
}

output "cognito_user_pool_id" {
  value = aws_cognito_user_pool.avaliacao_pool.id
}

output "cognito_client_id" {
  value = aws_cognito_user_pool_client.avaliacao_client.id
}
