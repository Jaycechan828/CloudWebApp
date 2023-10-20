
#build.pkr.hcl
variable "foo" {
  type    = string
  default = "bar"
}

variable "aws_access_key" {
  type    = string
  default = "AKIA2KC2JXHAK5663VU2"
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
  default = "ami-0b6edd8449255b799" # Ubuntu 22.04 LTS
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
  #ami_regions = [

  # "us-east-1",
  #]

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
    volume_size           = 8
    volume_type           = "gp2"
  }
}

build {
  sources = ["source.amazon-ebs.my-ami"]


  provisioner "shell" {
    environment_vars = [
      "DEBIAN_FRONTEND=noninteractive",
      "CHECKPOINT_DISABLE=1",


    ]
    inline = [
      "sudo apt-get update",
      "sudo apt-get upgrade -y",
      "sudo apt-get install nginx -y", # nginx was for demo only. you do not need to install in your AMI.
      "sudo apt-get clean",
    ]
  }
}