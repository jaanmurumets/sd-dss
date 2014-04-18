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

package eu.europa.ec.markt.dss.validation102853.loader;

import java.io.Serializable;

import eu.europa.ec.markt.dss.exception.DSSCannotFetchDataException;

/**
 * Component that allows to retrieve the data using any protocol: HTTP, HTTPS, FTP, LDAP.
 *
 * @version $Revision: 3710 $ - $Date: 2014-04-10 11:09:53 +0200 (Thu, 10 Apr 2014) $
 */

public interface DataLoader extends Serializable {

    public static final String FILE = "file";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String LDAP = "ldap";
    public static final String FTP = "ftp";

    /**
     * Execute a HTTP GET operation
     *
     * @param url
     * @return
     * @throws eu.europa.ec.markt.dss.exception.DSSCannotFetchDataException
     */
    byte[] get(final String url) throws DSSCannotFetchDataException;

    /**
     * Executes a HTTP POST operation
     *
     * @param url
     * @param content
     * @return
     * @throws eu.europa.ec.markt.dss.exception.DSSCannotFetchDataException
     */
    byte[] post(final String url, final byte[] content) throws DSSCannotFetchDataException;

    /**
     * This allows to set the content type. Example: Content-Type "application/ocsp-request"
     *
     * @param contentType
     */
    public void setContentType(final String contentType);
}
