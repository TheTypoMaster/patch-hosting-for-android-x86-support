/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
* @author Alexander V. Astapchuk
* @version $Revision$
*/

package java.security;

import java.io.Serializable;
import java.util.Date;
import java.security.cert.CertPath;

import org.apache.harmony.security.internal.nls.Messages;

/**
 * {@code Timestamp} represents a signed time stamp. {@code Timestamp} is
 * immutable.
 * 
 * @since Android 1.0
 */
public final class Timestamp implements Serializable {

    private static final long serialVersionUID = -5502683707821851294L;

    private Date timestamp;

    private CertPath signerCertPath;

    // Cached hash
    private transient int hash;

    /**
     * Constructs a new instance of {@code Timestamp} with the specified {@code
     * timestamp} and the given certificate path.
     * 
     * @param timestamp
     *            date and time.
     * @param signerCertPath
     *            the certificate path.
     * @throws NullPointerException
     *             if {@code timestamp} is {@code null} or if {@code
     *             signerCertPath} is {@code null}.
     * @since Android 1.0
     */
    public Timestamp(Date timestamp, CertPath signerCertPath) {
        if (timestamp == null) {
            throw new NullPointerException(Messages.getString("security.0F")); //$NON-NLS-1$
        }
        if (signerCertPath == null) {
            throw new NullPointerException(Messages.getString("security.10")); //$NON-NLS-1$
        }
        // Clone timestamp to prevent modifications
        this.timestamp = new Date(timestamp.getTime());
        this.signerCertPath = signerCertPath;
    }

    /**
     * Compares the specified object with this {@code Timestamp} for equality
     * and returns {@code true} if the specified object is equal, {@code false}
     * otherwise. The given object is equal to this {@code Timestamp}, if it is
     * an instance of {@code Timestamp}, the two timestamps have an equal date
     * and time and their certificate paths are equal.
     * 
     * @param obj
     *            object to be compared for equality with this {@code
     *            Timestamp}.
     * @return {@code true} if the specified object is equal to this {@code
     *         Timestamp}, otherwise {@code false}.
     * @see #hashCode
     * @since Android 1.0
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Timestamp) {
            Timestamp that = (Timestamp) obj;
            return timestamp.equals(that.timestamp)
                    && signerCertPath.equals(that.signerCertPath);
        }
        return false;
    }

    /**
     * Returns the certificate path of this {@code Timestamp}.
     * 
     * @return the certificate path of this {@code Timestamp}.
     * @since Android 1.0
     */
    public CertPath getSignerCertPath() {
        return signerCertPath;
    }

    /**
     * Returns the date and time of this {@code Timestamp}.
     * 
     * @return the date and time of this {@code Timestamp}.
     * @since Android 1.0
     */
    public Date getTimestamp() {
        // BEGIN android-changed
        // copied from a newer version of harmony
        return (Date) timestamp.clone();
        // END android-changed
    }

    /**
     * Returns the hash code value for this {@code Timestamp}. Returns the same
     * hash code for {@code Timestamp}s that are equal to each other as
     * required by the general contract of {@link Object#hashCode}.
     *
     * @return the hash code value for this {@code Timestamp}.
     * @see Object#equals(Object)
     * @see Timestamp#equals(Object)
     * @since Android 1.0
     */
    public int hashCode() {
        if (hash == 0) {
            hash = timestamp.hashCode() ^ signerCertPath.hashCode();
        }
        return hash;
    }

    /**
     * Returns a string containing a concise, human-readable description of this
     * {@code Timestamp}.
     * 
     * @return a printable representation for this {@code Timestamp}.
     * @since Android 1.0
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(256);
        // Dump only the first certificate
        buf.append("Timestamp [").append(timestamp).append(" certPath="); //$NON-NLS-1$ //$NON-NLS-2$
        buf.append(signerCertPath.getCertificates().get(0)).append("]"); //$NON-NLS-1$
        return buf.toString();
    }
}