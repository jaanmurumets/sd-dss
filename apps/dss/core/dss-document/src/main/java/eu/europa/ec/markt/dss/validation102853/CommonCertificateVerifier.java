/*
 * DSS - Digital Signature Services
 *
 * Copyright (C) 2013 European Commission, Directorate-General Internal Market and Services (DG MARKT), B-1049 Bruxelles/Brussel
 *
 * Developed by: 2013 ARHS Developments S.A. (rue Nicolas Bové 2B, L-1253 Luxembourg) http://www.arhs-developments.com
 *
 * This file is part of the "DSS - Digital Signature Services" project.
 *
 * "DSS - Digital Signature Services" is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * DSS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * "DSS - Digital Signature Services".  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.europa.ec.markt.dss.validation102853;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.ec.markt.dss.validation102853.crl.CRLSource;
import eu.europa.ec.markt.dss.validation102853.https.CommonsHttpDataLoader;
import eu.europa.ec.markt.dss.validation102853.loader.HTTPDataLoader;
import eu.europa.ec.markt.dss.validation102853.ocsp.OCSPSource;
import eu.europa.ec.markt.dss.validation102853.ocsp.OnlineOCSPSource;
import eu.europa.ec.markt.dss.validation102853.crl.OnlineCRLSource;

/**
 * This class provides the different sources used to verify the status of a certificate using the trust model. There are four different types of sources to be defined:<br /> -
 * Trusted certificates source;<br /> - Adjunct certificates source (not trusted);<br /> - OCSP source;<br /> - CRL source.<br />
 *
 * The {@code HTTPDataLoader} should be provided to give access to the certificates through AIA.
 *
 * @version $Revision: 1754 $ - $Date: 2013-03-14 20:27:56 +0100 (Thu, 14 Mar 2013) $
 */

public class CommonCertificateVerifier implements CertificateVerifier {

    private static final Logger LOG = LoggerFactory.getLogger(CommonCertificateVerifier.class);

    /**
     * This field contains the reference to the trusted certificate source. This source is fixed, it means that the same source is used for different validations.
     */
    private TrustedCertificateSource trustedCertSource;

    /**
     * This field contains the reference to any certificate source, can contain the trust store, or the any intermediate certificates.
     */
    private CertificateSource adjunctCertSource;

    /**
     * This field contains the reference to the {@code OCSPSource}.
     */
    private OCSPSource ocspSource;

    /**
     * This field contains the reference to the {@code CRLSource}.
     */
    private CRLSource crlSource;

    /**
     * The data loader used to access AIA certificate source.
     */
    private HTTPDataLoader dataLoader;

    /**
     * This method returns a new instance of the {@code CommonCertificateVerifier} including the {@code OnlineCRLSource}, {@code OnlineOCSPSource} and {@code
     * CommonsHttpDataLoader}.
     *
     * The {@link #trustedCertSource} and {@link #adjunctCertSource} still must be set.
     *
     * @return new instance of {@code CommonCertificateVerifier}
     */
    public static CommonCertificateVerifier getOnlineCertificateVerifier() {

        final CommonCertificateVerifier certificateVerifier = new CommonCertificateVerifier();
        final OnlineCRLSource onlineCRLSource = new OnlineCRLSource();
        certificateVerifier.setCrlSource(onlineCRLSource);
        final OnlineOCSPSource onlineOCSPSource = new OnlineOCSPSource();
        certificateVerifier.setOcspSource(onlineOCSPSource);
        return certificateVerifier;
    }

    /**
     * The default constructor. The {@code HTTPDataLoader} is created to allow the retrieval of certificates through AIA.
     */
    public CommonCertificateVerifier() {

        LOG.info("+ New CommonCertificateVerifier created.");
        dataLoader = new CommonsHttpDataLoader();
    }

    /**
     * This constructor allows to create {@code CommonCertificateVerifier} without {@code HTTPDataLoader}. It means that only a profile -B signatures can be created.
     *
     * @param simpleCreationOnly if true the {@code CommonCertificateVerifier} will not contain {@code HTTPDataLoader}.
     */
    public CommonCertificateVerifier(final boolean simpleCreationOnly) {

        if (!simpleCreationOnly) {
            dataLoader = new CommonsHttpDataLoader();
        }
    }

    /**
     * The constructor with key parameters.
     *
     * @param trustedCertSource the reference to the trusted certificate source.
     * @param crlSource         contains the reference to the {@code OCSPSource}.
     * @param ocspSource        contains the reference to the {@code CRLSource}.
     * @param dataLoader        contains the reference to a data loader used to access AIA certificate source.
     */
    public CommonCertificateVerifier(final TrustedCertificateSource trustedCertSource, final CRLSource crlSource, final OCSPSource ocspSource, final HTTPDataLoader dataLoader) {

        LOG.info("+ New CommonCertificateVerifier created with parameters.");
        this.trustedCertSource = trustedCertSource;
        this.crlSource = crlSource;
        this.ocspSource = ocspSource;
        this.dataLoader = dataLoader;
        if (dataLoader == null) {
            LOG.warn("HTTPDataLoader is null. It's required to access AIA certificate source");
        }
    }

    /**
     * @return
     */
    @Override
    public TrustedCertificateSource getTrustedCertSource() {

        return trustedCertSource;
    }

    /**
     * @return
     */
    @Override
    public OCSPSource getOcspSource() {

        return ocspSource;
    }

    /**
     * @return
     */
    @Override
    public CRLSource getCrlSource() {

        return crlSource;
    }

    /**
     * Defines the source of CRL used by this class
     *
     * @param crlSource the crlSource to set
     */
    @Override
    public void setCrlSource(final CRLSource crlSource) {

        this.crlSource = crlSource;
    }

    /**
     * Defines the source of OCSP used by this class
     *
     * @param ocspSource the ocspSource to set
     */
    @Override
    public void setOcspSource(final OCSPSource ocspSource) {

        this.ocspSource = ocspSource;
    }

    /**
     * Defines how the certificates from the Trusted Lists are retrieved. This source should provide trusted certificates. These certificates are used as trust anchors.
     *
     * @param trustedCertSource The source of trusted certificates.
     */
    @Override
    public void setTrustedCertSource(final TrustedCertificateSource trustedCertSource) {

        this.trustedCertSource = trustedCertSource;
    }

    /**
     * @return
     */
    @Override
    public CertificateSource getAdjunctCertSource() {

        return adjunctCertSource;
    }

    /**
     * @param adjunctCertSource
     */
    @Override
    public void setAdjunctCertSource(final CertificateSource adjunctCertSource) {

        this.adjunctCertSource = adjunctCertSource;
    }

    @Override
    public HTTPDataLoader getDataLoader() {
        return dataLoader;
    }

    @Override
    public void setDataLoader(final HTTPDataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

}
