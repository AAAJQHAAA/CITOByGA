package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;

import javax.crypto.SecretKey;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.keys.content.x509.XMLX509Digest;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;

/**
 * KeyResolverSpi implementation which resolves public keys and X.509 certificates from a 
 * <code>dsig11:X509Digest</code> element.
 * 
 * @author Brent Putman (putmanb@georgetown.edu)
 */
public class X509DigestResolver extends KeyResolverSpi {

    /** {@link org.apache.commons.logging} logging facility */
    private static org.apache.commons.logging.Log log = 
        org.apache.commons.logging.LogFactory.getLog(X509DigestResolver.class);

    /** {@inheritDoc}. */
    public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
        if (XMLUtils.elementIsInSignatureSpace(element, Constants._TAG_X509DATA)) {
            try {
                X509Data x509Data = new X509Data(element, baseURI);
                return x509Data.containsDigest();
            } catch (XMLSecurityException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /** {@inheritDoc}. */
    public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage)
        throws KeyResolverException {

        X509Certificate cert = this.engineLookupResolveX509Certificate(element, baseURI, storage);

        if (cert != null) {
            return cert.getPublicKey();
        }

        return null;
    }

    /** {@inheritDoc}. */
    public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage)
        throws KeyResolverException {

        if (log.isDebugEnabled()) {
            log.debug("Can I resolve " + element.getTagName());
        }

        if (!engineCanResolve(element, baseURI, storage)) {
            return null;
        }

        try {
            return resolveCertificate(element, baseURI, storage);
        } catch (XMLSecurityException e) {
            if (log.isDebugEnabled()) {
                log.debug("XMLSecurityException", e);
            }
        }

        return null;
    }

    /** {@inheritDoc}. */
    public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage)
        throws KeyResolverException {
        return null;
    }

    /**
     * Resolves from the storage resolver the actual certificate represented by the digest.
     * 
     * @param element
     * @param baseURI
     * @param storage
     * @return
     * @throws XMLSecurityException
     */
    private X509Certificate resolveCertificate(Element element, String baseURI, StorageResolver storage) 
        throws XMLSecurityException {

        XMLX509Digest x509Digests[] = null;

        Element x509childNodes[] = XMLUtils.selectDs11Nodes(element.getFirstChild(), Constants._TAG_X509DIGEST);

        if (x509childNodes == null || x509childNodes.length <= 0) {
            return null;
        }

        try {         
            checkStorage(storage);

            x509Digests = new XMLX509Digest[x509childNodes.length];

            for (int i = 0; i < x509childNodes.length; i++) {
                x509Digests[i] = new XMLX509Digest(x509childNodes[i], baseURI);
            }

            Iterator<Certificate> storageIterator = storage.getIterator();
            while (storageIterator.hasNext()) {
                X509Certificate cert = (X509Certificate) storageIterator.next();

                for (int i = 0; i < x509Digests.length; i++) {
                    XMLX509Digest keyInfoDigest = x509Digests[i];
                    byte[] certDigestBytes = XMLX509Digest.getDigestBytesFromCert(cert, keyInfoDigest.getAlgorithm());

                    if (Arrays.equals(keyInfoDigest.getDigestBytes(), certDigestBytes)) {
                        if (log.isDebugEnabled()) {
                            log.debug("Found certificate with: " + cert.getSubjectX500Principal().getName());
                        }
                        return cert;
                    }

                }
            }

        } catch (XMLSecurityException ex) {
            throw new KeyResolverException("empty", ex);
        }

        return null;
    }

    /**
     * Method checkSrorage
     * 
     * @param storage
     * @throws KeyResolverException
     */
    private void checkStorage(StorageResolver storage) throws KeyResolverException {
        if (storage == null) {
            Object exArgs[] = { Constants._TAG_X509DIGEST };
            KeyResolverException ex = new KeyResolverException("KeyResolver.needStorageResolver", exArgs);
            if (log.isDebugEnabled()) {
                log.debug("", ex);
            }
            throw ex;
        }
    }

}
