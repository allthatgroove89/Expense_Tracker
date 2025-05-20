provider "aws" {
  region = "us-east-1"
}

# 🔹 Create AWS Secrets Manager Secret
resource "aws_secretsmanager_secret" "expense_tracker_secret" {
  name = "expense_tracker"
}

# 🔹 Store Database Credentials in Secrets Manager
resource "aws_secretsmanager_secret_version" "db_secret" {
  secret_id = aws_secretsmanager_secret.expense_tracker_secret.id
  secret_string = jsonencode({
    "db_user" = "your_db_username",
    "db_pass" = "your_secure_password"
  })
}

# 🔹 Create PostgreSQL RDS Instance
resource "aws_db_instance" "expense_db" {
  engine         = "postgres"
  instance_class = var.instance_type
  allocated_storage = var.allocated_storage
  db_name = var.db_name
  identifier = "terraform-20250519204312825500000003"
  parameter_group_name = "custom-postgres-group"


  # Fetch username & password dynamically from AWS Secrets Manager
  username = jsondecode(aws_secretsmanager_secret_version.db_secret.secret_string)["db_user"]
  password = jsondecode(aws_secretsmanager_secret_version.db_secret.secret_string)["db_pass"]

  publicly_accessible = false
  storage_encrypted   = true
  skip_final_snapshot = true
}
