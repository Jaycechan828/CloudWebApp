
packer {
  required_plugins {
    amazon = {
      source  = "github.com/hashicorp/amazon"
      version = "~> 1"
    }
  }
}

#build.pkr.hcl
variable "foo" {
  type    = string
  default = "bar"
}

variable "aws_access_key" {
  type    = string
  default = "AKIA2KC2JXHAM7OCSSE2"
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

  ami_users = [
    "048190965240",
  ]

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
    source      = "/home/runner/work/webapp/webapp/target/csye6225-assignment-0.0.1-SNAPSHOT.jar"
    destination = "/tmp/webapp.jar"
  }

  provisioner "file" {
    source      = "/home/runner/work/webapp/webapp/src/main/resources/users.csv"
    destination = "/tmp/users.csv"
  }

  provisioner "file" {
    source      = "/home/runner/work/webapp/webapp/src/main/resources/csye6225.service"
    destination = "/tmp/csye6225.service"
  }
  provisioner "file" {
    source      = "/home/runner/work/webapp/webapp/src/main/resources/cloudwatch-config.json"
    destination = "/tmp/cloudwatch-config.json"
  }

  provisioner "shell" {
    inline = [

      "sudo mv /tmp/webapp.jar /opt/webapp.jar",
      "sudo mv /tmp/users.csv /opt/users.csv",
      "sudo mv /tmp/csye6225.service /etc/systemd/system/csye6225.service",
      "sudo mv /tmp/cloudwatch-config.json /opt/cloudwatch-config.json"
    ]
  }

  provisioner "shell" {
    environment_vars = [
      "DEBIAN_FRONTEND=noninteractive",
      "CHECKPOINT_DISABLE=1"
    ]
    inline = [
      "sudo apt-get update",
      "sudo apt-get upgrade -y",


      "sudo apt-get install -y openjdk-17-jdk",
      "wget https://amazoncloudwatch-agent.s3.amazonaws.com/debian/amd64/latest/amazon-cloudwatch-agent.deb",
      "sudo dpkg -i -E ./amazon-cloudwatch-agent.deb"

    ]
  }
  post-processor "manifest" {
    output = "manifest.json"
    strip_path = true
  }

  post-processor "shell-local" {
    inline = ["ami_id=$(jq -r '.builds[-1].artifact_id' manifest.json | cut -d':' -f2)", "echo $ami_id > output-ami-id.txt"]
  }
}