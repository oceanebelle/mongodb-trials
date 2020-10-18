
# Creating OPEN SSL Server Certificates for testing

https://docs.mongodb.com/manual/appendix/security/appendixB-openssl-server/



## How to create self signed certificate (CRT): 
https://www.arubacloud.com/tutorial/how-to-create-a-self-signed-ssl-certificate-on-ubuntu-18-04.aspx

#### To create a private key, use the OpenSSL client: 

    $ sudo openssl genrsa -aes128 -out private.key 2048

#### Creating a Certificate Signing Request (CSR)
After generating your private key, create a certificate signing request (CSR) which will specify the details for the certificate.

    $ sudo openssl req -new -days 365 -key private.key -out request.csr    


#### Generating the SSL Certificate
At this point, proceed with the generation of the certificate:

    $ sudo openssl x509 -in request.csr -out certificate.crt -req -signkey private.key -days 365

Where :

for the -in parameter specify the certificate signing request

for the parameter -out specify the name of the file that will contain the certificate

for the -signkey parameter specify your private key

for the parameter -days specify the number of days of validity of the certificate that is going o be created


## Method 2 PEM
https://support.microfocus.com/kb/doc.php?id=7013103

How to create a self-signed PEM file

    openssl req -newkey rsa:2048 -new -nodes -x509 -days 3650 -keyout key.pem -out cert.pem

How to create a PEM file from existing certificate files that form a chain
(optional) Remove the password from the Private Key by following the steps listed below:

    openssl rsa -in server.key -out nopassword.key

Note: Enter the pass phrase of the Private Key.

Combine the private key, public certificate and any 3rd party intermediate certificate files:

    cat nopassword.key > server.pem
    cat server.crt >> server.pem

Note: Repeat this step as needed for third-party certificate chain files, bundles, etc:

    cat intermediate.crt >> server.pem


## Creating self signed cert with password less RSA key with prompts. Be careful with the subject

    openssl req -nodes -new -x509 -keyout server.key -out certificate.crt

### Creating server pem

    cat certificate.crt server.key > server.pem


### One line Self-signed cert without prompts

n OpenSSL ≥ 1.1.1, this can be shortened to:

    openssl req -x509 -newkey rsa:4096 -sha256 -days 3650 -nodes -keyout server.key -out certificate.crt -subj "/CN=localhost" -addext "subjectAltName=DNS:localhost,DNS:localhost,IP:127.0.0.1"

It creates a certificate that is

valid for the (sub)domains localhost and localhost (SAN),
also valid for the IP address 127.0.0.1 (SAN),
relatively strong (as of 2020) and
valid for 3650 days (~10 years).
It creates the following files:

Private key: example.key
Certificate: example.crt
All information is provided at the command line. There is no interactive input that annoys you. There are no config files you have to mess around with. All necessary steps are executed by a single OpenSSL invocation: from private key generation up to the self-signed certificate.



# Accessing secured mongo via cli

see https://docs.mongodb.com/manual/tutorial/configure-ssl-clients/

    mongo "mongodb://root:example@localhost:27017/admin?authSource=admin" --tls --tlsCertificateKeyFile ./secrets/certificate.crt


# Helpful commands    

Check what the server returns for the handshake: 

    openssl s_client -connect localhost:27017