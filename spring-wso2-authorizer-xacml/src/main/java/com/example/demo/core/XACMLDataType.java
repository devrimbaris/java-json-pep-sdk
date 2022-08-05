package com.example.demo.core;

public enum XACMLDataType {

  STRING("http://www.w3.org/2001/XMLSchema#string"),
  BOOLEAN("http://www.w3.org/2001/XMLSchema#boolean"),
  INTEGER("http://www.w3.org/2001/XMLSchema#integer"),
  DOUBLE("http://www.w3.org/2001/XMLSchema#double"),
  TIME("http://www.w3.org/2001/XMLSchema#time"),
  DATE("http://www.w3.org/2001/XMLSchema#date"),
  DATETIME("http://www.w3.org/2001/XMLSchema#dateTime"),
  ANYURI("http://www.w3.org/2001/XMLSchema#anyURI"),
  HEXBINARY("http://www.w3.org/2001/XMLSchema#hexBinary"),
  BASE64BINARY("http://www.w3.org/2001/XMLSchema#base64Binary"),
  DAYTIMEDURATION("http://www.w3.org/2001/XMLSchema#dayTimeDuration"),
  YEARMONTHDURATION("http://www.w3.org/2001/XMLSchema#yearMonthDuration"),
  X500NAME("urn:oasis:names:tc:xacml:1.0:data-type:x500Name"),
  RFC822NAME("urn:oasis:names:tc:xacml:1.0:data-type:rfc822Name"),
  IPADDRESS("urn:oasis:names:tc:xacml:2.0:data-type:ipAddress"),
  DNSNAME("urn:oasis:names:tc:xacml:2.0:data-type:dnsName"),
  XPATHEXPRESSION("urn:oasis:names:tc:xacml:3.0:data-type:xpathExpression");

  public final String typeUrn;

  private XACMLDataType(String typeUrn) {
    this.typeUrn = typeUrn;
  }

}
