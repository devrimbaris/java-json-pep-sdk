package com.example.demo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;

import com.example.demo.core.XACMLField;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

class ReflectionTests {


	@Test
	void findXacmlAttributesOfClass(){

		ReflectionDummyClass r = new ReflectionDummyClass();
		r.setAge(10);
		r.setId(1l);
		r.setName("johndoe");
		r.setRole("magician");

		final List<Field> props = new LinkedList<>();
		final Class<ReflectionDummyClass> clazz = ReflectionDummyClass.class;
		ReflectionUtils.doWithFields(clazz , props::add, field -> AnnotationUtils.getAnnotation(field, XACMLField.class) != null);
		final Map<String, Object> mapOfXacmlAttributeValues = new HashMap<>();

		for (Field f:props){
			final XACMLField annotation = AnnotationUtils.getAnnotation(f, XACMLField.class);
			String           urn        = annotation.urn();
			final String methodName = "get" + StringUtils.capitalize(f.getName());
			final Method method     = ReflectionUtils.findMethod(clazz, methodName);
			if (method != null){
				Object val = ReflectionUtils.invokeMethod(method, r);
				mapOfXacmlAttributeValues.put(urn, val);
			} //TODO:handle else

		}
		Assert.assertTrue(props.size() > 0);
		Assert.assertTrue(props.size() == 4);
		Assert.assertTrue(mapOfXacmlAttributeValues.size() == 4);
	}

}
