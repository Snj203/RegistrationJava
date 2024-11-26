package org.example.certFetcher;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLContextSetup {
    public static SSLContext createSSLContext() {
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(keyStore);
        SSLContext sslContext = null;
        try{
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, new java.security.SecureRandom());
        } catch(NoSuchAlgorithmException nsae){
            System.out.println("No Such Algorithm Exception at SSLContextSetup: " + nsae);
        } catch(KeyManagementException kme){
            System.out.println("Key Management Exception at SSLContextSetup: " + kme);
        }

        return sslContext;
    }
}
