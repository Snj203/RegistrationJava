package org.example.certFetcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class LoadCertificate {
    public static KeyStore loadCertificate(String certFilePath){
        CertificateFactory cf = null;
        try{
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException ce){
            System.out.println("CCertificate Exception at LoadCertificate: " + ce);
        }

        KeyStore keyStore = null;
        try (FileInputStream fis = new FileInputStream(certFilePath)) {
            Certificate certificate = cf.generateCertificate(fis);

            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("myCert", certificate);

        } catch (Exception e){
            System.out.println("Exception at LoadCertificate: " + e);
        }
        return keyStore;
    }
}
