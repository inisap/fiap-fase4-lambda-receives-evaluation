# =========================
# IAM Role da Lambda
# =========================
resource "aws_iam_role" "lambda_exec" {
  name = "avaliacao-feedback-lambda-role-${var.environment}"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Principal = {
        Service = "lambda.amazonaws.com"
      }
      Action = "sts:AssumeRole"
    }]
  })

  tags = {
    Application = "avaliacao-feedback"
    Environment = var.environment
  }
}

# =========================
# CloudWatch Logs
# =========================
resource "aws_iam_role_policy_attachment" "basic_logs" {
  role       = aws_iam_role.lambda_exec.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

# =========================
# Policy específica do projeto
# =========================
resource "aws_iam_role_policy" "lambda_policy" {
  name = "avaliacao-feedback-policy-${var.environment}"
  role = aws_iam_role.lambda_exec.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [

      # DynamoDB
      {
        Effect = "Allow"
        Action = [
          "dynamodb:PutItem",
          "dynamodb:GetItem",
          "dynamodb:Query"
        ]
        Resource = data.terraform_remote_state.infra.outputs.dynamodb_avaliacoes_arn
      },

      # SNS
      {
        Effect = "Allow"
        Action   =[
          "sns:Publish"
        ]
        Resource = aws_sns_topic.avaliacoes_criticas.arn
      }
    ]
  })
}

