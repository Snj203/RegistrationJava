package org.example.certFetcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class CertificateFetcher {
    public static void getCertificate(String host, int port, String outputFile) {
        String hostname = host;
        int hostPort = port;

        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(hostname, port)) {
            SSLSession session = socket.getSession();
            Certificate[] certificates = session.getPeerCertificates();

            for (Certificate certificate : certificates) {
                if (certificate instanceof X509Certificate) {
                    X509Certificate x509 = (X509Certificate) certificate;

                    System.out.println("Субъект: " + x509.getSubjectDN());
                    System.out.println("Эмитент: " + x509.getIssuerDN());
                    System.out.println("Срок действия: " + x509.getNotBefore() + " - " + x509.getNotAfter());
                }
                if (certificates.length > 0 && certificates[0] instanceof X509Certificate) {
                    X509Certificate x509 = (X509Certificate) certificates[0];

                    // Сохраняем сертификат в файл
                    OutputStream os = null;
                    try{
                        os = new FileOutputStream(outputFile);
                    } catch (FileNotFoundException fnfe){

                    }
                    os.write(x509.getEncoded());

                    System.out.println("Сертификат сохранён в файл: " + outputFile);
                }
            }
        } catch (IOException ioe){
            System.out.println("IOException at CertificateFetcher: " + ioe );
        } catch (CertificateEncodingException cee){
            System.out.println("CertificateEncodingException at CertificateFetcher: " + cee );
        }
    }
}

