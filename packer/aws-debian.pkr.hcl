
#build.pkr.hcl
variable "foo" {
  type    = string
  default = "bar"
}

variable "aws_access_key" {
  type    = string
  default = "AKIA2KC2JXHAMDHWTLQC"
}
variable "aws_secret_key" {
  type    = string
  default = "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"
}

variable "aws_region" {
  type    = string
  default = "us-west-2"
}

variable "source_ami" {
  type    = string
  default = "ami-0b6edd8449255b799" # debian 22.04 LTS
}

variable "ssh_username" {
  type    = string
  default = "admin"
}

variable "subnet_id" {
  type    = string
  default = "subnet-0e95d9516b3fe4efc"
}

# https://www.packer.io/plugins/builders/amazon/ebs
source "amazon-ebs" "my-ami" {

  access_key      = var.aws_access_key
  region          = "us-west-2"
  ami_name        = "csye6225_${formatdate("YYYY_MM_DD_hh_mm_ss", timestamp())}"
  ami_description = "AMI for CSYE 6225"
  #  ami_regions = [
  #
  #    "us-east-1",
  #  ]

  aws_polling {
    delay_seconds = 120
    max_attempts  = 50
  }

  instance_type = "t2.micro"
  secret_key    = var.aws_secret_key
  source_ami    = var.source_ami
  ssh_username  = var.ssh_username
  subnet_id     = var.subnet_id

  launch_block_device_mappings {
    delete_on_termination = true
    device_name           = "/dev/xvda"
    volume_size           = 25
    volume_type           = "gp2"
  }
}

build {
  sources = ["source.amazon-ebs.my-ami"]

  provisioner "file" {
    source      = "target/csye6225-assignment-0.0.1-SNAPSHOT.jar"
    destination = "/home/admin/webapp"
  }

  provisioner "shell" {
    script = "./packer/service.sh"
  }
}