package io.xacml.pep.json.client;

import lombok.Builder;

import java.util.Objects;

/**
 * Standard Client configuration needed to make requests to the PDP.
 * <p>
 * Should be provided to the constructor of {@link AuthZClient} implementations where HTTP clients are built
 */
@Builder
public class DefaultClientConfiguration implements ClientConfiguration {

    /**
     * URL of the the PDP.
     * If running in application server like Tomcat where an application suffix is added, it should be included
     * <p>
     * Should not be null
     * <p>
     * ex: https://api.pdp.example.io
     * ex: https://api.example.io/asm-pdp
     */
    String pdpUrl;

    /**
     * The username of the PEP user who can access the pdp for policy decisions
     * <p>
     * ex: pep-user
     */
    String username;

    /**
     * The PEP user's password
     */
    String password;

    public String getPdpUrl() {
        return this.pdpUrl;
    }

    public void setPdpUrl(String pdpUrl) {
        this.pdpUrl = pdpUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DefaultClientConfiguration)) return false;
        final DefaultClientConfiguration other = (DefaultClientConfiguration) o;
        if (!Objects.equals(this.getPdpUrl(), other.getPdpUrl())) return false;
        if (!Objects.equals(this.getUsername(), other.getUsername())) return false;
        return Objects.equals(this.getPassword(), other.getPassword());
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $pdpUrl = this.getPdpUrl();
        result = result * PRIME + ($pdpUrl == null ? 43 : $pdpUrl.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "DefaultClientConfiguration(pdpUrl=" + this.getPdpUrl() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ")";
    }
}
