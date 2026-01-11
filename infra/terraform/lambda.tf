resource "aws_lambda_function" "avaliacao" {
  function_name = "avaliacao-feedback-${var.environment}"

  role = aws_iam_role.lambda_exec.arn

  runtime = "java17"
  handler = "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler"

  architectures = ["x86_64"]

  s3_bucket = data.terraform_remote_state.infra.outputs.artifacts_bucket_name
  s3_key    = "avaliacao/v0.0.3/function.zip"

  memory_size = 512
  timeout     = 10

  environment {
    variables = {
      DYNAMODB_TABLE = data.terraform_remote_state.infra.outputs.dynamodb_avaliacoes_name
      SNS_TOPIC_ARN  = aws_sns_topic.avaliacoes_criticas.arn
    }
  }
}
