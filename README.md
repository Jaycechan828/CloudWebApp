# webapp



For adding SSL certificate, run the command below in cert dir

aws acm import-certificate \
  --certificate fileb://certificate.crt \
  --private-key fileb://private.key \
  --certificate-chain fileb://ca_bundle.crt


