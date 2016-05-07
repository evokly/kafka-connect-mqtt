package com.evokly.kafka.connect.mqtt.ssl;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by booncol on 07.04.2016.
 *
 */
public class SslUtils {

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    /**
     * Create SSLSocketFactory.
     *
     * @param caCrt CA certificate filepath
     * @param crt Client certificate filepath
     * @param key Client key filepath
     * @param password Password
     *
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory getSslSocketFactory(String caCrt,
                                                       String crt,
                                                       String key,
                                                       String password)
            throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
            UnrecoverableKeyException, KeyManagementException {

        char[] passwdChars = password != null && password.length() > 0
                ? password.toCharArray() : "".toCharArray();

        // load client private key
        PEMParser parser = new PEMParser(
                new InputStreamReader(new FileInputStream(key), Charset.forName("UTF-8"))
        );

        Object obj = parser.readObject();
        KeyPair keyPair;
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        if (obj instanceof PEMEncryptedKeyPair) {
            PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(passwdChars);
            converter = new JcaPEMKeyConverter().setProvider("BC");
            keyPair = converter.getKeyPair(((PEMEncryptedKeyPair) obj).decryptKeyPair(decProv));
        } else {
            keyPair = converter.getKeyPair((PEMKeyPair) obj);
        }

        parser.close();
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter.setProvider("BC");

        // load CA certificate
        parser = new PEMParser(
                new InputStreamReader(new FileInputStream(caCrt), Charset.forName("UTF-8"))
        );
        X509CertificateHolder caCert = (X509CertificateHolder) parser.readObject();
        parser.close();

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", certConverter.getCertificate(caCert));

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory
                .getDefaultAlgorithm());

        tmf.init(caKs);

        // load client certificate
        parser = new PEMParser(
                new InputStreamReader(new FileInputStream(crt), Charset.forName("UTF-8"))
        );

        X509CertificateHolder cert = (X509CertificateHolder) parser.readObject();
        parser.close();

        // Client key and certificates are sent to server so it can authenticate
        // us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", certConverter.getCertificate(cert));
        ks.setKeyEntry("private-key", keyPair.getPrivate(), passwdChars,
                new java.security.cert.Certificate[] { certConverter.getCertificate(cert) });
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
                .getDefaultAlgorithm());
        kmf.init(ks, passwdChars);

        // Finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }

}