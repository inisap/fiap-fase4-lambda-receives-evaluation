resource "aws_lambda_function" "avaliacao" {
  function_name = "avaliacao-feedback-${var.environment}"

  role = aws_iam_role.lambda_exec.arn

  runtime = "java17"
  handler = "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler"

  architectures = ["x86_64"]

  s3_bucket = data.terraform_remote_state.infra.outputs.artifacts_bucket_name
  s3_key    = "avaliacao/v0.0.1/function.zip"

  memory_size = 512
  timeout     = 10

  environment {
    variables = {
      DYNAMODB_TABLE = data.terraform_remote_state.infra.outputs.dynamodb_avaliacoes_name
      SNS_TOPIC_ARN  = aws_sns_topic.avaliacoes_criticas.arn

      #Cognito
      QUARKUS_OIDC_AUTH_SERVER_URL = "https://cognito-idp.us-east-1.amazonaws.com/${aws_cognito_user_pool.avaliacao_pool.id}"
      QUARKUS_OIDC_CLIENT_ID       = aws_cognito_user_pool_client.avaliacao_client.id
    }
  }
}

resource "aws_lambda_permission" "api_gateway" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.avaliacao.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.avaliacao_api.execution_arn}/*/*"
}
