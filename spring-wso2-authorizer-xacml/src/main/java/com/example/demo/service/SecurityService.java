package com.example.demo.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import com.example.demo.core.XACMLAttribute;
import com.example.demo.core.XACMLField;
import com.example.demo.impl.XACMLQueryService;
import io.xacml.json.model.Attribute;
import io.xacml.json.model.Category;
import io.xacml.json.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public abstract class SecurityService<T> implements ResultFilterer {

  @Autowired
  XACMLQueryService xacmlService;

  public XACMLQueryService getXACMLQueryService() {
    return xacmlService;
  }

  public Class<T> getType() {
    Class<T> persistentClass = (Class<T>)
        ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    return persistentClass;
  }

  protected String getPackageName() {
    return getType().getCanonicalName();
  }

  @Override
  public boolean filterResult(final Object filterObject, final Authentication authentication) {

    final List<XACMLAttribute> listOfXacmlAttributes = getXacmlAttributesOfObject(filterObject);
    //loop over xacml attrributes and add them as resource type attributes
    final Request request = buildXACMLRequest(getType().getCanonicalName(), authentication.getName(), "read", listOfXacmlAttributes);
    boolean       result  = getXACMLQueryService().checkPermission(request);
    return result;
  }

  protected Request buildXACMLRequest(final String resourceTypeClass, final String userName, final String permission, final  List<XACMLAttribute> xacmlAttributesOfObject) {

    //build subject
    Category subject = new Category();
    subject.addAttribute(new Attribute("http://wso2.org/identity/user/username",
                                       userName,
                                       false,
                                       "string"
    ));

    //build action
    Category action = new Category();
    action.addAttribute("urn:oasis:names:tc:xacml:1.0:action:action-id", permission);

    //build resourceww
    Category resource = new Category();
    resource.addAttribute("urn:oasis:names:tc:xacml:1.0:resource:resource-id", resourceTypeClass);
    for (XACMLAttribute xa : xacmlAttributesOfObject) {
      resource.addAttribute(xa.getUrn(), xa.getValue(), xa.getDataType().typeUrn);
    }

    // build environment
    //TODO: build environment add Security attributes
    //Security should be coming from application level attributes etc.
    Category environment = new Category();
    environment.addAttribute("Security", "NS");

    //build request from subject, action, resource and environment
    Request request = new Request();
    request.addAccessSubjectCategory(subject);
    request.addActionCategory(action);
    request.addResourceCategory(resource);
    request.addEnvironmentCategory(environment);

    return request;
  }

  private List<XACMLAttribute> getXacmlAttributesOfObject(final Object filterObject) {
    final List<XACMLAttribute> list = new LinkedList<>();
    final List<Field>          props                     = new LinkedList<>();
    final Class<T>            clazz                     = getType();
    ReflectionUtils.doWithFields(clazz, props::add, field -> AnnotationUtils.getAnnotation(field, XACMLField.class) != null);

    for (Field f : props) {
      final XACMLField annotation = AnnotationUtils.getAnnotation(f, XACMLField.class);
      String           urn        = annotation.urn();
      final String         methodName = "get" + StringUtils.capitalize(f.getName());
      final Method         method     = ReflectionUtils.findMethod(clazz, methodName);
      if (method != null) {
        Object val = ReflectionUtils.invokeMethod(method, filterObject);
        XACMLAttribute x = new XACMLAttribute(urn, annotation.dataType(), val);
        list.add(x);
      } //TODO:handle else
    }
    return list;
  }

}
