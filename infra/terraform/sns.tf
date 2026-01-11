# =========================
# SNS Topic
# =========================
resource "aws_sns_topic" "avaliacoes_criticas" {
  name = var.sns_topic_name

  tags = {
    Application = "avaliacao-feedback"
    Environment = var.environment
  }
}